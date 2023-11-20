package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.TP.command.general.CustomCommand;
import de.CypDasHuhn.TP.compability.LocationCompability;
import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.file_manager.item_manager.ListManager;
import de.CypDasHuhn.TP.file_manager.player_manager.PlayerDataManager;
import de.CypDasHuhn.TP.file_manager.player_manager.PlayerListManager;
import de.CypDasHuhn.TP.interfaces.folder_interface.FolderInterface;
import de.CypDasHuhn.TP.interfaces.Interface;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand extends SkeletonCommand {
    private static final String PLAYER_NOT_FOUND = "player_not_found_online";

    public static final String TELEPORT_COMMAND = "teleport";
    public static final String TELEPORT_GLOBAL_COMMAND = "teleportGlobal";
    public static final String TELEPORT_USER_COMMAND = "teleportUser";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        boolean isTeleportCommand = CustomCommand.isCommand(label, TELEPORT_COMMAND);
        boolean isTeleportGlobalCommand = CustomCommand.isCommand(label, TELEPORT_GLOBAL_COMMAND);
        boolean isTeleportUserCommand = CustomCommand.isCommand(label, TELEPORT_USER_COMMAND);

        int bonus = isTeleportUserCommand ? 1 : 0;
        // check

        if (isTeleportCommand && !(sender instanceof Player)) return; // command blocks don't have their own directory

        if (isTeleportUserCommand) {
            if (args.length < 1) {
                Message.sendMessage(sender, Finals.Messages.PLAYER_NOT_GIVEN.label);
                return;
            } else {
                String targetPlayer = args[0];
                boolean playerExists = PlayerListManager.existsByName(targetPlayer);
                if (!playerExists) {
                    Message.sendMessage(sender, Finals.Messages.PLAYER_NOT_FOUND_CONFIG.label, targetPlayer);
                    return;
                }
            }
        }

        String directory;
        if (isTeleportCommand) directory = ((Player)sender).getUniqueId().toString();
        else if (isTeleportUserCommand) directory = args[0]; // targetPlayer
        else directory = Finals.GLOBAL; // isGlobalCommand

        if (args.length < 1+bonus) {
            if (sender instanceof Player player) { // command blocks can not open an interface
                String parentName = PlayerDataManager.getParent(player);
                int page = PlayerDataManager.getPage(player);

                Interface.openTargetInterface(player, FolderInterface.interfaceName, directory, parentName, page);
            }
            return;
        }

        Location senderLocation = (sender instanceof Player) ? ((Player) sender).getLocation() : ((BlockCommandSender)sender).getBlock().getLocation();

        String locationName = args[0];
        if (isTeleportUserCommand) locationName = args[1];

        boolean locationExists = ListManager.findID(directory, locationName, Finals.ItemType.LOCATION.label) != Finals.NULL_INT;
        if (!locationExists) {
            Message.sendMessage(sender, Finals.Messages.NO_LOCATION_NAME_TARGET_FOUND.label, locationName);
            return;
        }

        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].getFileConfiguration(locationName, directory+"/"+ Finals.ItemType.LOCATION.label);

        if (LocationCompability.oldVersion(directory, locationName)) {
            LocationCompability.update(directory, locationName);
        }

        Location teleportLocation = locationConfig.getLocation("Location");

        Player targetPlayer = null;
        String targetPlayerName = null;
        if ((isTeleportCommand || isTeleportGlobalCommand) && args.length > 1) targetPlayerName = args[1];
        else if (isTeleportUserCommand && args.length > 2) targetPlayerName = args[2];
        else if (sender instanceof Player) targetPlayer = (Player)sender;
        else return;


        if (targetPlayerName != null) {
            targetPlayer = SpigotMethods.getPlayer(targetPlayerName, senderLocation);
            if (targetPlayer == null) {
                Message.sendMessage(sender, Finals.Messages.PLAYER_NOT_FOUND_ONLINE.label);
                return;
            }
        }

        targetPlayer.teleport(teleportLocation);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        boolean isTeleportCommand = CustomCommand.isCommand(label, TELEPORT_COMMAND);
        boolean isTeleportGlobalCommand = CustomCommand.isCommand(label, TELEPORT_GLOBAL_COMMAND);
        boolean isTeleportUserCommand = CustomCommand.isCommand(label, TELEPORT_USER_COMMAND);

        int bonus = isTeleportUserCommand ? 1 : 0;

        List<String> arguments = new ArrayList<String>();

        if (isTeleportUserCommand && args.length == 1) {
            List<String> players = PlayerListManager.getPlayers();
            arguments.addAll(players);
        }

        if (isTeleportUserCommand) {
            int playerNameIndex = 0;
            boolean isPlayer = args.length > playerNameIndex && (PlayerListManager.existsByName(args[playerNameIndex]));
            if (args.length > playerNameIndex && !isPlayer) return arguments;
        }

        String directory;
        if (isTeleportCommand) directory = ((Player)sender).getUniqueId().toString();
        else if (isTeleportUserCommand) directory = args[0]; // targetPlayer
        else directory = Finals.GLOBAL; // isGlobalCommand

        if (args.length == 1+bonus) {
            List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
            arguments.addAll(locations);
        }

        int locationNameIndex = 1;
        boolean isLocation = args.length > locationNameIndex && (PlayerListManager.existsByName(args[locationNameIndex]));
        if (args.length > locationNameIndex && !isLocation) return arguments;

        if (args.length == 2+bonus) {
            List<String> players = SpigotMethods.getPossibleTargets();
            arguments.addAll(players);
        }

        return arguments;
    }

}
