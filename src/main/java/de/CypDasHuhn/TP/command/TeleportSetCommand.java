package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.TP.file_manager.item_manager.ItemManager;
import de.CypDasHuhn.TP.file_manager.item_manager.LocationManager;
import de.CypDasHuhn.TP.file_manager.player_manager.PermissionManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportSetCommand extends SkeletonCommand {
    public static final String TELEPORT_SET_COMMAND = "teleportSet";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        // check
        if (!(sender instanceof Player player)) return; // The Command Block cannot give a directory for the command.

        if (args.length < 1) {
            Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_CREATED_GIVEN.label);
            return;
        }
        boolean isGlobal = args[0].equals(Finals.Attributes.GLOBAL.label);
        if (isGlobal) {
            boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
            if (args.length < 2) {
                Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_CREATED_GIVEN.label);
                return;
            }
            if (!isPermissioned) {
                Message.sendMessage(player, Finals.Messages.NO_PERMISSION.label);
                return;
            }
        }

        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();
        String locationName = isGlobal ? args[1] : args[0];
        Location playerLocation = player.getLocation();

        boolean exists = ItemManager.itemExists(directory, locationName, Finals.ItemType.LOCATION.label);
        if (exists) {
            Message.sendMessage(sender, Finals.Messages.LOCATION_NAME_EXISTS.label, locationName);
            return;
        }

        boolean illegalName = FileManagerMethods.illegalName(locationName);
        if (illegalName) {
            Message.sendMessage(sender, Finals.Messages.ILLEGAL_NAME.label, locationName);
            return;
        }

        ItemManager.register(directory, locationName, Finals.DEFAULT_PARENT, Finals.ItemType.LOCATION.label);
        LocationManager.setLocation(directory, locationName, playerLocation);

        Message.sendMessage(sender, Finals.Messages.TELEPORT_SET_SUCCESS.label, locationName);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        boolean isPermissioned = false;
        if (!(sender instanceof Player player)) return arguments; // The Command Block cannot give a directory for the command.
        isPermissioned = PermissionManager.isPermissioned(player.getName());

        switch (args.length) {
            case 1 -> {
                arguments.add("[Name]");
                if (isPermissioned) arguments.add(Finals.Attributes.GLOBAL.label);
            }
            case 2 -> {
                if (isPermissioned && args[0].equals(Finals.Attributes.GLOBAL.label)) {
                    arguments.add("[Name]");
                }
            }
        }

        return arguments;
    }
}
