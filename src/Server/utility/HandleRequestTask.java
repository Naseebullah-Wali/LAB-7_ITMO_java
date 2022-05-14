package Server.utility;

import Collection.CommandManager;
import common.Interaction.Request;
import common.Interaction.Response;
import common.Interaction.ResponseCode;
import common.Interaction.User;

import java.util.concurrent.RecursiveTask;

/**
 * A class for handle request task.
 */
public class HandleRequestTask extends RecursiveTask<Response> {
    private Request request;
    private CommandManager commandManager;

    public HandleRequestTask(Request request, CommandManager commandManager) {
        this.request = request;
        this.commandManager = commandManager;
    }

    @Override
    protected Response compute() {
        User hashedUser = new User(
                request.getUser().getUsername(),
                PasswordHasher.hashPassword(request.getUser().getPassword())
        );
        commandManager.addToHistory(request.getCommmandName(), request.getUser());
        ResponseCode responseCode = executeCommand(request.getCommmandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument(), hashedUser);
        return new Response(responseCode, ReponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private synchronized ResponseCode executeCommand(String command, String commandStringArgument,
                                                     Object commandObjectArgument, User user) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add_element(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update_id(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.remove(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                return ResponseCode.CLIENT_EXIT;
            case "add_if_min":
                if (!commandManager.add_if_min(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "add_if_max":
                if (!commandManager.add_if_max(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.HistoryCommand(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "sum_of_price":
                if (!commandManager.sum_of_price(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "count_greater_than_part_number":
                if (!commandManager.count_greater_than_part_number(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "print_field_ascending_price":
                if (!commandManager.print_field_ascending_price(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
//            case "server_exit":
//                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument, user))
//                    return ResponseCode.ERROR;
//                return ResponseCode.SERVER_EXIT;
            case "login":
                if (!commandManager.login(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            case "register":
                if (!commandManager.register(commandStringArgument, commandObjectArgument, user))
                    return ResponseCode.ERROR;
                break;
            default:
                ReponseOutputer.appendln("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}
