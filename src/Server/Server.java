package Server;

import Client.ClientApp;
import Collection.CommandManager;
import Server.utility.ConnectionHandler;
import common.Exceptions.*;
//import common.exceptions.ConnectionErrorException;
//import common.exceptions.OpeningServerSocketException;
import common.Interaction.*;
//import Interaction.Response;
//import common.interaction.ResponseCode;
import common.utilities.Outputer;
//import Server.utility.RequestHandler;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static com.oracle.net.Sdp.openServerSocket;

/**
 * Runs the server.
 */
public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    //private RequestHandler requestHandler;
    private CommandManager commandManager;
    private boolean isStopped;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private Semaphore semaphore;

//    public Server(int port, int soTimeout, RequestHandler requestHandler) {
//        this.port = port;
//        this.soTimeout = soTimeout;
//        this.requestHandler = requestHandler;
//    }
    public Server(int port, int maxClients, CommandManager commandManager) {
    this.port = port;
    this.commandManager = commandManager;
    this.semaphore = new Semaphore(maxClients);
    }


    /**
     * Begins server operation.
     */
    public void run() {
        try {
            openServerSocket();
            while (!isStopped()) {
                try {
                    acquireConnection();
                    if (isStopped()) throw new ConnectionErrorException();
                    Socket clientSocket = connectToClient();
                    cachedThreadPool.submit(new ConnectionHandler(this, clientSocket, commandManager));
                } catch (ConnectionErrorException exception) {
                    if (!isStopped()) {
                        Outputer.printerror("Произошла ошибка при соединении с клиентом!");
                        exception.printStackTrace();
                    } else break;
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                }
            }
            cachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            Outputer.println("Работа сервера завершена.");
        } catch (OpeningServerSocketException exception) {
            Outputer.printerror("Сервер не может быть запущен!");
           // App.logger.fatal("Сервер не может быть запущен!");
        } catch (InterruptedException e) {
            Outputer.printerror("Произошла ошибка при завершении работы с уже подключенными клиентами!");
        }
    }

//        try {
//            openServerSocket();
//            boolean processingStatus = true;
//            while (processingStatus) {
//                try (Socket clientSocket = connectToClient()) {
//                    processingStatus = processClientRequest(clientSocket);
//                } catch (ConnectionErrorException | SocketTimeoutException exception) {
//                    break;
//                } catch (IOException exception) {
//                    Outputer.printerror("An error occurred while trying to end the connection with the client!");
//                }
//            }
//            stop();
//        } catch (OpeningServerSocketException exception) {
//            Outputer.printerror("The server cannot be started!");
//        }
//    }
    /**
     * Release connection.
     */
    public void releaseConnection() {
        semaphore.release();
        System.out.println("Разрыв соединения зарегистрирован.");
       // ServerApp.logger.info("Разрыв соединения зарегистрирован.");
    }
    /**
     * Acquire connection.
     */
    public void acquireConnection() {
        try {
            semaphore.acquire();
          //  ServerApp.logger.info("Разрешение на новое соединение получено.");
        } catch (InterruptedException exception) {
            Outputer.printerror("Произошла ошибка при получении разрешения на новое соединение!");
           // ServerApp.logger.error("Произошла ошибка при получении разрешения на новое соединение!");
        }
//        catch(NullPointerException e){
//            System.out.println("problem of aquireConnection" );
//        }
    }
    /**
     * Checked stops of server.
     *
     * @return Status of server stop.
     */
    private synchronized boolean isStopped() {
        return isStopped;
    }

    /**
     * Finishes server operation.
     */
    public void stop() {
        try {
           // ServerApp.logger.info("Shutting down the server ...");
           // ServerApp.logger.info("Save data...");
            CommandManager.SaveCom("save", null,null);

            if (serverSocket == null) {
                throw new ClosingSocketException();
            }
            serverSocket.close();
            Outputer.println("The server has ended successfully.");
           // ServerApp.logger.info("The server has ended successfully.");

        } catch (ClosingSocketException exception) {
            Outputer.printerror("Unable to shutdown a server that has not yet been started!");
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while shutting down the server!");
        }
        catch (NullPointerException e){
            System.out.println("Found in stop server");
        }
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            Outputer.println("Starting the server ...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);//time(soTimeout);
            Outputer.println("The server has started successfully.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("Port '" + port + "' is out of range!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocketException();
        }catch (NullPointerException e){
            Outputer.println("problem in opening server Socket");
        }
    }

    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            Outputer.println("Port listening '" + port + "'...");
            //ServerApp.logger.info("Port listening '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputer.println("The connection with the client has been successfully established.");
            //ServerApp.logger.info("The connection with the client has been successfully established.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Outputer.printerror("Connection timed out!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while connecting to the client!");
        }catch (NullPointerException e){
            System.out.println("Problem in connectto client");
            e.printStackTrace();
        }throw new ConnectionErrorException();
    }

    /**
     * The process of receiving a request from a client.
     */
//    private boolean processClientRequest(Socket clientSocket) {
//        Request userRequest = null;
//        Response responseToUser = null;
//
//        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
//             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
//
//            do {
//                userRequest = (Request) clientReader.readObject();
//                responseToUser = requestHandler.handle(userRequest);
//                ServerApp.logger.info("Request '" + userRequest.getCommmandName() + "' successfully processed.");
//                clientWriter.writeObject(responseToUser);
//                clientWriter.flush();
//            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT);
//            return false;
//        } catch (ClassNotFoundException exception) {
//            Outputer.printerror("An error occurred while reading the received data!");
//        } catch (InvalidClassException | NotSerializableException exception) {
//            Outputer.printerror("An error occurred while sending data to the client!");
//        } catch (IOException exception) {
//            if (userRequest == null) {
//                Outputer.printerror("Unexpected disconnection from the client!");
//            } else {
//                Outputer.println("The client has been successfully disconnected from the server!");
//                ServerApp.logger.info("The client has been successfully disconnected from the server!");
//            }
//        }
//        return true;
//    }
}
