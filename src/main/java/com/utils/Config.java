package com.utils;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class);
    private static Config instance;
    private final Properties properties = new Properties();

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getStartURL() {
        return getProperties("startURL");
    }

    public String getProperties(String key) {
        loadFile("config.properties");
        return properties.getProperty(key);
    }

    private void loadFile(String src) {
        try (FileInputStream inputStream = new FileInputStream(new File(Objects.requireNonNull(getClass().getClassLoader().getResource(src)).toURI()))) {
            properties.load(inputStream);
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error("File " + src + " is not found\n" + ex.getMessage());
        }
    }
}
