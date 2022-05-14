package Commands;

import Collection.CollectionManager;
import Server.utility.DatabaseCollectionManager;
import Server.utility.models.ProductModel;
import common.Data.Product;
import common.Exceptions.DatabaseHandlingException;
import common.Exceptions.WrongAmountOfElementsException;
import Server.ServerApp;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'add_if_mix'. Adds a new element to collection if it's more than the minimal one.
 */
public class add_if_max extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager ddd;

    public add_if_max(CollectionManager collectionManager, DatabaseCollectionManager ddd) {
        super("add_if_max", "{element}", "Add a new element if its value is bigger than the smallest one");
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
            ProductModel maxInCollectionModel = collectionManager.getWithMaxPrice();
            User owner = maxInCollectionModel.getUser();
            Product product = maxInCollectionModel.getProduct();
            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            if (collectionManager.collectionSize() == 0 || productToAdd.getPrice() < product.getPrice()) {

         //       ServerApp.logger.info(collectionManager.getWithMaxPrice().toString());
         //       ServerApp.logger.info(productToAdd.toString());
                collectionManager.addToCollection(ddd.insertProduct(productToAdd, user));
          //      ServerApp.logger.info("Successfully added!");
                ReponseOutputer.appendln("Successfully added! ");

                return true;
            } else {
//                ServerApp.logger.info("collectionManager.getFirst()) <= 0 " + productToAdd.compareTo(collectionManager.getWithMinPrice()));
//                ReponseOutputer.appendln("collectionManager.getFirst()) <= 0 " + productToAdd.compareTo(collectionManager.getWithMinPrice()));
            }
//            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
//            Product productToAdd = (Product) objectArgument;
//
//            if (collectionManager.collectionSize() == 0 || productToAdd.compareTo(collectionManager.getWithMinPrice()) < 0) {
//                collectionManager.addToCollection(productToAdd);
//                ReponseOutputer.appendln("Added succesfully!");
//                return true;
//            } else
//                ReponseOutputer.appenderror("The Product price value is greater than the value of the smallest product price!");
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("using: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ReponseOutputer.appenderror("The object submitted by the client is invalid!");
        } catch (DatabaseHandlingException e) {

        }
        return false;
    }
}
