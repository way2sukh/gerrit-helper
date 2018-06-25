package org.helper.gerrit.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.helper.gerrit.model.GerritProperties;

/**
 * @author Sukhpal Singh
 *
 */
public final class PropertyUtils {
    private PropertyUtils() {
        throw new RuntimeException("No need to intialize");
    }

    public static GerritProperties loadProperties() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("properties/gerrit.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return InstanceHolder.getObjectMapper().convertValue(properties,
                    GerritProperties.class);
        }
    }
}
