package de.CypDasHuhn.TP.filemanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ChildManager {
    public static void setParent(String directory, String childrenName, String childrenType, String parentName, int slot) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].gfc(childrenName,directory+"/"+childrenType);
        // Set
        childConfig.set("Parent.Name", parentName);
        childConfig.set("Parent.Slot", slot);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void setItem(String directory, String childrenName, String childrenType, ItemStack item) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].gfc(childrenName,directory+"/"+childrenType);
        // Set
        childConfig.set("Item", item);
        // Save
        CustomFiles.saveArray(customFiles);
    }
}
