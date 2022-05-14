package Collection;

import Commands.Commands;
import common.Exceptions.HistoryIsEmptyException;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandManager {
    private static final int Command_HISTORY_SIZE=8;
    private static String[] commandHistory= new String[Command_HISTORY_SIZE];
    private static List<Commands> commands= new ArrayList<>();
    private static Commands add_element;
    private static Commands add_if_max;
    private static Commands add_if_min;
    private static Commands clear;
    private static Commands count_greater_than_part_number;
    private static Commands executerScriptCommand;
    private static Commands exit;
    private static Commands HelpCommand;
    private static Commands info;
    private static Commands print_field_ascending_price;
    private static Commands remove;
    private static Commands save;
    private static Commands show;
    private static Commands sum_of_price;
    private static Commands update_id;
    private static Commands HistoryCommand;
    private static Commands SaveCom;
    private Commands loginCommand;
    private Commands registerCommand;
    private ReadWriteLock historyLocker = new ReentrantReadWriteLock();
    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();


    public CommandManager(Commands add_element,
                          Commands clear,
                          Commands exit,
                          Commands HelpCommand,
                          Commands info,
                          Commands show,
                          Commands print_field_ascending_price,
                          Commands sum_of_price,
                          Commands add_if_max,
                          Commands add_if_min,
                          Commands executerScriptCommand,
                          Commands remove,
                          Commands count_greater_than_part_number,
                          Commands save,
                          Commands update_id,
                          Commands HistoryCommand,
                          Commands Savecom,
                          Commands loginCommand,
                          Commands registerCommand) {
        this.add_element = add_element;
        this.clear = clear;
        this.exit = exit;
        this.HelpCommand = HelpCommand;
        this.info = info;
        this.show = show;
        this.print_field_ascending_price = print_field_ascending_price;
        this.sum_of_price = sum_of_price;
        this.add_if_max = add_if_max;
        this.add_if_min = add_if_min;
        this.executerScriptCommand = executerScriptCommand;
        this.remove = remove;
        this.count_greater_than_part_number = count_greater_than_part_number;
        this.save = save;
        this.update_id = update_id;
        this.HistoryCommand = HistoryCommand;
        this.SaveCom= Savecom;
        this.loginCommand=loginCommand;
        this.registerCommand=registerCommand;



        commands.add(add_element);
        commands.add(clear);
        commands.add(exit);
        commands.add(HelpCommand);
        commands.add(info);
        commands.add(show);
        commands.add(print_field_ascending_price);
        commands.add(sum_of_price);
        commands.add(add_if_max);
        commands.add(add_if_min);
        commands.add(executerScriptCommand);
        commands.add(remove);
        commands.add(count_greater_than_part_number);
        commands.add(save);
        commands.add(update_id);
        commands.add(HistoryCommand);
        commands.add(Savecom);
//        commands.add(loginCommand);
//        commands.add(registerCommand);
    }




    public List<Commands> getCommands(){
        return commands;
    }
    public void addtoHistory(String commandToStore){
        for (Commands command : commands) {
            if (command.getName().equals(commandToStore)) {
                for (int i = Command_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }

    }
    public static boolean help(String stringArgument, Object objectArgument, User user) {
        if (HelpCommand.execute(stringArgument, objectArgument,user)) {
            for (Commands command : commands) {
                ReponseOutputer.appendtable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }
    public static boolean info(String stringArgument, Object objectArgument, User user) {
        return info.execute(stringArgument, objectArgument, user);
    }
    public static boolean add_element(String stringArgument, Object objectArgument, User user) {
        return add_element.execute(stringArgument, objectArgument, user);
    }
    public static boolean add_if_max(String stringArgument, Object objectArgument, User user) {
        return add_if_max.execute(stringArgument, objectArgument, user);
    }
    public static boolean add_if_min(String stringArgument, Object objectArgument, User user) {
        return add_if_min.execute(stringArgument, objectArgument, user);
    }
    public static boolean clear(String stringArgument, Object objectArgument, User user) {
        return clear.execute(stringArgument, objectArgument, user);
    }
    public static boolean count_greater_than_part_number(String stringArgument,Object objectArgument, User user){
        return count_greater_than_part_number.execute(stringArgument,objectArgument, user);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean login(String stringArgument, Object objectArgument, User user) {
        return loginCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean register(String stringArgument, Object objectArgument, User user) {
        return registerCommand.execute(stringArgument, objectArgument, user);
    }
    public void addToHistory(String commandToStore, User user) {
        historyLocker.writeLock().lock();
        try {
            for (Commands command : commands) {
                if (command.getName().equals(commandToStore)) {
                    for (int i = Command_HISTORY_SIZE - 1; i > 0; i--) {
                        commandHistory[i] = commandHistory[i - 1];
                    }
                    commandHistory[0] = commandToStore + " (" + user.getUsername() + ')';
                }
            }
        } finally {
            historyLocker.writeLock().unlock();
        }
    }

    public static boolean executeScript(String stringArgument, Object objectArgument, User user) {
    return executerScriptCommand.execute(stringArgument, objectArgument, user);
    }
    public static boolean exit(String stringArgument, Object objectArgument, User user) {
        return exit.execute(stringArgument, objectArgument, user);
    }
    public static boolean print_field_ascending_price(String stringArgument, Object objectArgument, User user) {
        return print_field_ascending_price.execute(stringArgument, objectArgument, user);
    }
    public static boolean remove(String stringArgument, Object objectArgument, User user) {
        return remove.execute(stringArgument, objectArgument, user);
    }
    public static boolean save(String stringArgument, Object objectArgument, User user) {
        return save.execute(stringArgument, objectArgument, user);
    }
    public static boolean show(String stringArgument, Object objectArgument, User user) {
        return show.execute(stringArgument, objectArgument, user);
    }
    public static boolean sum_of_price(String stringArgument, Object objectArgument, User user) {
        return sum_of_price.execute(stringArgument, objectArgument, user);
    }
    public static boolean update_id(String stringArgument, Object objectArgument, User user) {
        return update_id.execute(stringArgument, objectArgument, user);
    }
    public static boolean HistoryCommand(String stringArgument, Object objectArgument, User user) {
        if (HistoryCommand.execute(stringArgument, objectArgument, user)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ReponseOutputer.appendln("Last used commands:");
                for (String command : commandHistory) {
                    if (command != null) ReponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ReponseOutputer.appendln("NOt any Command used yet!");
            }
        }
        return false;
    }
    public static boolean SaveCom(String stringArgument, Object ObjectArgument,User user){
        return SaveCom.execute(stringArgument,ObjectArgument, user);
    }


}

