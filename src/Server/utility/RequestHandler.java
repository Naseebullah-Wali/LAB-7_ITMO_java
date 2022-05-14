//package Server.utility;
//
//import Collection.CommandManager;
//import Interaction.Request;
//import Interaction.Response;
//import Interaction.ResponseCode;
//import Server.ServerApp;
//
//public class RequestHandler {
//    private CommandManager commandManager;
//
//    public RequestHandler(CommandManager commandManager) {
//        this.commandManager = commandManager;
//    }
//
//    //  For Handling Requests
//
//    public Response handle(Request request) {
//        commandManager.addtoHistory(request.getCommmandName());
//        ResponseCode responseCode = executeCommand(
//                request.getCommmandName(),
//                request.getCommandStringArgument(),
//                request.getCommandObjectArgument()
//        );
//        return new Response(responseCode, ReponseOutputer.getAndClear());
//    }
//
//    private ResponseCode executeCommand(String command, String commandStringArgument, Object commandObjectArgument) {
//        switch (command) {
//            case "":
//                break;
//            case "help":
//                if (!CommandManager.help(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "info":
//                if (!CommandManager.info(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                    break;
//            case "add":
//                if(!CommandManager.add_element(commandStringArgument,commandObjectArgument))
//                return ResponseCode.ERROR;
//                break;
//
//            case "add_if_max":
//                if(!CommandManager.add_if_max(commandStringArgument,commandObjectArgument))
//            return ResponseCode.ERROR;
//                break;
//            case "add_if_min":
//                if (!CommandManager.add_if_min(commandStringArgument,commandObjectArgument)) {
//                    ServerApp.logger.info("add if min error");
//                    return ResponseCode.ERROR;
//                } else {
//                    ServerApp.logger.info("add if min ok");
//                }
//                break;
//            case "clear":
//                if (!CommandManager.clear(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "count_greater_than_part_number":
//                if (!CommandManager.count_greater_than_part_number(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "exit":
//                if (!CommandManager.exit(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "print_field_ascending_price":
//                if (!CommandManager.print_field_ascending_price(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "remove":
//               if(!CommandManager.remove(commandStringArgument,commandObjectArgument))
//                   return ResponseCode.ERROR;
//               break;
//            case "Show":
//                if (!CommandManager.show(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "sum_of_price":
//                if (!CommandManager.sum_of_price(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "update_id":
//                if(! CommandManager.update_id(commandStringArgument,commandObjectArgument))
//                return ResponseCode.ERROR;
//                break;
//            case "save":
//                if (!CommandManager.save(commandStringArgument,commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "Execute_script":
//                if (!CommandManager.executeScript(commandStringArgument, commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
//            case "history":
//                if(!CommandManager.HistoryCommand(commandStringArgument,commandObjectArgument))
//                    break;
//                    return ResponseCode.ERROR;
//
//            case "SaveCom":
//                if(!CommandManager.SaveCom(commandStringArgument,commandObjectArgument))
//                return ResponseCode.ERROR;
//                return ResponseCode.SERVER_SAVE;
//
//
//
//            default:
//                System.out.println("No such Command");
//                return ResponseCode.ERROR;
//        }
//        return ResponseCode.OK;
//    }
//
//}
//
//
