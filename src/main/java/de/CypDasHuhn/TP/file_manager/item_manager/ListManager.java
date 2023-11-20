package de.CypDasHuhn.TP.file_manager.item_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ListManager {
    private static final String LIST_FILE = "List";
    private static final String AMOUNT_DIRECTORY = "Amount";
    public static int findID(String directory, String itemName, String itemType) {
        // Prework
        CustomFiles[] cf = CustomFiles.getCustomFiles(1);
        FileConfiguration lConfig = cf[0].getFileConfiguration(LIST_FILE, directory);
        int amount = lConfig.getInt(itemType + "."+AMOUNT_DIRECTORY);
        // Find
        for (int i = 0; i < amount; i++) {
            String currentItemName = lConfig.getString(itemType + "." + i);
            if (currentItemName.equals(itemName)) {
                return i;
            }
        }
        // No result
        return Finals.NULL_INT;
    }

    public static void add(String directory, String itemName, String itemType) {
        // check
        boolean exists = findID(directory, itemName, itemType) != Finals.NULL_INT;
        if (exists) return;
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].getFileConfiguration(LIST_FILE, directory);
        int amount = listConfig.getInt(itemType+"."+AMOUNT_DIRECTORY);
        // Set
        listConfig.set(itemType+"."+AMOUNT_DIRECTORY, amount+1);
        listConfig.set(itemType+"."+amount, itemName);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void remove(String directory, String itemName, String itemType) {
        // check
        boolean exists = findID(directory, itemName, itemType) != Finals.NULL_INT;
        if (!exists) return;
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].getFileConfiguration(LIST_FILE, directory);
        int amount = listConfig.getInt(itemType+"."+AMOUNT_DIRECTORY);
        // Delete
        int targetID = findID(directory, itemName, itemType);


        for (int i = targetID; i < amount; i++) {
            String nextItem = listConfig.getString(itemType+"."+(i+1));
            listConfig.set(itemType+"."+i, nextItem);
        }
        listConfig.set(itemType+"."+AMOUNT_DIRECTORY, amount-1);
        listConfig.set(itemType+"."+(amount-1), Finals.EMPTY);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void replace(String directory, String oldName, String newName, String itemType) {
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].getFileConfiguration(LIST_FILE, directory);
        int id = findID(directory, oldName, itemType);
        // set
        listConfig.set(itemType+"."+id, newName);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static List<String> getItems(String directory, String itemType) {
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration listConfig = customFiles[0].getFileConfiguration(LIST_FILE, directory);
        List<String> items = new ArrayList<String>();
        int amount = listConfig.getInt(itemType+"."+AMOUNT_DIRECTORY);
        // add
        for (int i = 0; i < amount; i++) {
            String item = listConfig.getString(itemType+"."+i);
            items.add(item);
        }
        return items;
    }
}
