package de.CypDasHuhn.TP.compability;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationCompability {
    public static boolean oldVersion(String directory, String locationName) {
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(locationName, directory);
        //find
        return locationConfig.getString("Coords.World") != null;
    }

    public static void update(String directory, String locationName) {
        //Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(locationName, directory);
        //find
        int x = locationConfig.getInt("Coords.X");
        int y = locationConfig.getInt("Coords.Y");
        int z = locationConfig.getInt("Coords.Z");
        float yaw = (float) locationConfig.getDouble("Coords.Yaw");
        float pitch = (float) locationConfig.getDouble("Coords.Pitch");
        World world = Bukkit.getWorld(locationConfig.getString("Coords.World"));

        Location location = new Location(world, x, y, z, yaw, pitch);
        // set
        locationConfig.set("Coords", Finals.EMPTY);
        locationConfig.set("Location", location);
        // save
        CustomFiles.saveArray(customFiles);
    }
}
