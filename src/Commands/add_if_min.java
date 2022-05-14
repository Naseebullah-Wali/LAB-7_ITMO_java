package Commands;
import Server.utility.DatabaseCollectionManager;
import Server.utility.DatabaseHandler;
import Server.utility.models.ProductModel;
import common.Data.Product;
import common.Exceptions.DatabaseHandlingException;
import common.Exceptions.WrongAmountOfElementsException;

import Collection.CollectionManager;
import Server.ServerApp;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class add_if_min extends AbstractCommand {
    private CollectionManager collectionManager;
    DatabaseCollectionManager ddd;

    public add_if_min(CollectionManager collectionManager, DatabaseCollectionManager ddd) {
        super("add_if_min", "{element}", "add a new element if its value is smaller than the smallest!");
        this.collectionManager = collectionManager;
        this.ddd=ddd;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Product productToAdd = (Product) objectArgument;
            ProductModel minInCollection = collectionManager.getWithMinPrice();
            User owner = minInCollection.getUser();
            Product product = minInCollection.getProduct();
            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            if (collectionManager.collectionSize() == 0 || productToAdd.getPrice() < product.getPrice()) {

              //  ServerApp.logger.info(collectionManager.getWithMinPrice().toString());
              //  ServerApp.logger.info(productToAdd.toString());
                collectionManager.addToCollection(ddd.insertProduct(productToAdd,user));
              //  ServerApp.logger.info("Successfully added!");
                ReponseOutputer.appendln("Successfully added! ");

                return true;
            } else {
             //   ServerApp.logger.info("collectionManager.getFirst()) <= 0 " + productToAdd.compareTo(collectionManager.getWithMinPrice()));
            ReponseOutputer.appendln("collectionManager.getFirst()) <= 0 " + productToAdd.compareTo(collectionManager.getWithMinPrice()));
            }
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("using: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ReponseOutputer.appenderror("The object submitted by the client is invalid!");
        } catch (DatabaseHandlingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
