package Commands;
import Server.utility.DatabaseCollectionManager;
import Server.utility.models.ProductModel;
import common.Exceptions.DatabaseHandlingException;
import common.Exceptions.WrongAmountOfElementsException;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'clear'. Clears the collection.
 */
public class clear extends AbstractCommand {
    private CollectionManager collectionManager;
    DatabaseCollectionManager databaseCollectionManager;

    public clear(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "", "For Cleaning all collection");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager=databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            ProductModel pp= collectionManager.clearCollection();
            User owner = pp.getUser();
            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            databaseCollectionManager.clearCollection();
            collectionManager.clearCollection();
            ReponseOutputer.appendln("Collection Cleared!");
            return true;
        } catch (WrongAmountOfElementsException | DatabaseHandlingException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }}
