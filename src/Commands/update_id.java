package Commands;
import Server.utility.DatabaseCollectionManager;
import Server.utility.models.ProductModel;
import common.Exceptions.DatabaseHandlingException;
import common.Exceptions.WrongAmountOfElementsException;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class update_id extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public update_id(CollectionManager collectionManager,DatabaseCollectionManager databaseCollectionManager) {
        super("Update_id", "{element}", "Use For UPdate a Product");
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
            int id = Integer.parseInt(stringArgument);
            ProductModel update= (ProductModel) objectArgument;

            databaseCollectionManager.updateProductById(id,update);
            User owner= update.getUser();
            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException("stringArgument.isEmpty(): " + stringArgument.isEmpty() + ", objectArgument == null: " + (objectArgument == null));
//            int id = Long.parseLong(stringArgument);
             ProductModel productToAdd = (ProductModel) objectArgument;
//            ProductModel existingProduct = collectionManager.getById(Math.toIntExact(id));
            if (update != null) {
                collectionManager.removeFromCollection(update);
                collectionManager.addToCollection(productToAdd);
                return true;
            } else {
                return false;
            }
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ReponseOutputer.appenderror("Invide object request from client !");
        } catch (DatabaseHandlingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
