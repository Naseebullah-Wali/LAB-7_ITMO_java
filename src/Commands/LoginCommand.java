package Commands;

import common.Exceptions.*;
//import common.exceptions.UserIsNotFoundException;
//import common.exceptions.WrongAmountOfElementsException;
import common.Interaction.User;
import Server.utility.DatabaseUserManager;
import Server.utility.ReponseOutputer;

/**
 * Command 'login'. Allows the user to login.
 */
public class LoginCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public LoginCommand(DatabaseUserManager databaseUserManager) {
        super("login", "", "внутренняя команда");
        this.databaseUserManager = databaseUserManager;
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
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) ReponseOutputer.appendln("Пользователь " +
                    user.getUsername() + " авторизован.");
            else throw new UserIsNotFoundException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ReponseOutputer.appendln("Использование: эммм...эээ.это внутренняя команда...");
        } catch (ClassCastException exception) {
            ReponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ReponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (UserIsNotFoundException exception) {
            ReponseOutputer.appenderror("Неправильные имя пользователя или пароль!");
        }
        return false;
    }
}