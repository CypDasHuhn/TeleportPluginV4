package de.CypDasHuhn.TP.file_manager.item_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {
        public static void setLocation(String directory, String childrenName, Location location) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration childConfig = customFiles[0].getFileConfiguration(childrenName,directory+"/"+ Finals.ItemType.LOCATION.label);
        // Set
        childConfig.set("Location", location);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static Location getLocation(String directory, String childrenName) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(childrenName,directory+"/"+ Finals.ItemType.LOCATION.label);
        // get
        Location location = locationConfig.getLocation("Location");
        return location;
    }


}
