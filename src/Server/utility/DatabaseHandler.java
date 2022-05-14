package Server.utility;

import common.utilities.Outputer;
import Server.ServerApp;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

//import org.apache.commons.codec.digest.DigestUtils;
import java.sql.*;

/**
 * A class for handle database.
 */
public class DatabaseHandler {
    // Table names
    public static final String PRODUCT_TABLE = "Products";
    public static final String USER_TABLE = "my_user";
    public static final String COORDINATES_TABLE = "coordinates";
    public static final String MANUFACTURERE_TABLE = "Organizations";
    public static final String ADDRESS_TABLE="address";
    public static final String TOWN_ADDRESS ="Towns ";
    // PRODUCT_TABLE column names
    public static final String PRODUCT_TABLE_ID_COLUMN = "id";
    public static final String PRODUCT_TABLE_NAME_COLUMN = "name";
    public static final String PRODUCT_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String PRODUCT_TABLE_PRICE_COLUMN = "PRICE";
    public static final String PRODUCT_TABLE_PARTNUMBER_COLUMN = "PARTNUMBER";
    public static final String PRODUCT_TABLE_UNIT_OF_MEASURE_COLUMN = "UNIT_OF_MEASURE";
    public static final String SELECT_MANUFACTURER_BY_ID="ORGANIZATION_ID";
    public static final String PRODUCT_TABLE_ADDRESS_ID="ADDRESS_ID";
    public static final String PRODUCT_TABLE_TOWN_ID="TOWN_ID";
//    public static final String MARINE_TABLE_MELEE_WEAPON_COLUMN = "melee_weapon";
//    public static final String MARINE_TABLE_CHAPTER_ID_COLUMN = "chapter_id";
    public static final String PRODUCT_TABLE_USER_ID_COLUMN = "user_id";
    // USER_TABLE column names
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";
    // COORDINATES_TABLE column names
    public static final String COORDINATES_TABLE_ID_COLUMN = "id";
    public static final String COORDINATES_TABLE_PRODUCT_ID_COLUMN = "PRODUCT_ID";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";
    // MANUFACTURERE column names
    public static final String MANUFACTURERE_TABLE_ID_COLUMN = "id";
    public static final String MANUFACTURERE_TABLE_PRODUCT_ID_COLUMN = "PRODUCT_ID";
    public static final String MANUFACTURERE_TABLE_NAME_COLUMN = "name";
    public static final String MANUFACTURERE_TABLE_FULL_NAME_COLUMN = "FULL_NAME";
    public static final String MANUFACTURERE_TABLE_ORGANIZATION_TYPE_COLUMN = "ORGANIZAION_TYPE";
    //ADDRESS COLUMN NAMES
    public static final String ADDRESS_TABLE_ID_COLUMN = "id";
    public static final String ADDRESS_TABLE_PRODUCT_ID_COLUMN = "PRODUCT_ID";
    public static final String ADDRESS_TABLE_STREET_COLUMN = "STREET";
    public static final String ADDRESS_TABLE_ZIPCODE_COLUMN = "ZIPCODE";
    //TOWN COLUMN NAMES
    public static final String TOWN_TABLE_ID_COLUMN = "id";
    public static final String TOWN_TABLE_X_COLUMN = "X";
    public static final String TOWN_TABLE_Y_COLUMN = "Y";
    public static final String TOWN_TABLE_Z_COLUMN = "Z";
    public static final String TOWN_TABLE_NAME_COLUMN = "NAME";


    //public static final String ADDRESS_TABLE__COLUMN = "STREET";
    //public static final String CHAPTER_TABLE_MARINES_COUNT_COLUMN = "marines_count";

    private final String JDBC_DRIVER = "org.postgresql.Driver";

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DatabaseHandler(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        connectToDataBase();
    }

    /**
     * A class for connect to database.
     */
    private void connectToDataBase() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "xay853");
            Outputer.println("Соединение с базой данных установлено.");

           // ServerApp.logger.info("Соединение с базой данных установлено.");
        } catch (SQLException exception) {
            Outputer.printerror("Произошла ошибка при подключении к базе данных!");
            exception.printStackTrace();
           // App.logger.error("Произошла ошибка при подключении к базе данных!");
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("Драйвер управления базой дынных не найден!");
            exception.printStackTrace();
           // App.logger.error("Драйвер управления базой дынных не найден!");
        }

    }

    /**
     * @param sqlStatement SQL statement to be prepared.
     * @param generateKeys Is keys needed to be generated.
     * @return Pprepared statement.
     * @throws SQLException When there's exception inside.
     */
    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            //App.logger.info("Подготовлен SQL запрос '" + sqlStatement + "'.");
            return preparedStatement;
        } catch (SQLException exception) {
            //App.logger.error("Произошла ошибка при подготовке SQL запроса '" + sqlStatement + "'.");
            if (connection == null)
                System.out.println("Cоединение с базой данных не установлено!");//App.logger.error("Соединение с базой данных не установлено!");
            throw new SQLException(exception);
        }
    }

    /**
     * Close prepared statement.
     *
     * @param sqlStatement SQL statement to be closed.
     */
    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
            //App.logger.info("Закрыт SQL запрос '" + sqlStatement + "'.");
        } catch (SQLException exception) {
            //App.logger.error("Произошла ошибка при закрытии SQL запроса '" + sqlStatement + "'.");
        }
    }

    /**
     * Close connection to database.
     */
    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            Outputer.println("Соединение с базой данных разорвано.");
            //ServerApp.logger.info("Соединение с базой данных разорвано.");
        } catch (SQLException exception) {
            Outputer.printerror("Произошла ошибка при разрыве соединения с базой данных!");
           // App.logger.error("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    /**
     * Set commit mode of database.
     */
    public void setCommitMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при установлении режима транзакции базы данных!");
          //  App.logger.error("Произошла ошибка при установлении режима транзакции базы данных!");
        }
    }

    /**
     * Set normal mode of database.
     */
    public void setNormalMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при установлении нормального режима базы данных!");
            //App.logger.error("Произошла ошибка при установлении нормального режима базы данных!");
        }
    }

    /**
     * Commit database status.
     */
    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при подтверждении нового состояния базы данных!");
           // App.logger.error("Произошла ошибка при подтверждении нового состояния базы данных!");
        }
    }

    /**
     * Roll back database status.
     */
    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при возврате исходного состояния базы данных!");
           // App.logger.error("Произошла ошибка при возврате исходного состояния базы данных!");
        }
    }

    /**
     * Set save point of database.
     */
    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при сохранении состояния базы данных!");
            //App.logger.error("Произошла ошибка при сохранении состояния базы данных!");
        }
    }
}
