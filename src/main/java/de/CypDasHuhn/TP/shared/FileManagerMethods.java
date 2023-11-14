package de.CypDasHuhn.TP.shared;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FileManagerMethods {
    public static boolean itemExists(String directory, String itemName, String itemType) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(itemName,directory+"/"+itemType);
        // Check
        boolean exists = childConfig.getString("Parent.Name") != null;
        if (itemName.equals(Finals.DEFAULT_PARENT) && itemType.equals(Finals.ItemType.FOLDER.label)) exists = true; // edge-case
        return exists;
    }

    public static int getFreeSlot(String directory, String parentName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName, directory+"/"+Finals.ItemType.FOLDER.label);
        // find
        int id = 0;
        for (int i = 0; true; i++) { // infinite loop
            String name = parentConfig.getString("Child."+i+".Name");
            boolean empty = name == null || name.equals(Finals.EMPTY);
            if (empty) {
                id = i;
                return id;
            }
        }
    }

    static List<String> illegalCharacters = new ArrayList<String>(){{
        add("/");
        add("\\");
        add(":");
        add("*");
        add("?");
        add("\"");
        add("<");
        add(">");
        add("|");
    }};
    static List<String> illegalName = new ArrayList<String>(){{
        add(Finals.EMPTY);
        add(Finals.DEFAULT_PARENT);
        add(Finals.Attributes.GLOBAL.label);
        add(Finals.Attributes.PROPOSE.label);
        add(Finals.Attributes.FIND.label);
    }};
    public static boolean illegalName(String name) {
        for (String character : illegalCharacters) {
            if (name.contains(character)) return false;
        }
        return illegalName.contains(name);
    }
}
