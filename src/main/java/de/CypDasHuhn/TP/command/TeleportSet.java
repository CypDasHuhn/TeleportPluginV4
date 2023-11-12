package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.filemanager.PermissionManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportSet {
    public static final String TELEPORT_SET_COMMAND = "teleportSet";
    public static void command(CommandSender sender, String[] args, String label) {
        // check
        if (!(sender instanceof Player player)) return; // The Command Block cannot give a directory for the command.

        if (args.length < 1) {
            Message.sendMessage(player, "teleport_set_short_argument");
            return;
        }
        boolean isGlobal = args[0].equalsIgnoreCase("-global");
        if (isGlobal) {
            boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
            if (args.length < 2) {
                Message.sendMessage(player, "teleport_set_short_argument");
                return;
            } else if (!isPermissioned) {
                Message.sendMessage(player, "no_permission");
                return;
            }
        }

        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
        String locationName = isGlobal ? args[1] : args[0];
        Location playerLocation = player.getLocation();

        LocationManager.register(sender, directory, locationName, Finals.DEFAULT_PARENT, playerLocation);

        Message.sendMessage(player, "teleport_set_success", locationName);
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        boolean isPermissioned = false;
        if (!(sender instanceof Player player)) return arguments; // The Command Block cannot give a directory for the command.
        isPermissioned = PermissionManager.isPermissioned(player.getName());

        switch (args.length) {
            case 1 -> {
                arguments.add("[Name]");
                if (isPermissioned) arguments.add("-global");
            }
            case 2 -> {
                if (isPermissioned && args[0].equalsIgnoreCase("-global")) {
                    arguments.add("[Name]");
                }
            }
        }

        return arguments;
    }
}
