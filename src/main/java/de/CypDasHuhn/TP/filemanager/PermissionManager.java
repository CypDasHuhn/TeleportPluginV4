package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.configuration.file.FileConfiguration;

public class PermissionManager {
    public static void add() {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration permissionConfig = customFiles[0].getFileConfiguration("Permission","");
        int amount = permissionConfig.getInt("Amount");
        // add
        amount++;
        permissionConfig.set("Amount", amount);
    }

    public static void remove() {

    }

    public static boolean isPermissioned() {
        return false;
    }
}
