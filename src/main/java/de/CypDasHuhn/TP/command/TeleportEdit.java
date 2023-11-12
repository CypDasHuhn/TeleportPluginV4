package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.ChildManager;
import de.CypDasHuhn.TP.filemanager.ListManager;
import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.filemanager.PermissionManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportEdit {
    public static final String TELEPORT_EDIT_COMMAND = "teleportEdit";
    public static final String LOCATION_MODE = "Location";
    public static final String NAME_MODE = "Name";
    public static void command(CommandSender sender, String[] args, String label) {
        // check
        if (!(sender instanceof Player player)) return; // The Command Block cannot give a directory for the command.

        boolean isGlobal = args.length > 0 && args[0].equalsIgnoreCase("-global");

        if (isGlobal) {
            boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
            if (!isPermissioned) {
                Message.sendMessage(player, "no_permission");
                return;
            }
        }
        int isGlobalBonus = isGlobal ? 1 : 0;
        if (args.length < 1+isGlobalBonus) {
            Message.sendMessage(player, "teleport_edit_short_argument_name");
            return;
        }
        if (args.length < 2+isGlobalBonus) {
            Message.sendMessage(player, "teleport_edit_short_argument_mode");
            return;
        }
        if (args.length < 3+isGlobalBonus && args[1+isGlobalBonus].equalsIgnoreCase(NAME_MODE)) {
            Message.sendMessage(player, "teleport_edit_short_argument_custom_name");
            return;
        }

        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
        String locationName = args[0+isGlobalBonus];
        String mode = args[1+isGlobalBonus];

        boolean locationExists = FileManagerMethods.itemExists(directory, locationName, Finals.ItemType.LOCATION.label);
        if (!locationExists) {
            Message.sendMessage(player, "teleport_location_not_found");
            return;
        }

        if (!(mode.equalsIgnoreCase(LOCATION_MODE) || mode.equalsIgnoreCase(NAME_MODE))) {
            Message.sendMessage(player, "teleport_edit_mode_not_found");
            return;
        }

        if (mode.equalsIgnoreCase(LOCATION_MODE)) {
            Location location = player.getLocation();
            LocationManager.setLocation(directory, locationName, location);
        }
        else if (mode.equalsIgnoreCase(NAME_MODE)) {
            String newName = args[2+isGlobalBonus];
            ChildManager.renameItem(directory, locationName, newName, Finals.ItemType.LOCATION.label);
        }
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        if (!(sender instanceof  Player player)) return arguments; // command blocks cannot access a directory
        boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
        boolean isGlobal = args.length > 0 && args[0].equalsIgnoreCase("-global");
        int isGlobalBonus = isGlobal ? 1 : 0;
        String directory = "";

        if (args.length == 1) {
            if (isPermissioned) arguments.add("-global");
        }
        if (args.length == 1+isGlobalBonus) {
            if (isGlobal && !isPermissioned) return arguments;

            directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
            List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
            arguments.addAll(locations);
        } else if (args.length == 2+isGlobalBonus) {
            if (isGlobal && !isPermissioned) return arguments;

            directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
            String locationName = args[0+isGlobalBonus];
            boolean locationExists = FileManagerMethods.itemExists(directory, locationName, Finals.ItemType.LOCATION.label);
            if (!locationExists) return arguments;

            arguments.add(NAME_MODE);
            arguments.add(LOCATION_MODE);
        } else if (args.length == 3+isGlobalBonus) {
            if (isGlobal && !isPermissioned) return arguments;

            directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
            String locationName = args[0+isGlobalBonus];
            boolean locationExists = FileManagerMethods.itemExists(directory, locationName, Finals.ItemType.LOCATION.label);
            if (!locationExists) return arguments;

            if ((args[1+isGlobalBonus]).equalsIgnoreCase(NAME_MODE)) {
                arguments.add("[Name]");
            }
        }
        return arguments;
    }
}
