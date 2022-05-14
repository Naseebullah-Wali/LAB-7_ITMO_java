package Server.utility;

import Server.ServerApp;
import Server.utility.models.ProductModel;
import common.Data.*;
import common.Exceptions.DatabaseHandlingException;
import common.Interaction.User;
import common.utilities.Outputer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeSet;

import static Server.utility.DatabaseHandler.MANUFACTURERE_TABLE_ORGANIZATION_TYPE_COLUMN;

/**
 * Operates the database collection itself.
 */
public class DatabaseCollectionManager {

    // Product_table
    private final String SELECT_ALL_PRODUCTS = "SELECT * FROM " + DatabaseHandler.PRODUCT_TABLE;
    private final String SELECT_PRODUCTS_BY_ID = SELECT_ALL_PRODUCTS + " WHERE " +
            DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_PRODUCT_BY_ID_AND_USER_ID = SELECT_PRODUCTS_BY_ID + " AND " +
            DatabaseHandler.PRODUCT_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_PRODUCT = "INSERT INTO " +
            DatabaseHandler.PRODUCT_TABLE + " (" +
            DatabaseHandler.PRODUCT_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.PRODUCT_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseHandler.PRODUCT_TABLE_PRICE_COLUMN + ", " +
            DatabaseHandler.PRODUCT_TABLE_PARTNUMBER_COLUMN + ", " +
            DatabaseHandler.PRODUCT_TABLE_UNIT_OF_MEASURE_COLUMN + ", " +
            DatabaseHandler.SELECT_MANUFACTURER_BY_ID + ", " +


