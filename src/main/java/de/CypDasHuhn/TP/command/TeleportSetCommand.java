package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.Skeleton.SkeletonCommand;
import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.filemanager.PermissionManager;
import de.CypDasHuhn.TP.message.Message;
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

        LocationManager.register(sender, directory, locationName, Finals.DEFAULT_PARENT, playerLocation);
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
