package Commands;


import Server.utility.DatabaseCollectionManager;
import Server.utility.models.ProductModel;
import common.Data.Product;
import common.Exceptions.DatabaseHandlingException;
import common.Exceptions.WrongAmountOfElementsException;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;


/**
 * Command 'add'. Adds a new element to collection.
 */
public class add_element extends AbstractCommand {
    private CollectionManager collectionManager;
    DatabaseCollectionManager ddd;

    public add_element(CollectionManager collectionManager, DatabaseCollectionManager ddd) {
        super("add", "{element}", "Add new Element to the Collection");
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

            Product producttoadd =(Product) objectArgument;
            ProductModel addto =collectionManager.addToCollection(ddd.insertProduct(producttoadd,user));
            User owner = addto.getUser();

            if (!user.getUsername().equals(owner.getUsername())) {
                // its not owner, error
                ReponseOutputer.appendln("You are not owner of this product! Access denied.");
                return false;
            }
            //Product producttoadd =(Product) objectArgument;

         //   collectionManager.addToCollection(ddd.insertProduct(producttoadd,user));
            ReponseOutputer.appendln("Product Added!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ReponseOutputer.appenderror("The object submitted by the client is invalid!");
        } catch (DatabaseHandlingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
