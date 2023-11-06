package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ListManager {
    public static int findID(String directory, String itemName, String itemType) {
        // Prework
        CustomFiles[] cf = CustomFiles.getCustomFiles(1);
        FileConfiguration lConfig = cf[0].gfc("List", directory);
        int amount = lConfig.getInt(itemType + ".amount");
        // Find
        for (int i = 0; i < amount; i++) {
            String currentItemName = lConfig.getString(itemType + "." + i);
            if (currentItemName.equals(itemName)) {
                return i;
            }
        }
        // No result
        return -1;
    }

    public static void add(String directory, String itemName, String itemType) {
        // check
        boolean exists = findID(directory, itemName, itemType) != -1;
        if (exists) return;
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].gfc("List", directory);
        int amount = listConfig.getInt(itemType+".amount")+1;
        // Set
        listConfig.set(itemType+".amount", amount);
        listConfig.set(itemType+"."+amount, itemName);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void remove(String directory, String itemName, String itemType) {
        // check
        boolean exists = findID(directory, itemName, itemType) != -1;
        if (exists) return;
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].gfc("List", directory);
        int amount = listConfig.getInt(itemType+".amount")+1;
        // Delete
        int targetID = findID(directory, itemName, itemType);
        listConfig.set(itemType+".amount", amount-1);
        for (int i = amount; i > targetID; i--) {
            listConfig.set(itemType+i, itemType+(i-1));
        }
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static List<String> getItems(String directory, String itemType) {
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].gfc("List", directory);
        List<String> items = new ArrayList<String>();
        int amount = listConfig.getInt(itemType+".amount");
        // add
        for (int i = 0; i < amount; i++) {
            String item = listConfig.getString(itemType+"."+i);
            items.add(item);
        }

        return items;
    }
}