            //  DatabaseHandler.MARINE_TABLE_MELEE_WEAPON_COLUMN + ", " +
            //  DatabaseHandler.MARINE_TABLE_CHAPTER_ID_COLUMN + ", " +
            DatabaseHandler.PRODUCT_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?::astartes_category," +
            "?::weapon, ?::melee_weapon, ?, ?)";
    private final String DELETE_PRODUCT_BY_ID = "DELETE FROM " + DatabaseHandler.PRODUCT_TABLE +
            " WHERE " + DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_PRODUCT_NAME_BY_ID = "UPDATE " + DatabaseHandler.PRODUCT_TABLE + " SET " +
            DatabaseHandler.PRODUCT_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_PRODUCT_PRICE_BY_ID = "UPDATE " + DatabaseHandler.PRODUCT_TABLE + " SET " +
            DatabaseHandler.PRODUCT_TABLE_PRICE_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_PRODUCT_PARTNUMBER_BY_ID = "UPDATE " + DatabaseHandler.PRODUCT_TABLE + " SET " +
            DatabaseHandler.PRODUCT_TABLE_PARTNUMBER_COLUMN + " = ?::astartes_category" + " WHERE " +
            DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_PRODUCT_UNIT_OF_MEASURE_BY_ID = "UPDATE " + DatabaseHandler.PRODUCT_TABLE + " SET " +
            DatabaseHandler.PRODUCT_TABLE_UNIT_OF_MEASURE_COLUMN + " = ?::weapon" + " WHERE " +
            DatabaseHandler.PRODUCT_TABLE_ID_COLUMN + " = ?";
    //    private final String UPDATE_MARINE_MELEE_WEAPON_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
//            DatabaseHandler.MARINE_TABLE_MELEE_WEAPON_COLUMN + " = ?::melee_weapon" + " WHERE " +
//            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    // COORDINATES_TABLE
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseHandler.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_PRODUCT_ID = SELECT_ALL_COORDINATES +
            " WHERE " + DatabaseHandler.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseHandler.COORDINATES_TABLE + " (" +
            DatabaseHandler.COORDINATES_TABLE_PRODUCT_ID_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?, ?)";
    private final String UPDATE_COORDINATES_BY_PRODUCT_ID = "UPDATE " + DatabaseHandler.COORDINATES_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.COORDINATES_TABLE_PRODUCT_ID_COLUMN + " = ?";
    //Manufacturer_Table
    private final String SELECT_ALL_ORGANIZAION = "SELECT * FROM " + DatabaseHandler.MANUFACTURERE_TABLE;
    private final String SELECT_MANUFACTURER_BY_ID = SELECT_ALL_ORGANIZAION +
            "WHERE " + DatabaseHandler.MANUFACTURERE_TABLE_ID_COLUMN + "= ?";
    private final String INSERT_MANUFACTURER = " INSERT INTO " +
            DatabaseHandler.MANUFACTURERE_TABLE + " (" +
            DatabaseHandler.MANUFACTURERE_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.MANUFACTURERE_TABLE_FULL_NAME_COLUMN + ", " +
            MANUFACTURERE_TABLE_ORGANIZATION_TYPE_COLUMN + ", ";
    private final String UPDATE_ORGANIZATION_NAME_BY_ID = " UPDATE " + DatabaseHandler.MANUFACTURERE_TABLE + "SET " +
            DatabaseHandler.MANUFACTURERE_TABLE_NAME_COLUMN + "= ?" + "WHERE " +
            DatabaseHandler.MANUFACTURERE_TABLE_ID_COLUMN + "= ?";
    private final String UPDATE_ORGANIZATION_FULL_NAME_BY_ID = " UPDATE " + DatabaseHandler.MANUFACTURERE_TABLE + "SET " +
            DatabaseHandler.MANUFACTURERE_TABLE_FULL_NAME_COLUMN + "= ?" + "WHERE " +
            DatabaseHandler.MANUFACTURERE_TABLE_ID_COLUMN + "= ?";
    private final String UPDATE_ORGRANIZATION_TABLE_ORGANIZATION_TYPE_COLUMN_BY_ID = " UPDATE " + DatabaseHandler.MANUFACTURERE_TABLE + "SET " +
            MANUFACTURERE_TABLE_ORGANIZATION_TYPE_COLUMN + "= ?" + "WHERE " +
            DatabaseHandler.MANUFACTURERE_TABLE_ID_COLUMN + "= ?";
    //ADDRESS_TABLE
    private final String SELECT_ALL_ADDRESS = "SELECT * FROM " + DatabaseHandler.ADDRESS_TABLE;
    private final String SELECT_ADDRESS_BY_PRODUCT_ID = SELECT_ALL_ADDRESS +
            " WHERE " + DatabaseHandler.ADDRESS_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_ADDRESS = "INSERT INTO " +
            DatabaseHandler.ADDRESS_TABLE + " (" +
            DatabaseHandler.ADDRESS_TABLE_STREET_COLUMN + ", " +
            DatabaseHandler.ADDRESS_TABLE_ZIPCODE_COLUMN + ", ";
    private final String UPDATE_ADDRESS_TABLE_STREET_BY_PRODUCTID = "UPDATE " + DatabaseHandler.ADDRESS_TABLE + " SET " +
            DatabaseHandler.ADDRESS_TABLE_STREET_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.ADDRESS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ADDRESS_TABLE_ZIPCODE_BY_PRODUCTID = "UPDATE " + DatabaseHandler.ADDRESS_TABLE + " SET " +
            DatabaseHandler.ADDRESS_TABLE_ZIPCODE_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.ADDRESS_TABLE_ID_COLUMN + " = ?";
    //TOWN_TABLE
    private final String SELECT_ALL_TOWN_ADDRESS = "SELECT * FROM " + DatabaseHandler.TOWN_ADDRESS;
    private final String SELECT_TOWN_ADDRESS_BY_PRODUCT_ID = SELECT_ALL_TOWN_ADDRESS +
            " WHERE " + DatabaseHandler.TOWN_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_TOWN_ADDRESS = "INSERT INTO " +
            DatabaseHandler.TOWN_ADDRESS + " (" +
            DatabaseHandler.TOWN_TABLE_X_COLUMN + ", " +
            DatabaseHandler.TOWN_TABLE_Y_COLUMN + ", " +
            DatabaseHandler.TOWN_TABLE_Z_COLUMN + ", " +
            DatabaseHandler.TOWN_TABLE_NAME_COLUMN + ", ";
    private final String UPDATE_TOWN_ADDRESS_BY_PRODUCT_ID = "UPDATE " + DatabaseHandler.TOWN_ADDRESS + " SET " +
            DatabaseHandler.TOWN_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.TOWN_TABLE_Y_COLUMN + " = ?" +
            DatabaseHandler.TOWN_TABLE_Z_COLUMN + " = ?" +
            DatabaseHandler.TOWN_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.TOWN_TABLE_ID_COLUMN + " = ?";

    private DatabaseHandler databaseHandler;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseHandler databaseHandler, DatabaseUserManager databaseUserManager) {
        this.databaseHandler = databaseHandler;
        this.databaseUserManager = databaseUserManager;
    }

    /**
     * Create Marine.
     *
     * @param resultSet Result set parametres of Marine.
     * @return New Marine.
     * @throws SQLException When there's exception inside.
     */
    private Product createProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DatabaseHandler.PRODUCT_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseHandler.PRODUCT_TABLE_NAME_COLUMN);
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseHandler.PRODUCT_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        int Price = resultSet.getInt(DatabaseHandler.PRODUCT_TABLE_PRICE_COLUMN);
        String Partnumber = resultSet.getString(DatabaseHandler.PRODUCT_TABLE_PARTNUMBER_COLUMN);
        UnitOfMeasure unitofmeasure = UnitOfMeasure.valueOf(resultSet.getString(DatabaseHandler.PRODUCT_TABLE_UNIT_OF_MEASURE_COLUMN));
        Coordinates coordinates = getCoordinatesByProductId(id);
        Organization organization = getOrganizaionByProductId(id);
        User owner = databaseUserManager.getUserById(resultSet.getLong(DatabaseHandler.PRODUCT_TABLE_USER_ID_COLUMN));
        return new Product(
                id,
                name,
                creationDate,
                coordinates,
                Price,
                Partnumber,
                unitofmeasure,
                organization
        );
    }

    /**
     * @return List of Marines.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public TreeSet<Product> getCollection() throws DatabaseHandlingException {
        TreeSet<Product> productlist = new TreeSet<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = databaseHandler.getPreparedStatement(SELECT_ALL_PRODUCTS, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                productlist.add(createProduct(resultSet));
            }
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return productlist;
    }


    /**
     * @param productId Id of Product.
     * @return coordinates.
     * @throws SQLException When there's exception inside.
     */
    private Coordinates getCoordinatesByProductId(int productId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedSelectCoordinatesByProductIdStatement = null;
        try {
            preparedSelectCoordinatesByProductIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_COORDINATES_BY_PRODUCT_ID, false);
            preparedSelectCoordinatesByProductIdStatement.setLong(1, productId);
            ResultSet resultSet = preparedSelectCoordinatesByProductIdStatement.executeQuery();
           // ServerApp.logger.info("Выполнен запрос SELECT_COORDINATES_BY_Product_ID.");
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getFloat(DatabaseHandler.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseHandler.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_MARINE_ID!");
            //ServerApp.logger.error("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_MARINE_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectCoordinatesByProductIdStatement);
        }
        return coordinates;
    }

    private Organization getOrganizaionByProductId(long productId) throws SQLException {
        Organization organization;
        PreparedStatement preparedSelectOrganizaionByProductIdStatement = null;
        try {
            preparedSelectOrganizaionByProductIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_MANUFACTURER_BY_ID, false);
            preparedSelectOrganizaionByProductIdStatement.setLong(1, productId);
            ResultSet resultSet = preparedSelectOrganizaionByProductIdStatement.executeQuery();
          //  ServerApp.logger.info("Выполнен запрос SELECT_COORDINATES_BY_Product_ID.");
            if (resultSet.next()) {
                int id = resultSet.getInt(DatabaseHandler.MANUFACTURERE_TABLE_ID_COLUMN);
                String name = resultSet.getString(DatabaseHandler.MANUFACTURERE_TABLE_NAME_COLUMN);
                String fullname = resultSet.getString(DatabaseHandler.MANUFACTURERE_TABLE_FULL_NAME_COLUMN);
                OrganizationType o = OrganizationType.valueOf(resultSet.getString(MANUFACTURERE_TABLE_ORGANIZATION_TYPE_COLUMN));
                Address address = getAddressById(id);
                organization = new Organization(
                        id,
                        name,
                        fullname,
                        o,
                        address
                );

            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_MARINE_ID!");
            //ServerApp.logger.error("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_MARINE_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectOrganizaionByProductIdStatement);
        }
        return organization;
    }

    private Address getAddressById(long chapterId) throws SQLException {
        Address address;
        PreparedStatement preparedSelectAddressByIdStatement = null;
        try {
            preparedSelectAddressByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_ADDRESS_BY_PRODUCT_ID, false);
            preparedSelectAddressByIdStatement.setLong(1, chapterId);
            ResultSet resultSet = preparedSelectAddressByIdStatement.executeQuery();
           // ServerApp.logger.info("Выполнен запрос SELECT_ADDRESS_BY_ID.");
            if (resultSet.next()) {
                address = new Address(
                        resultSet.getString(DatabaseHandler.ADDRESS_TABLE_STREET_COLUMN),
                        resultSet.getString(DatabaseHandler.ADDRESS_TABLE_ZIPCODE_COLUMN),
                        getTownById(resultSet.getInt(DatabaseHandler.TOWN_TABLE_ID_COLUMN))
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            //  App.logger.error("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAddressByIdStatement);
        }
        return address;
    }

    private Location getTownById(int chapterId) throws SQLException {
        Location location;
        PreparedStatement preparedSelectTownByIdStatement = null;
        try {
            preparedSelectTownByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_TOWN_ADDRESS_BY_PRODUCT_ID, false);
            preparedSelectTownByIdStatement.setLong(1, chapterId);
            ResultSet resultSet = preparedSelectTownByIdStatement.executeQuery();
          //  ServerApp.logger.info("Выполнен запрос SELECT_TOWN_ADDRESS_BY_ID.");
            if (resultSet.next()) {
                location = new Location(
                        resultSet.getDouble(DatabaseHandler.TOWN_TABLE_X_COLUMN),
                        resultSet.getInt(DatabaseHandler.TOWN_TABLE_Y_COLUMN),
                        resultSet.getFloat(DatabaseHandler.TOWN_TABLE_Z_COLUMN),
                        resultSet.getString(DatabaseHandler.TOWN_TABLE_NAME_COLUMN)
                        //resultSet.getString(DatabaseHandler.ADDRESS_TABLE_STREET_COLUMN),
                        //  resultSet.getString(DatabaseHandler.ADDRESS_TABLE_ZIPCODE_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            //  App.logger.error("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectTownByIdStatement);
        }
        return location;
    }


    /**
     * @param product Marine raw.
     * @param user    User.
     * @return Marine.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public ProductModel insertProduct(Product product, User user) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        Product product1;
        PreparedStatement preparedInsertProductStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertOrganizaionStatement = null;
        PreparedStatement preparedInsertAddressStatement = null;
        PreparedStatement preparedInsertLocaionStatement = null;

        //PreparedStatement preparedInsertChapterStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertProductStatement = databaseHandler.getPreparedStatement(INSERT_PRODUCT, true);
            preparedInsertCoordinatesStatement = databaseHandler.getPreparedStatement(INSERT_COORDINATES, true);
            preparedInsertOrganizaionStatement = databaseHandler.getPreparedStatement(INSERT_MANUFACTURER, true);
            preparedInsertAddressStatement = databaseHandler.getPreparedStatement(INSERT_ADDRESS, true);
            preparedInsertLocaionStatement = databaseHandler.getPreparedStatement(INSERT_TOWN_ADDRESS, true);
            // preparedInsertChapterStatement = databaseHandler.getPreparedStatement(INSERT_CHAPTER, true);

//            preparedInsertChapterStatement.setString(1, product.getChapter().getName());
//            preparedInsertChapterStatement.setLong(2, product.getChapter().getMarinesCount());
//            if (preparedInsertChapterStatement.executeUpdate() == 0) throw new SQLException();
//            ResultSet generatedChapterKeys = preparedInsertChapterStatement.getGeneratedKeys();
//            long chapterId;
//            if (generatedChapterKeys.next()) {
//                chapterId = generatedChapterKeys.getLong(1);
//            } else throw new SQLException();
//            ServerApp.logger.info("Выполнен запрос INSERT_CHAPTER.");

            preparedInsertProductStatement.setString(1, product.getName());
            preparedInsertProductStatement.setTimestamp(2, Timestamp.valueOf(creationTime));
            preparedInsertProductStatement.setInt(3, product.getPrice());
            preparedInsertProductStatement.setString(4, product.getPartNumber().toString());
            preparedInsertProductStatement.setString(5, product.getUnitOfMeasure().toString());
            preparedInsertOrganizaionStatement.setString(6, product.getManufacturer().toString());
            preparedInsertAddressStatement.setString(7,product.getOfficialAddress().toString());
            preparedInsertLocaionStatement.setString(8,product.getManufacturer().getOfficialAddress().getTown().getName());
            // preparedInsertProductStatement.setString(6, product.getMeleeWeapon().toString());
            // preparedInsertProductStatement.setLong(7, chapterId);
            preparedInsertProductStatement.setLong(7, databaseUserManager.getUserIdByUsername(user));
            if (preparedInsertProductStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedProductKeys = preparedInsertProductStatement.getGeneratedKeys();
            int ProductId;
            if (generatedProductKeys.next()) {
                ProductId = generatedProductKeys.getInt(1);
            } else throw new SQLException();
            //ServerApp.logger.info("Выполнен запрос INSERT_PRODUCT.");

            preparedInsertCoordinatesStatement.setLong(1, ProductId);
            preparedInsertCoordinatesStatement.setDouble(2, product.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setFloat(3, (float) product.getCoordinates().getY());
            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) throw new SQLException();
           // ServerApp.logger.info("Выполнен запрос INSERT_COORDINATES.");

            product1 = new Product(

                    ProductId,
                    product.getName(),
                    creationTime,
                    product.getCoordinates(),
                    product.getPrice(),
                    product.getPartNumber(),
                    product.getUnitOfMeasure(),
                    product.getManufacturer()
            );
            databaseHandler.commit();
            return new ProductModel(product1, user);
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении группы запросов на добавление нового объекта!");
            //App.logger.error("Произошла ошибка при выполнении группы запросов на добавление нового объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertProductStatement);
            databaseHandler.closePreparedStatement(preparedInsertCoordinatesStatement);
            //  databaseHandler.closePreparedStatement(preparedInsertChapterStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * @param productId Id of Marine.
     * @param product   Marine raw.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void updateProductById(int productId, ProductModel product) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        PreparedStatement preparedUpdateProductNameByIdStatement = null;
        PreparedStatement preparedUpdateProductPriceByIdStatement = null;
        PreparedStatement preparedUpdateProductPartnumberByIdStatement = null;
        PreparedStatement preparedUpdateProductUnitOfMearsureByIdStatement = null;
        PreparedStatement preparedUpdateProductOrganizationNameByIdStatement = null;
        PreparedStatement preparedUpdateProductOrganizationFullNameByIdStatement = null;
        PreparedStatement preparedUpdateProductOrganizationTypeByIdStatement = null;
        PreparedStatement preparedUpdateProductAddreeStreetByIdStatement = null;
        PreparedStatement preparedUpdateProductAddreeZipcodeByIdStatement = null;
        PreparedStatement preparedUpdateProductLocationTownByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByProductIdStatement = null;
//        PreparedStatement preparedUpdateProductAddressByIdStatement = null;
//        PreparedStatement preparedUpdateProductLocationByIdStatement = null;

        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            preparedUpdateProductNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_PRODUCT_NAME_BY_ID, false);
            preparedUpdateProductPriceByIdStatement = databaseHandler.getPreparedStatement(UPDATE_PRODUCT_PRICE_BY_ID, false);
            preparedUpdateProductPartnumberByIdStatement = databaseHandler.getPreparedStatement(UPDATE_PRODUCT_PARTNUMBER_BY_ID, false);
            preparedUpdateProductUnitOfMearsureByIdStatement = databaseHandler.getPreparedStatement(UPDATE_PRODUCT_UNIT_OF_MEASURE_BY_ID, false);
            preparedUpdateProductOrganizationNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_ORGANIZATION_NAME_BY_ID, false);
            preparedUpdateProductOrganizationFullNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_ORGANIZATION_FULL_NAME_BY_ID, false);
            preparedUpdateProductOrganizationTypeByIdStatement = databaseHandler.getPreparedStatement(UPDATE_ORGRANIZATION_TABLE_ORGANIZATION_TYPE_COLUMN_BY_ID, false);
            preparedUpdateProductAddreeStreetByIdStatement = databaseHandler.getPreparedStatement(UPDATE_ADDRESS_TABLE_STREET_BY_PRODUCTID, false);
            preparedUpdateProductAddreeZipcodeByIdStatement = databaseHandler.getPreparedStatement(UPDATE_ADDRESS_TABLE_ZIPCODE_BY_PRODUCTID, false);
            preparedUpdateProductLocationTownByIdStatement = databaseHandler.getPreparedStatement(UPDATE_TOWN_ADDRESS_BY_PRODUCT_ID, false);
            preparedUpdateCoordinatesByProductIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATES_BY_PRODUCT_ID, false);


            //   preparedUpdateMarineMeleeWeaponByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_MELEE_WEAPON_BY_ID, false);
            //   preparedUpdateChapterByIdStatement = databaseHandler.getPreparedStatement(UPDATE_CHAPTER_BY_ID, false);

            if (product.getProduct().getName() != null) {
                preparedUpdateProductNameByIdStatement.setString(1, product.getProduct().getName());
                preparedUpdateProductNameByIdStatement.setLong(2, productId);
                if (preparedUpdateProductNameByIdStatement.executeUpdate() == 0) throw new SQLException();
              //  ServerApp.logger.info("Выполнен запрос UPDATE_MARINE_NAME_BY_ID.");
            }
            if (product.getProduct().getCoordinates() != null) {
                preparedUpdateCoordinatesByProductIdStatement.setDouble(1, product.getProduct().getCoordinates().getX());
                preparedUpdateCoordinatesByProductIdStatement.setFloat(2, (float) product.getProduct().getCoordinates().getY());
                preparedUpdateCoordinatesByProductIdStatement.setLong(3, productId);
                if (preparedUpdateCoordinatesByProductIdStatement.executeUpdate() == 0) throw new SQLException();
               // ServerApp.logger.info("Выполнен запрос UPDATE_COORDINATES_BY_MARINE_ID.");
            }
            if (product.getProduct().getPrice() != -1) {
                preparedUpdateProductPriceByIdStatement.setDouble(1, product.getProduct().getPrice());
                preparedUpdateProductPriceByIdStatement.setLong(2, productId);
                if (preparedUpdateProductPriceByIdStatement.executeUpdate() == 0) throw new SQLException();
               // ServerApp.logger.info("Выполнен запрос UPDATE_MARINE_HEALTH_BY_ID.");
            }
            if (product.getProduct().getPartNumber() != null) {
                preparedUpdateProductPartnumberByIdStatement.setString(1, product.getProduct().getPartNumber().toString());
                preparedUpdateProductPartnumberByIdStatement.setLong(2, productId);
                if (preparedUpdateProductPartnumberByIdStatement.executeUpdate() == 0) throw new SQLException();
            //    ServerApp.logger.info("Выполнен запрос UPDATE_MARINE_CATEGORY_BY_ID.");
            }
            if (product.getProduct().getUnitOfMeasure() != null) {
                preparedUpdateProductUnitOfMearsureByIdStatement.setString(1, product.getProduct().getUnitOfMeasure().toString());
                preparedUpdateProductUnitOfMearsureByIdStatement.setLong(2, productId);
                if (preparedUpdateProductUnitOfMearsureByIdStatement.executeUpdate() == 0) throw new SQLException();
            //    ServerApp.logger.info("Выполнен запрос UPDATE_MARINE_WEAPON_TYPE_BY_ID.");
            }
            if (product.getProduct().getManufacturer() != null) {
                Organization organization = null;
                preparedUpdateProductOrganizationNameByIdStatement.setString(1, organization.getName());
                preparedUpdateProductOrganizationFullNameByIdStatement.setString(2, organization.getFullname());
                preparedUpdateProductOrganizationTypeByIdStatement.setString(3, String.valueOf(organization.getType()));
//                preparedUpdateCoordinatesByProductIdStatement.setFloat(2, (float) product.getCoordinates().getY());
//                preparedUpdateCoordinatesByProductIdStatement.setLong(3, productId);
                if (preparedUpdateCoordinatesByProductIdStatement.executeUpdate() == 0) throw new SQLException();
             //   ServerApp.logger.info("Выполнен запрос UPDATE_ORGANIZATION_BY_ID.");
            }
            if (product.getProduct().getOfficialAddress() != null) {
                Address address = null;
                preparedUpdateProductAddreeStreetByIdStatement.setString(1, address.getStreet());
                preparedUpdateProductAddreeZipcodeByIdStatement.setString(2, address.getZipcode());
                if (preparedUpdateCoordinatesByProductIdStatement.executeUpdate() == 0) throw new SQLException();
              //  ServerApp.logger.info("Выполнен запрос UPDATE_ADRESS_BY_ID.");
            }

            if (product.getProduct().getOfficialAddress() != null) {
                Location location = null;
                preparedUpdateProductLocationTownByIdStatement.setDouble(1, location.getX());
                preparedUpdateProductLocationTownByIdStatement.setInt(2, location.getY());
                preparedUpdateProductLocationTownByIdStatement.setFloat(3, location.getZ());
                preparedUpdateProductLocationTownByIdStatement.setString(4, location.getName());
                if (preparedUpdateCoordinatesByProductIdStatement.executeUpdate() == 0) throw new SQLException();
            //    ServerApp.logger.info("Выполнен запрос UPDATE_LOCATION_BY_ID.");
            }

            databaseHandler.commit();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            // App.logger.error("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedUpdateProductNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductPriceByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductPartnumberByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductUnitOfMearsureByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductOrganizationNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductOrganizationFullNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductOrganizationTypeByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductAddreeStreetByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductAddreeZipcodeByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateProductLocationTownByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesByProductIdStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * Delete Marine by id.
     *
     * @param ProductId Id of Marine.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void deleteProductById(long ProductId) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        PreparedStatement preparedDeleteChapterByIdStatement = null;
        try {
            // preparedDeleteChapterByIdStatement = databaseHandler.getPreparedStatement(DELETE_CHAPTER_BY_ID, false);
            //  preparedDeleteChapterByIdStatement.setLong(1, getChapterIdByProductId(marineId));
            if (preparedDeleteChapterByIdStatement.executeUpdate() == 0) Outputer.println(3);
         //   ServerApp.logger.info("Выполнен запрос DELETE_CHAPTER_BY_ID.");
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE_CHAPTER_BY_ID!");
            // App.logger.error("Произошла ошибка при выполнении запроса DELETE_CHAPTER_BY_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedDeleteChapterByIdStatement);
        }
    }

    /**
     * Checks Products user id.
     *
     * @param productId Id of Product.
     * @param user      Owner of Product.
     * @return Is everything ok.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean checkProductUserId(long productId, User user) throws DatabaseHandlingException {
        PreparedStatement preparedSelectProductByIdAndUserIdStatement = null;
        try {
            preparedSelectProductByIdAndUserIdStatement = databaseHandler.getPreparedStatement(SELECT_PRODUCT_BY_ID_AND_USER_ID, false);
            preparedSelectProductByIdAndUserIdStatement.setLong(1, productId);
            preparedSelectProductByIdAndUserIdStatement.setLong(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedSelectProductByIdAndUserIdStatement.executeQuery();
         //   ServerApp.logger.info("Выполнен запрос SELECT_PRODUCT_BY_ID_AND_USER_ID.");
            return resultSet.next();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_PRODUCT_BY_ID_AND_USER_ID!");
            //App.logger.error("Произошла ошибка при выполнении запроса SELECT_MARINE_BY_ID_AND_USER_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectProductByIdAndUserIdStatement);
        }

    }

    /**
     * Clear the collection.
     *
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void clearCollection() throws DatabaseHandlingException {
        TreeSet<Product> productlist = getCollection();
        for (Product product : productlist) {
            deleteProductById(product.getId());
        }
    }
}
