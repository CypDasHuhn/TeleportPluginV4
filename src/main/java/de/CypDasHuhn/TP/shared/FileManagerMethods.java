package de.CypDasHuhn.TP.shared;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManagerMethods {
    public static boolean itemExists(String directory, String itemName, String itemType) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(itemName,directory+"/"+itemType);
        // Check
        boolean exists = childConfig.getString("Parent.Name") != null;
        if (itemName.equals(FinalVariables.DEFAULT_PARENT) && itemType.equals("Folder")) exists = true; // edge-case
        return exists;
    }

    public static int getFreeSlot(String directory, String parentName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration dConfig = customFiles[0].getFileConfiguration(parentName, directory+"/Folder");
        // find
        int id = 0;
        for (int i = 0; i == -1; i++) { // infinite loop
            boolean empty = dConfig.getString("Child."+id+".Name") == null || dConfig.getString("Child."+id+".Name").equals(FinalVariables.EMPTY);
            if (empty) {
                id = i;
                i = -1; // close loop
            }
        }
        return id;
    }

    public static boolean illegalName(String name) {
        boolean illegalName =
                name.equals(FinalVariables.DEFAULT_PARENT) ||
                name.equals(FinalVariables.EMPTY) ||
                name.contains("/") ||
                name.contains("\\") ||
                name.contains(":") ||
                name.contains("*") ||
                name.contains("?") ||
                name.contains("\"") ||
                name.contains("<") ||
                name.contains(">") ||
                name.contains("|");
        return illegalName;
    }
}
