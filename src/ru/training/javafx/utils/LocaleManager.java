package ru.training.javafx.utils;

import ru.training.javafx.objects.Lang;

import java.util.Locale;

/**
 * Created by Neboola on 15.11.2015.
 */
public class LocaleManager {

    public static final Locale RU_LOCALE = new Locale("ru");
    public static final Locale EN_LOCALE = new Locale("en");

    private static Lang currentLang;

    public static Lang getCurrentLang() {
        return currentLang;
    }

    public static void setCurrentLang(Lang currentLang) {
        LocaleManager.currentLang = currentLang;
    }
}
