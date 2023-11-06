package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        if (inventory == null) inventory = FinalVariables.EMPTY;
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
        return inventory;
    }
}
