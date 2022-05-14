package Client.utility;

import Client.ClientApp;
import Collection.ProductAsker;
import common.Data.Product;
import common.Exceptions.CommandUsageException;
import common.Exceptions.IncorrectInputInScriptException;
import common.Exceptions.ScriptRecursionException;
import common.Interaction.Request;
import common.Interaction.ResponseCode;
import common.Interaction.User;
import common.utilities.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */
public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode, User user) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Outputer.println("Back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            Outputer.print(ClientApp.PS1);
                            Outputer.println(userInput);
                        }
                    } else {
                        Outputer.print(ClientApp.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println();
                    Outputer.printerror("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printerror("The number of input attempts has been exceeded!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingCode) {
                    case OBJECT:
                        Product productADDRaw = generateProductAdd();
                        return new Request(userCommand[0], userCommand[1], productADDRaw, user);

                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.println("Executing the script '" + scriptFile.getName() + "'...");
                        break;
                    case OK:
                        break;
                    case UPDATE_OBJECT:
                        int id = Integer.parseInt(userCommand[1]);
                        Product productToUpdate = generateProductAdd(id);
                        return new Request(userCommand[0], userCommand[1], productToUpdate, user);
                }
            } catch (FileNotFoundException exception) {
                Outputer.printerror("Script file not found!");
            } catch (ScriptRecursionException exception) {
                Outputer.printerror("Scripts cannot be called recursively!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            Outputer.printerror("The script execution has been interrupted!!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request(user);
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "Show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "save":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "Execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add_if_min":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "count_greater_than_part_number":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "sum_of_price":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "print_field_ascending_price":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add_if_max":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<product>");
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                default:
                    Outputer.println("Command '" + command + "' not found. Type 'help' for help.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            //  System.out.println("here was problem");
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.println("Using: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }


    private Product generateProductAdd() throws IncorrectInputInScriptException {
        ProductAsker productAsker = new ProductAsker(userScanner);
        return productAsker.createProduct();
    }

    private Product generateProductAdd(int id) throws IncorrectInputInScriptException {
        ProductAsker productAsker = new ProductAsker(userScanner);
        return productAsker.createProduct(id);
    }

    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }


}
