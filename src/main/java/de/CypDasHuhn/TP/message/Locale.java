package de.CypDasHuhn.TP.message;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Locale {
    public static final List<String> locales = Arrays.asList("en", "de");
    public static boolean isLocale(String language) {
        return Arrays.asList(locales).contains(language);
    }

    public static void setLanguage(Player player, String language) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        playerConfig.set("Players." + player.getUniqueId() + ".language", language);
        CustomFiles.saveArray(customFiles);
    }

    public static String getLanguage(Player player) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        String language = playerConfig.getString("Players." + player.getUniqueId() + ".language");
        if (language == null) {
            String globalLanguage = getLanguageGlobal();
            setLanguage(player, globalLanguage);
            language = globalLanguage;
        }
        return language;
    }

    public static void setLanguageGlobal(String language) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        playerConfig.set("GlobalLanguage", language);
        CustomFiles.saveArray(customFiles);
    }

    public static String getLanguageGlobal() {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        String language = playerConfig.getString("GlobalLanguage");
        return language;
    }

    public static java.util.Locale getLocale(Player player) {
        return java.util.Locale.forLanguageTag(getLanguage(player));
    }
}
