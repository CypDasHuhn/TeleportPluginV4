package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.TP.file_manager.player_manager.PermissionManager;
import de.CypDasHuhn.TP.file_manager.player_manager.PlayerListManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PermissionCommand extends SkeletonCommand {
    public static final String PERMISSION_COMMAND = "teleportPermission";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        // check
        boolean isPermissioned = false;
        if (sender instanceof ConsoleCommandSender) isPermissioned = true;
        else if (sender instanceof Player player) isPermissioned = PermissionManager.isPermissioned(player.getName());

        if (!isPermissioned) {
            Message.sendMessage(sender, Finals.Messages.NO_PERMISSION.label);
            return;
        }

        if (args.length < 1) {
            Message.sendMessage(sender, Finals.Messages.PLAYER_NOT_GIVEN.label);
            return;
        }
        String targetPlayer = args[0];
        boolean playerExists = PlayerListManager.existsByName(targetPlayer);
        if (!playerExists) {
            Message.sendMessage(sender, Finals.Messages.PLAYER_NOT_FOUND_CONFIG.label);
            return;
        }
        // prework
        boolean targetPlayerPermissioned = PermissionManager.isPermissioned(targetPlayer);
        // handle
        if (!targetPlayerPermissioned) {
            PermissionManager.add(targetPlayer);
            Message.sendMessage(sender, Finals.Messages.PERMISSION_ADDED.label, targetPlayer);
        } else {
            PermissionManager.remove(targetPlayer);
            Message.sendMessage(sender, Finals.Messages.PERMISSION_REMOVED.label, targetPlayer);
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1:
                List<String> players = PlayerListManager.getPlayers();
                arguments.addAll(players);
                break;
        }
        return arguments;
    }
}
