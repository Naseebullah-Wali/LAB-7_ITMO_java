package Commands;
import common.Exceptions.*;
//import common.exceptions.UserAlreadyExists;
//import common.exceptions.WrongAmountOfElementsException;
import common.Interaction.User;
import Server.utility.DatabaseUserManager;
import Server.utility.ReponseOutputer;

/**
 * Command 'register'. Allows the user to register.
 */
public class RegisterCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public RegisterCommand(DatabaseUserManager databaseUserManager) {
        super("register", "", "внутренняя команда");
        this.databaseUserManager = databaseUserManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        System.out.println("2");
        try {
            System.out.println("3");
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            System.out.println("4");
            if (databaseUserManager.insertUser(user)) ReponseOutputer.appendln("Пользователь " +
                    user.getUsername() + " зарегистрирован.");
            else throw new UserAlreadyExists();

            System.out.println("2");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: эммм...эээ.это внутренняя команда...");
            ReponseOutputer.appendln("Использование: эммм...эээ.это внутренняя команда...");
        } catch (ClassCastException exception) {
            System.out.println("Переданный клиентом объект неверен!");
            ReponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            System.out.println("Произошла ошибка при обращении к базе данных!");
            ReponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (UserAlreadyExists exception) {
            System.out.println("Пользователь " + user.getUsername() + " уже существует!");
            ReponseOutputer.appenderror("Пользователь " + user.getUsername() + " уже существует!");
        }
        return false;
    }
}
