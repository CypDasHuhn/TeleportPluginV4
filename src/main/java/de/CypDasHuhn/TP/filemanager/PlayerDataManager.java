package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        if (inventory == null) inventory = Finals.EMPTY;
        playerDataConfig.set("Data.Inventory", inventory);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getInventory(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        String inventory = playerDataConfig.getString("Data.Inventory");
        if (inventory == null) inventory = Finals.EMPTY;
        return inventory;
    }

    public static void setParent(Player player, String parent) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("Data.Parent", parent);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getParent(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        String parentName = playerDataConfig.getString("Data.Parent");
        if (parentName == null) parentName = Finals.DEFAULT_PARENT;
        return parentName;
    }

    public static void setPage(Player player, int page) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("Data.Page", page);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int getPage(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        int page = playerDataConfig.getInt("Data.Page");
        return page;
    }
}
