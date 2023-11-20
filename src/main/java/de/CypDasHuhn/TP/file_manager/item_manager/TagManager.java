package de.CypDasHuhn.TP.file_manager.item_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
    public static void addItem(Player player, String directory, String tagName, String itemName, String itemType) {
        // check
        boolean exists = findID(directory, tagName, itemName, itemType) != Finals.NULL_INT;
        if (exists) {
            Message.sendMessage(player, Finals.Messages.ITEM_HAS_TAG_ALREADY.label, itemName,tagName);
            return;
        }
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration tagConfig = customFiles[0].getFileConfiguration(tagName,directory+"/"+ Finals.ItemType.TAG.label);
        int amount = tagConfig.getInt(itemName+".Amount");
        // Set
        tagConfig.set(itemType+".Amount", amount+1);
        tagConfig.set(itemType+"."+amount, itemName);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void removeItem(Player player, String directory, String tagName, String itemName, String itemType) {
        // check
        int targetID = findID(directory, tagName, itemName, itemType);
        boolean exists = targetID != Finals.NULL_INT;
        if (!exists) {
            Message.sendMessage(player, Finals.Messages.ITEM_HAS_NOT_TAG.label, itemName,tagName);
            return;
        }
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration tagConfig = customFiles[0].getFileConfiguration(tagName,directory+"/"+ Finals.ItemType.TAG.label);
        int amount = tagConfig.getInt(itemType+".Amount");
        // Delete
        for (int i = targetID; i < amount; i++) {
            String nextItem = tagConfig.getString(itemType+"."+(i+1));
            tagConfig.set(itemType+"."+i, nextItem);
        }
        tagConfig.set(itemType+".Amount", amount-1);
        tagConfig.set(itemType+"."+(amount-1), Finals.EMPTY);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static int findID(String directory, String tagName, String itemName, String itemType) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration tagConfig = customFiles[0].getFileConfiguration(tagName,directory+"/"+ Finals.ItemType.TAG.label);
        int amount = tagConfig.getInt(itemType+".Amount");
        // Find
        for (int i = 0; i < amount; i++) {
            String currentItemName = tagConfig.getString(itemType + "." + i);
            if (currentItemName.equals(itemName)) {
                return i;
            }
        }
        // No result
        return Finals.NULL_INT;
    }

    public static List<String> getItems(String directory, String tagName, String itemType) {
        // Prework
        List<String> items = new ArrayList<String>();
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration tagConfig = customFiles[0].getFileConfiguration(tagName,directory+"/"+ Finals.ItemType.TAG.label);
        int amount = tagConfig.getInt(itemType+".Amount");
        // Find
        for (int i = 0; i < amount; i++) {
            String currentItemName = tagConfig.getString(itemType + "." + i);
            items.add(currentItemName);
        }
        return items;
    }
}
