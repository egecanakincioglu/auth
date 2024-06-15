package com.egecanakincioglu.services.language;

public class StringManager {
    public static String get(String key) {
        return LangManager.getString(key);
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
