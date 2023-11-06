package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import de.CypDasHuhn.TP.filemanager.ListManager;
import de.CypDasHuhn.TP.filemanager.ParentManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportDelete {
    public static void command(CommandSender sender, String[] args) {
        // check
        if (args.length < 1) {
            Message.sendMessage(sender, "teleport_delete_short_argument");
            return;
        }

        if (!(sender instanceof Player player)) return; // command blocks cannot access a directory

        // prework
        String locationName = args[0];
        String UUID = player.getUniqueId().toString();
        String directory = UUID;

        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(locationName, UUID+"/"+ FinalVariables.LOCATION);

        String parentName = locationConfig.getString("Parent.Name");
        int slot = locationConfig.getInt("Parent.Slot");
        // delete
        customFiles[0].delete(locationName, UUID+"/"+ FinalVariables.LOCATION);
        ListManager.remove(UUID, locationName, FinalVariables.LOCATION);
        ParentManager.setChildren(directory, locationName, parentName, FinalVariables.LOCATION, slot);
    }

    public static List<String> completer(CommandSender sender, String[] args) {
        List<String> arguments = new ArrayList<String>();
        arguments.add(Message.getMessage(sender, "wip"));
        return arguments;
    }
}
