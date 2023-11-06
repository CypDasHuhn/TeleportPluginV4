package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class PlayerManager {
    public static boolean existsUUID(String uuid) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].gfc("Players","");
        // find
        String player = playersConfig.getString("UUID."+uuid);
        // check
        boolean exists = player != null || player.equals("EMPTY");

        return exists;
    }

    public static boolean existsName(String name) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].gfc("Players","");
        // find
        String player = playersConfig.getString("Name."+name);
        // check
        boolean exists = player != null || player.equals("EMPTY");

        return exists;
    }

    public static void add(String name, String uuid) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].gfc("Players","");
        int amount = playersConfig.getInt("PlayerAmount");
        // add
        amount++;
        playersConfig.set("PlayerAmount",amount);
        playersConfig.set("PlayerList."+amount,name);

        playersConfig.set("Name."+name, uuid);
        playersConfig.set("UUID."+uuid, name);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static void replaceName(String name, String uuid) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].gfc("Players","");
        int amount = playersConfig.getInt("PlayerAmount");

        // find
        String oldName = playersConfig.getString("UUID."+uuid);
        int id = -1;
        for (int i = 0; i < amount; i++) {
            String currentName = playersConfig.getString("PlayerList."+i);
            if (currentName.equals(oldName)) {
                id = i;
                break;
            }
        }

        playersConfig.set("PlayerList."+id,name);

        playersConfig.set("Name."+oldName, "EMPTY");
        playersConfig.set("Name."+name, uuid);
        playersConfig.set("UUID."+uuid, name);
        // save
        CustomFiles.saveArray(customFiles);
    }
}
