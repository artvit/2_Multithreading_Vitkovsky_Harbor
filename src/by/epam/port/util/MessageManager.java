package by.epam.port.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private final static String RESOURCE_NAME = "messages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());

    public static void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }
}
