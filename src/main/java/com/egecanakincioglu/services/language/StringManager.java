package com.egecanakincioglu.services.language;

import java.util.HashMap;
import java.util.Map;

public class StringManager {
    private static final Map<String, String> stringMap = new HashMap<>();

    public static void loadStrings(Map<String, Object> languageData) {
        populateStringMap(languageData, "");
    }

    @SuppressWarnings("unchecked")
    private static void populateStringMap(Map<String, Object> data, String prefix) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() instanceof String) {
                String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
                String value = (String) entry.getValue();
                stringMap.put(key, value);
            } else if (entry.getValue() instanceof Map) {
                String newPrefix = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
                populateStringMap((Map<String, Object>) entry.getValue(), newPrefix);
            }
        }
    }

    public static String get(String key) {
        return stringMap.get(key);
    }

    public static String getCommandHandlerPackage() {
        return get("COMMAND_HANDLER_PACKAGE");
    }

    public static String getCommandLoaded() {
        return get("COMMAND_LOADED");
    }

    public static String getCommandFailedToLoad() {
        return get("COMMAND_FAILED_TO_LOAD");
    }
}
