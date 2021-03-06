package Server.utility;

import Collection.CommandManager;
import common.Interaction.*;
//import common.interaction.Response;
//import common.interaction.ResponseCode;
import common.utilities.Outputer;//Outputer;
import Server.ServerApp;//App;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Handles user connection.
 */
public class ConnectionHandler implements Runnable {
    private Server server;
    private Socket clientSocket;
    private CommandManager commandManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);


    public ConnectionHandler(Server server, Socket clientSocket, CommandManager commandManager) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.commandManager = commandManager;
    }

    /**
     * Main handling cycle.
     */
    @Override
    public void run() {
        Request userRequest = null;
        Response responseToUser = null;
        boolean stopFlag = false;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = forkJoinPool.invoke(new HandleRequestTask(userRequest, commandManager));
              //  ServerApp.logger.info("Запрос '" + userRequest.getCommmandName() + "' обработан.");
                Response finalResponseToUser = responseToUser;
                if (!fixedThreadPool.submit(() -> {
                    try {
                        clientWriter.writeObject(finalResponseToUser);
                        clientWriter.flush();
                        return true;
                    } catch (IOException exception) {
                        Outputer.printerror("Произошла ошибка при отправке данных на клиент!");
                        //App.logger.error("Произошла ошибка при отправке данных на клиент!");
                    }
                    return false;
                }).get()) break;
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT &&
                    responseToUser.getResponseCode() != ResponseCode.CLIENT_EXIT);
            if (responseToUser.getResponseCode() == ResponseCode.SERVER_EXIT)
                stopFlag = true;
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            //App.logger.error("Произошла ошибка при чтении полученных данных!");
        } catch (CancellationException | ExecutionException | InterruptedException exception) {
            Outputer.println("При обработке запроса произошла ошибка многопоточности!");
           // App.logger.warn("При обработке запроса произошла ошибка многопоточности!");
        } catch (IOException exception) {
            Outputer.printerror("Непредвиденный разрыв соединения с клиентом!");
           // App.logger.warn("Непредвиденный разрыв соединения с клиентом!");
        } catch (Exception e){
            e.printStackTrace();
            try {
                fixedThreadPool.shutdown();
                clientSocket.close();
                Outputer.println("Клиент отключен от сервера.");
               // ServerApp.logger.info("Клиент отключен от сервера.");
            } catch (IOException exception) {
                Outputer.printerror("Произошла ошибка при попытке завершить соединение с клиентом!");
                //App.logger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
            }
            if (stopFlag) server.stop();
            server.releaseConnection();
        }
    }
}
