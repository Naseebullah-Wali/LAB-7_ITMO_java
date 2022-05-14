package Commands;

import common.Exceptions.WrongAmountOfElementsException;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'execute_script'. Executes scripts from a file. Ectually only checks argument and prints messages.
 */
public class executerScriptCommand extends AbstractCommand {
    public executerScriptCommand() {
        super("Execute_script", "<file_name>", "Execute script from specified file");
    }

    /**
     * Executes the command, but partially.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }}