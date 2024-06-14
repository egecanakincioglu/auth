package com.egecanakincioglu.services.language;

import com.egecanakincioglu.services.config.Config;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

public class LangManager {
    private static Map<String, Object> languageData;

    static {
        String locale = Config.getLocale();
        String fileName = "Language/Lang-" + locale.toUpperCase() + ".yml";
        loadLanguageFile(fileName);
    }

    private static void loadLanguageFile(String fileName) {
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(Map.class, loaderOptions);
        Yaml yaml = new Yaml(constructor);

        try (InputStream inputStream = LangManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                languageData = yaml.load(inputStream);
            } else {
                throw new IllegalArgumentException("Language file not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load language file: " + fileName, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String getString(String key) {
        String[] keys = key.split("\\.");
        int length = keys.length;
        Map<String, Object> currentMap = languageData;

        for (int i = 0; i < length - 1; i++) {
            Object value = currentMap.get(keys[i]);
            if (value instanceof Map) {
                currentMap = (Map<String, Object>) value;
            } else {
                throw new IllegalArgumentException("Invalid key path: " + key);
            }
        }

        Object finalValue = currentMap.get(keys[length - 1]);
        if (finalValue instanceof String) {
            return (String) finalValue;
        } else {
            throw new IllegalArgumentException("Value for key is not a string: " + key);
        }
    }
}