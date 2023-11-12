package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChildManager {
    public static void setParent(String directory, String childrenName, String childrenType, String parentName, int slot) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(childrenName,directory+"/"+childrenType);
        // Set
        childConfig.set("Parent.Name", parentName);
        childConfig.set("Parent.Slot", slot);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void setItem(String directory, String childrenName, String childrenType, ItemStack item) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(childrenName,directory+"/"+childrenType);
        // Set
        childConfig.set("Item", item);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void renameItem(String directory, String oldName, String newName, String itemType) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(3);
        FileConfiguration oldItemConfig = customFiles[0].getFileConfiguration(oldName,directory+"/"+ itemType);
        FileConfiguration newItemConfig = customFiles[1].getFileConfiguration(newName,directory+"/"+ itemType);

        String parentName = oldItemConfig.getString("Parent.Name");
        int slot = oldItemConfig.getInt("Parent.Slot");
        FileConfiguration parentConfig = customFiles[2].getFileConfiguration(parentName, directory+"/"+Finals.ItemType.FOLDER.label);
        // Set
        newItemConfig.set("Parent.Name", parentName);
        newItemConfig.set("Parent.Slot", slot);

        if (itemType.equals(Finals.ItemType.LOCATION.label)) {
            Location location = oldItemConfig.getLocation("Location");
            newItemConfig.set("Location", location);
        }
        else if (itemType.equals(Finals.ItemType.FOLDER.label)) {
            ArrayList<Integer> slots = new ArrayList<Integer>();
            HashMap<Integer, String> nameMap = new HashMap<Integer, String>();
            HashMap<Integer, String> typeMap = new HashMap<Integer, String>();
            // save old
            int childrenMax = ParentManager.getMaxChildren(directory, parentName);
            for (int i = 0; i <= childrenMax; i++) {
                String currentChildName = parentConfig.getString("Child."+i+".Name");
                if (!(currentChildName == null || currentChildName.equals(Finals.EMPTY))) {
                    String currentChildType = parentConfig.getString("Child."+i+".Type");

                    slots.add(i);
                    nameMap.put(i, currentChildName);
                    typeMap.put(i, currentChildType);
                }
            }
            // set new
            for (int i : slots) {
                parentConfig.set("Child."+i+".Name", nameMap.get(i));
                parentConfig.set("Child."+i+".Type", typeMap.get(i));
            }
        }
        // Save
        CustomFiles.saveArray(customFiles);
        // other calls
        ParentManager.setChildren(directory, parentName, newName, itemType, slot); // set old slot with new name
        ListManager.replace(directory, oldName, newName, itemType); // set old id with new name
        // delete old
        customFiles[0].delete(oldName, directory+"/"+itemType);
    }
}
