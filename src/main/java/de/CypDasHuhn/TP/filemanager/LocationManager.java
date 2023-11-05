package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {
    public static void register(CommandSender sender, String directory, String name, String parentName, Location location) {
        // check
        boolean exists = FileManagerMethods.itemExists(directory, name, FinalVariables.LOCATION);
        if (exists) {
            Message.sendMessage(sender, "register_location_exists", name);
            return;
        }

        boolean illegalName = FileManagerMethods.illegalName(name);
        if (illegalName) {
            Message.sendMessage(sender, "illegal_name", name);
            return;
        }

        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].gfc(name,directory+"/"+ FinalVariables.LOCATION);
        // Set
        childConfig.set("Location", location);
        // Save
        CustomFiles.saveArray(customFiles);
        // Other Calls
        int freeSlot = FileManagerMethods.getFreeSlot(directory, parentName);
        ParentManager.setChildren(directory, parentName, name, FinalVariables.LOCATION, freeSlot);
        ChildManager.setParent(directory,name, FinalVariables.LOCATION,parentName, freeSlot);
        ListManager.add(directory, name, FinalVariables.LOCATION);

        Message.sendMessage(sender, "register_location_created");
    }

    public static void updateName() {

    }

    public static void updateSlot() {

    }

    public static void updateParent() {

    }

    public static Location getLocation() {
        return null;
    }


}
