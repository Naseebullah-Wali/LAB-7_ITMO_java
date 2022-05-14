package Client;
import Client.utility.AuthHandler;
import Client.utility.UserHandler;
import common.Exceptions.NotInDeclaredLimitsException;
import common.Exceptions.WrongAmountOfElementsException;
import common.utilities.Outputer;

import java.util.Scanner;



public class ClientApp {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    private static String host="localhost";
    private static int port;

    public static void main(String[] args) {
        if (!initialize(args)) return;
        Scanner userScanner = new Scanner(System.in);
        AuthHandler authHandler = new AuthHandler(userScanner);
        UserHandler userHandler = new UserHandler(userScanner);
        Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler, authHandler);
        client.run();
        userScanner.close();
    }

    /**
     * Controls initialization.
     */
    private static boolean initialize(String[] args) {
        try {
            if (args.length != 2) throw new WrongAmountOfElementsException();
            host = args[0];
            port = Integer.parseInt(args[1]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(ClientApp.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Использование: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printerror("Порт должен быть представлен числом!");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Порт не может быть отрицательным!");
        }
        return false;
    }
}

//import Client.utility.UserHandler;
//import common.Exceptions.NotInDeclaredLimitsException;
//import common.Exceptions.WrongAmountOfElementsException;
//import common.utilities.Outputer;
//
//import java.util.Scanner;
//
//
//public class ClientApp {
//    public static final String PS1 = "$ ";
//    public static final String PS2 = "> ";
//
//    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
//    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
//
//    private static String host = "localhost";
//    private static int port = 1821;
//
//
//
//    public static void main(String[] args) {
//
//        Scanner userScanner = new Scanner(System.in);
//        UserHandler userHandler = new UserHandler(userScanner);
//        Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler);
//        client.run();
//        userScanner.close();
//    }}
