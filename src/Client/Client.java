package Client;


import Client.utility.AuthHandler;
import Client.utility.UserHandler;
import common.Exceptions.*;
//import common.exceptions.NotInDeclaredLimitsException;
import common.Interaction.Request;
import common.Interaction.Response;
import common.Interaction.ResponseCode;
import common.Interaction.User;
import common.utilities.Outputer;
import common.utilities.Outputer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private AuthHandler authHandler;
    private User user;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler, AuthHandler authHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
        this.authHandler=authHandler;

    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            while (true) {
                try {
                    connectToServer();
                    processAuthentication();
                    processRequestToServer();
                    break;
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printerror("Превышено количество попыток подключения!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Outputer.printerror("Время ожидания подключения '" + reconnectionTimeout +
                                "' находится за пределами возможных значений!");
                        Outputer.println("Повторное подключение будет произведено немедленно.");
                    } catch (Exception timeoutException) {
                        Outputer.printerror("Произошла ошибка при попытке ожидания подключения!");
                        Outputer.println("Повторное подключение будет произведено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Outputer.println("Работа клиента завершена.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Клиент не может быть запущен!");
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при попытке завершить соединение с сервером!");
        }
    }
//    public void run() {
//        try {
//            boolean processingStatus = true;
//            while (processingStatus) {
//                try {
//                    connectToServer();
//                    processingStatus = processRequestToServer();
//                } catch (ConnectionErrorException exception) {
//                    if (reconnectionAttempts >= maxReconnectionAttempts) {
//                        Outputer.printerror("The number of connection attempts has been exceeded!");
//                        break;
//                    }
//                    try {
//                        Thread.sleep(reconnectionTimeout);
//                    } catch (IllegalArgumentException timeoutException) {
//                        Outputer.printerror("Connection timeout'" + reconnectionTimeout +
//                                "' is out of range!");
//                        Outputer.println("Reconnection will be done immediately.");
//                    } catch (Exception timeoutException) {
//                        Outputer.printerror("An error occurred while trying to wait for a connection!");
//                        Outputer.println("Reconnection will be done immediately.");
//                    }
//                }
//                reconnectionAttempts++;
//            }
//            if (socketChannel != null) socketChannel.close();
//            Outputer.println("The client has completed successfully.");
//        } catch (NotInDeclaredLimitsException exception) {
//            Outputer.printerror("The client cannot be started!");
//        } catch (IOException exception) {
//            Outputer.printerror("An error occurred while trying to end the connection to the server!");
//        }
//    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1)
                Outputer.println("Reconnecting to the server ...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Outputer.println("The connection to the server has been successfully established.");
            Outputer.println("Waiting for permission to exchange data ...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Outputer.println("Permission to exchange data received.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("The server address was entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private void processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode(), user) :
                        userHandler.handle(null, user);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Outputer.printerror("Соединение с сервером разорвано!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    if (requestToServer.getCommmandName().equals("exit"))
                        Outputer.println("Команда не будет зарегистрирована на сервере.");
                    else Outputer.println("Попробуйте повторить команду позднее.");
                }
            }
        } while (!requestToServer.getCommmandName().equals("exit"));
    }

    //    private boolean processRequestToServer() {
//        Request requestToServer = null;
//        Response serverResponse = null;
//        do {
//            try {
//                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
//                        userHandler.handle(null);
//                if (requestToServer.isEmpty()) continue;
//                serverWriter.writeObject(requestToServer);
//                serverResponse = (Response) serverReader.readObject();
//                Outputer.print(serverResponse.getResponseBody());
//            } catch (InvalidClassException | NotSerializableException exception) {
//                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
//            } catch (ClassNotFoundException exception) {
//                Outputer.printerror("Произошла ошибка при чтении полученных данных!");
//            } catch (IOException exception) {
//                Outputer.printerror("Соединение с сервером разорвано!");
//                try {
//                    reconnectionAttempts++;
//                    connectToServer();
//                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
//                    if (requestToServer.getCommmandName().equals("exit"))
//                        Outputer.println("Команда не будет зарегистрирована на сервере.");
//                    else Outputer.println("Попробуйте повторить команду позднее.");
//                }
//            }
//        } while (!requestToServer.getCommmandName().equals("exit"));
//        return false;
//    }
//    private boolean processRequestToServer() {
//        Request requestToServer = null;
//        Response serverResponse = null;
//        do {
//            try {
//                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
//                        userHandler.handle(null);
//                if (requestToServer.isEmpty()) continue;
//                serverWriter.writeObject(requestToServer);
//                serverResponse = (Response) serverReader.readObject();
//                Outputer.print(serverResponse.getResponseBody());
//            } catch (InvalidClassException | NotSerializableException exception) {
//                Outputer.printerror("An error occurred while sending data to the server! " + exception.getMessage() + " " + exception.toString());
//            } catch (ClassNotFoundException exception) {
//                Outputer.printerror("An error occurred while reading the received data!");
//            } catch (IOException exception) {
//                ArrayList stackTrace = new ArrayList<>(Arrays.asList(exception.getStackTrace()));
//                Outputer.printerror("The connection to the server has been dropped!" + exception.getMessage() + " " + stackTrace);
//                try {
//                    reconnectionAttempts++;
//                    connectToServer();
//                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
//                    if (requestToServer.getCommmandName().equals("exit"))
//                        Outputer.println("The command will not be registered on the server.");
//                    else Outputer.println("Please try again later.");
//                }
//            }
//        } while (!requestToServer.getCommmandName().equals("exit"));
//        return false;
//    }
    private void processAuthentication() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = authHandler.handle();
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Outputer.printerror("processAuthentication, Соединение с сервером разорвано!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    Outputer.println("Попробуйте повторить авторизацию позднее.");
                }
            }
        } while (serverResponse == null || !serverResponse.getResponseCode().equals(ResponseCode.OK));
        user = requestToServer.getUser();
    }
}
