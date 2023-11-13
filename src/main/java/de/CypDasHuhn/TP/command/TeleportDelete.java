package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import de.CypDasHuhn.TP.filemanager.ListManager;
import de.CypDasHuhn.TP.filemanager.ParentManager;
import de.CypDasHuhn.TP.filemanager.PermissionManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportDelete {
    public static final String TELEPORT_DELETE_COMMAND = "teleportDelete";
    public static void command(CommandSender sender, String[] args, String label) {
        // check
        if (!(sender instanceof Player player)) return; // command blocks cannot access a directory

        if (args.length < 1) {
            Message.sendMessage(sender, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
            return;
        }
        boolean isGlobal = args[0].equals(Finals.Attributes.GLOBAL.label);
        if (isGlobal) {
            boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
            if (args.length < 2) {
                Message.sendMessage(sender, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
                return;
            } else if (!isPermissioned) {
                Message.sendMessage(sender, Finals.Messages.NO_PERMISSION.label);
                return;
            }
        }

        // prework
        String locationName = args[0];
        String directory = player.getUniqueId().toString();

        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(locationName, directory+"/"+ Finals.ItemType.LOCATION.label);

        String parentName = locationConfig.getString("Parent.Name");
        int slot = locationConfig.getInt("Parent.Slot");
        // delete
        customFiles[0].delete(locationName, directory+"/"+ Finals.ItemType.LOCATION.label);
        ListManager.remove(directory, locationName, Finals.ItemType.LOCATION.label);
        ParentManager.setChildren(directory, parentName, Finals.EMPTY, Finals.EMPTY, slot);

        Message.sendMessage(sender, Finals.Messages.TELEPORT_DELETE_SUCCESS.label, locationName);
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        boolean isPermissioned = false;
        if (!(sender instanceof Player player)) return arguments;
        isPermissioned = PermissionManager.isPermissioned(player.getName());
        String directory = "";

        switch (args.length) {
            case 1 -> {
                directory = player.getUniqueId().toString();
                List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
                arguments.addAll(locations);
                if (isPermissioned) arguments.add(Finals.Attributes.GLOBAL.label);
            }
            case 2 -> {
                if (isPermissioned && args[0].equals(Finals.Attributes.GLOBAL.label)) {
                    directory = Finals.GLOBAL;
                    List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
                    arguments.addAll(locations);
                }
            }
        }

        return arguments;
    }
}
