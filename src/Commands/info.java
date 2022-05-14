package Commands;


import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Exceptions.WrongAmountOfElementsException;
//import Server.utility.CollectionManager;
import common.Interaction.User;

import java.time.LocalDateTime;

/**
 * Command 'info'. Prints information about the collection.
 */
public class info extends AbstractCommand {
    private CollectionManager collectionManager;

    public info(CollectionManager collectionManager) {
        super("info", "", "display information about a collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "no initialization has occurred in this session yet" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "no save has occurred in this session yet" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            ReponseOutputer.appendln("Collection details:");
            ReponseOutputer.appendln(" Type: " + collectionManager.collectionType());
            ReponseOutputer.appendln(" Amount of elements: " + collectionManager.collectionSize());
            ReponseOutputer.appendln(" Last save date: " + lastSaveTimeString);
            ReponseOutputer.appendln(" Last initialization date: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
