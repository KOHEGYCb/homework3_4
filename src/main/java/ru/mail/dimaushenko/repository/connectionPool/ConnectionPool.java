package ru.mail.dimaushenko.repository.connectionPool;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.constants.ErrorConstants;
import ru.mail.dimaushenko.constants.PropertyConstants;
import ru.mail.dimaushenko.utils.PropertyUtil;

public class ConnectionPool {

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionPool() {
    }

    public static ConnectionPool getINSTANCE() {
        return INSTANCE;
    }

    public Connection getConnection() {
        String url = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_URL);
        String user = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_USERNAME);
        String password = PropertyUtil.getINSTANCE().getProperty(PropertyConstants.CONFIG_FILE_MYSQL, PropertyConstants.MYSQL_PROP_PASSWORD);
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
    }
    
    public Connection getConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_NOT_CONMECTION_TO_DB);
        }
    }
}
