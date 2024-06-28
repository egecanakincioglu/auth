package com.egecanakincioglu.services.config;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

public class Config {

    private static Map<String, Object> data;

    public Config() {
        this("Config.yml");
    }

    public Config(String fileName) {
        initialize(fileName);
    }

    public static synchronized void initialize(String fileName) {
        if (data == null) {
            LoaderOptions loaderOptions = new LoaderOptions();
            Constructor constructor = new Constructor(Map.class, loaderOptions);
            Yaml yaml = new Yaml(constructor);

            try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
                if (inputStream != null) {
                    data = yaml.load(inputStream);
                } else {
                    throw new IllegalArgumentException("File not found: " + fileName);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load config file: " + fileName, e);
            }
        }
    }

    @NotNull
    public static String getToken() {
        return getString("BOT_TOKEN");
    }

    @NotNull
    public static String getName() {
        return getString("GLOBAL_BOT_NAME");
    }

    @NotNull
    public static String getLocale() {
        return getString("LOCALE");
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private static String getString(@NotNull String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = data;

        for (int i = 0; i < keys.length - 1; i++) {
            Object value = currentMap.get(keys[i]);
            if (value instanceof Map) {
                currentMap = (Map<String, Object>) value;
            } else {
                throw new IllegalArgumentException("Invalid key path: " + key);
            }
        }

        Object finalValue = currentMap.get(keys[keys.length - 1]);
        if (finalValue instanceof String) {
            return (String) finalValue;
        } else {
            throw new IllegalArgumentException("Value for key is not a string: " + key);
        }
    }
}
