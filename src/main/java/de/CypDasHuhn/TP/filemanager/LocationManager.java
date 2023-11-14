package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {
    public static void register(CommandSender sender, String directory, String name, String parentName, Location location) {
        // check
        boolean exists = FileManagerMethods.itemExists(directory, name, Finals.ItemType.LOCATION.label);
        if (exists) {
            Message.sendMessage(sender, Finals.Messages.LOCATION_NAME_EXISTS.label, name);
            return;
        }

        boolean illegalName = FileManagerMethods.illegalName(name);
        if (illegalName) {
            Message.sendMessage(sender, Finals.Messages.ILLEGAL_NAME.label, name);
            return;
        }

        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(name,directory+"/"+ Finals.ItemType.LOCATION.label);
        // Set
        childConfig.set("Location", location);
        // Save
        CustomFiles.saveArray(customFiles);
        // Other Calls
        int freeSlot = FileManagerMethods.getFreeSlot(directory, parentName);
        ParentManager.setChildren(directory, parentName, name, Finals.ItemType.LOCATION.label, freeSlot);
        ChildManager.setParent(directory,name, Finals.ItemType.LOCATION.label,parentName, freeSlot);
        ListManager.add(directory, name, Finals.ItemType.LOCATION.label);

        Message.sendMessage(sender, Finals.Messages.TELEPORT_SET_SUCCESS.label, name);
    }

    public static void setLocation(String directory, String childrenName, Location location) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(childrenName,directory+"/"+ Finals.ItemType.LOCATION.label);
        // Set
        childConfig.set("Location", location);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static Location getLocation() {
        return null;
    }


}
