package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;

public class ParentManager {
    private static final String MAX_CHILDREN = "MaxChildren";
    public static void setChildren(String directory, String parentName, String childrenName, String childrenType, int slot) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        int maxChildren = parentConfig.getInt(MAX_CHILDREN);
        // Set
        parentConfig.set("Child."+slot+".Name", childrenName);
        parentConfig.set("Child."+slot+".Type", childrenType);
        if (slot > maxChildren) parentConfig.set(MAX_CHILDREN, slot);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static int getMaxChildren(String directory, String parentName) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        // find
        return parentConfig.getInt(MAX_CHILDREN);
    }
}