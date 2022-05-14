package Commands;

import common.Exceptions.CollectionIsEmptyException;
import common.Exceptions.WrongAmountOfElementsException;
import Collection.CollectionManager;
import Server.utility.ReponseOutputer;
import common.Interaction.User;

/**
 * Command 'sum of price'.
 */
public class sum_of_price extends AbstractCommand {
    private CollectionManager collectionManager;

    public sum_of_price(CollectionManager collectionManager) {
        super("sum_of_price ", "", "Show the sum of all Prices");
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
            double sum_of_health = collectionManager.getSumOfPrice();
            if (sum_of_health == 0) throw new CollectionIsEmptyException();
            ReponseOutputer.appendln("Sum of Prices of All Product: " + sum_of_health);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ReponseOutputer.appenderror("Empty Collection!");
        }
        return false;
    }
}

