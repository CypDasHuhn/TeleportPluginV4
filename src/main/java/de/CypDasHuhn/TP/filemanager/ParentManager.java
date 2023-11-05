package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.configuration.file.FileConfiguration;

public class ParentManager {
    public static void setChildren(String directory, String parentName, String childrenName, String childrenType, int slot) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration parentConfig = customFiles[0].gfc(parentName,directory+"/Folder");
        // Set
        parentConfig.set("Child."+slot+".Name", childrenName);
        parentConfig.set("Child."+slot+".Type", childrenType);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void moveChildren(String directory, String parentName, String childrenName, String childrenType, int slotOld, int slotNew) {
        setChildren(directory, parentName, childrenName, childrenType, slotNew); // register new
        setChildren(directory, parentName, FinalVariables.EMPTY_CHILD, FinalVariables.EMPTY_CHILD, slotOld); // register old
    }
}
