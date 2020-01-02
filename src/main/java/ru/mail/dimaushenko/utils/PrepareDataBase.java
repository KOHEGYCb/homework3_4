package ru.mail.dimaushenko.utils;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.constants.ErrorConstants;
import ru.mail.dimaushenko.constants.PropertyConstants;
import ru.mail.dimaushenko.repository.connectionPool.ConnectionPool;
import ru.mail.dimaushenko.repository.dao.CarModelDAO;
import ru.mail.dimaushenko.repository.model.CarModelEnum;

public class PrepareDataBase {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final String DATABASE_NAME = "jd2_homework3_4";

    private static final String TABLE_NAME_CAR = "car";
    private static final String TABLE_NAME_CAR_MODEL = "car_model";

    private static final String SQL_REQUEST_SHOW_DB = "SHOW DATABASES;";
    private static final String SQL_REQUEST_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_REQUEST_CREATE_DB = "CREATE DATABASE " + DATABASE_NAME + ";";
    private static final String SQL_REQUEST_CREATE_TABLE_CAR = "CREATE TABLE car(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR (50) NULL, id_car_model INT NULL, engine_capacity INT NULL);";
    private static final String SQL_REQUEST_CREATE_TABLE_CAR_MODEL = "CREATE TABLE car_model(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR (50) NULL);";

    private static final String SQL_COLUMN_DATABASE = "Database";
    private static final String SQL_COLUMN_DATABASE_TABLES = "Tables_in_" + DATABASE_NAME;

    public static void prepareDB() {
        boolean isDBFound = false;

        String url = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_URL_MYSQL);
        String user = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_USERNAME);
        String password = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_PASSWORD);

        try (
                Connection connection = ConnectionPool.getINSTANCE().getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_SHOW_DB);
                ResultSet result = statement.executeQuery();) {
            while (result.next()) {
                if (result.getString(SQL_COLUMN_DATABASE).equals(DATABASE_NAME)) {
                    isDBFound = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }

        if (!isDBFound) {
            try (
                    Connection connection = ConnectionPool.getINSTANCE().getConnection(url, user, password);
                    PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_CREATE_DB);) {
                statement.execute();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
            }
        }

        boolean isFoundTableCar = false;
        boolean isFoundTableCarModel = false;

        try (
                Connection connection = ConnectionPool.getINSTANCE().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_SHOW_TABLES);
                ResultSet result = statement.executeQuery();) {
            while (result.next()) {
                if (result.getString(SQL_COLUMN_DATABASE_TABLES).equals(TABLE_NAME_CAR)) {
                    isFoundTableCar = true;
                }
                if (result.getString(SQL_COLUMN_DATABASE_TABLES).equals(TABLE_NAME_CAR_MODEL)) {
                    isFoundTableCarModel = true;
                }
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }

        if (!isFoundTableCar) {
            try (
                    Connection connection = ConnectionPool.getINSTANCE().getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_CREATE_TABLE_CAR);) {
                statement.execute();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
            }
        }
        if (!isFoundTableCarModel) {
            try (
                    Connection connection = ConnectionPool.getINSTANCE().getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_CREATE_TABLE_CAR_MODEL);) {
                statement.execute();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
            }
            for (CarModelEnum value : CarModelEnum.values()) {
                CarModelDAO.getINSTANCE().addEntity(value);
            }
        }

    }

}
