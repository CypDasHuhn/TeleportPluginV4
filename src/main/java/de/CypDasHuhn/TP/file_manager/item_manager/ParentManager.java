package de.CypDasHuhn.TP.file_manager.item_manager;

import de.CypDasHuhn.TP.DTO.ItemDTO;
import de.CypDasHuhn.TP.file_manager.CustomFiles;
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

    public static ItemDTO getChild(String directory, String parentName, int slot) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        // get
        String childrenName = parentConfig.getString("Child."+slot+".Name");
        String childrenType = parentConfig.getString("Child."+slot+".Type");
        return childrenType != null ? new ItemDTO(childrenName, childrenType) : null;
    }

    public static int getMaxChildren(String directory, String parentName) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        // find
        return parentConfig.getInt(MAX_CHILDREN);
    }

    public static void setRowAmount(String directory, String parentName, int rowAmount) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        // Set
        parentConfig.set("RowAmount", rowAmount);
        // Save
        CustomFiles.saveArray(customFiles);
    }
    public static int getRowAmount(String directory, String parentName)  {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].getFileConfiguration(parentName,directory+"/"+ Finals.ItemType.FOLDER.label);
        // Get
        int rowAmount = parentConfig.getInt("RowAmount");
        if (rowAmount == 0) {
            rowAmount = 6;
            setRowAmount(directory, parentName, rowAmount);
        }
        return rowAmount;
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
}