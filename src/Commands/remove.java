package Commands;

import Collection.CollectionManager;
import Server.utility.DatabaseCollectionManager;
import Server.utility.DatabaseHandler;
import Server.utility.models.ProductModel;
import common.Data.Product;
import common.Exceptions.CollectionIsEmptyException;
import common.Exceptions.ProductNotFoundException;
import common.Exceptions.WrongAmountOfElementsException;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

public class remove extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public remove(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove", "<ID>", "Delet Element from Collection By ID");
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
            ProductModel producttoremov=(ProductModel) objectArgument;
            collectionManager.removeFromCollection(producttoremov);
            User owner = producttoremov.getUser();
            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            ProductModel producttoremove = collectionManager.getById(Math.toIntExact(id));
            if (producttoremove == null) throw new ProductNotFoundException();
            collectionManager.removeFromCollection(producttoremove);
            ReponseOutputer.appendln("Product removed Successfully!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ReponseOutputer.appenderror("Collection is Empty!");
        } catch (NumberFormatException exception) {
            ReponseOutputer.appenderror("ID must be represented by a number!");
        } catch (ProductNotFoundException exception) {
            ReponseOutputer.appenderror("Product with such ID not aviable!");
        }


        return false;
    }
}
