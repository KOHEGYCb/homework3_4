package ru.mail.dimaushenko.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.constants.ErrorConstants;

public class PropertyUtil {

    private static final PropertyUtil INSTANCE = new PropertyUtil();

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private PropertyUtil() {
    }

    public static PropertyUtil getINSTANCE() {
        return INSTANCE;
    }

    public String getProperty(String configFile, String propertyName) {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(configFile)) {
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_PROP_FILE_IS_NOT_FOUND);
        }
        return properties.getProperty(propertyName);
    }

}
