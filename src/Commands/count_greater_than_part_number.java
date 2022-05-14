package Commands;
import common.Data.Product;
import common.Exceptions.CollectionIsEmptyException;
import common.Exceptions.WrongAmountOfElementsException;
//import Interaction.ProductRaw;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;


public class count_greater_than_part_number extends AbstractCommand {
    private CollectionManager collectionManager;

    public count_greater_than_part_number(CollectionManager collectionManager) {
        super("count_greater_than_part_number", "", "Show the sum of all Prices");
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
                double sum_of_price = collectionManager.getSumOfPrice();
                if (sum_of_price == 0) throw new CollectionIsEmptyException();
                ReponseOutputer.appendln("Sum of Prices of All Product: " + sum_of_price);
                return true;
            } catch (WrongAmountOfElementsException exception) {
                ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
            } catch (CollectionIsEmptyException exception) {
                ReponseOutputer.appenderror("Empty Collection!");
            }
            return false;
        }
        }

