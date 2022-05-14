package Commands;
import common.Exceptions.WrongAmountOfElementsException;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class print_field_ascending_price extends AbstractCommand {
    private CollectionManager collectionManager;

    public print_field_ascending_price(CollectionManager collectionManager) {
        super("print_Field_Ascending_price", "", "Show Products of Collection in Ascending Order ");
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
            ReponseOutputer.appendln(collectionManager.showCollection());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
