package Server;
import Collection.*;
import Commands.*;

import Server.utility.DatabaseCollectionManager;
import Server.utility.DatabaseHandler;
import Server.utility.DatabaseUserManager;

import common.Exceptions.NotInDeclaredLimitsException;
import common.Exceptions.WrongAmountOfElementsException;
import common.utilities.Outputer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerApp {
//    public static final int Port =1821;
//    public static final int CONNECTION_TIMEOUT =60*100;
//    public static Logging logger = new Logging();
    private static final int MAX_CLIENTS = 1000;
    //public static Logger logger = LogManager.getLogManager().getLogger("ServerLogger");  //getLogger("ServerLogger");
    private static String databaseUsername = "postgres";
    private static int port;
    private static String databaseHost= "localhost";
    private static String databasePassword="xay853";
    private static String databaseAddress;


   // FileM fileM = new FileM();
    public static void main(String [] args) {

        if (!initialize(args)) return;
        DatabaseHandler databaseHandler = new DatabaseHandler(databaseAddress, databaseUsername, databasePassword);
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseHandler);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseHandler, databaseUserManager);
        CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);




      //  FileM fileM = new FileM();

//        TreeSet<Product> products = fileM.readCSV();

        //CollectionManager collectionManager = new CollectionManager(fileM);
        System.out.println("Welcome to program!");


        CommandManager commandManager = new CommandManager(
                new add_element(collectionManager,databaseCollectionManager),
                new clear(collectionManager,databaseCollectionManager),
                new exit(),
                new HelpCommand(),
                new info(collectionManager),
                new Show(collectionManager),
                new print_field_ascending_price(collectionManager),
                new sum_of_price(collectionManager),
                new add_if_max(collectionManager,databaseCollectionManager),
                new add_if_min(collectionManager,databaseCollectionManager),
                new executerScriptCommand(),
                new remove(collectionManager,databaseCollectionManager),
                new count_greater_than_part_number(collectionManager),
                new save(collectionManager),
                new update_id(collectionManager,databaseCollectionManager),
                new HistoryCommand(), //todo
                new Savecom(collectionManager),
                new LoginCommand(databaseUserManager),
                new RegisterCommand(databaseUserManager)
        );
        Server server = new Server(port, MAX_CLIENTS, commandManager);
        server.run();
        databaseHandler.closeConnection();
    }

//        RequestHandler requestHandler = new RequestHandler(commandManager);
//        Server server = new Server(Port, CONNECTION_TIMEOUT, requestHandler);
//
//        Runnable save = () -> {
//            Scanner scanner = new Scanner(System.in, "windows-1251");
//            while (true) {
//                String com = scanner.nextLine().trim();
//                if (com.equals("save")) {
//                    CommandManager.SaveCom("save", null);
//                } else if (com.equals("stop")) {
//                    server.stop();
//                    break;
//                }
//            }
//        };
//
//        Thread th = new Thread(save);
//        th.start();
//
//        server.run();


    private static boolean initialize(String[] args) {
        try {
            if (args.length != 3) throw new WrongAmountOfElementsException();
            port = Integer.parseInt(args[0]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            databaseHost = args[1];
            databasePassword = args[2];
            databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/postgres";

            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(ServerApp.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Использование: 'java -jar " + jarName + " <port> <db_host> <db_password>'");
        } catch (NumberFormatException exception) {
            Outputer.printerror("Порт должен быть представлен числом!");
           // App.logger.fatal("Порт должен быть представлен числом!");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Порт не может быть отрицательным!");
         //   App.logger.fatal("Порт не может быть отрицательным!");
        }
       // App.logger.fatal("Ошибка инициализации порта запуска!");
        return false;
    }
}


