package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.general.Command;
import de.CypDasHuhn.TP.compability.LocationCompability;
import de.CypDasHuhn.TP.filemanager.CustomFiles;
import de.CypDasHuhn.TP.filemanager.ListManager;
import de.CypDasHuhn.TP.filemanager.PlayerListManager;
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

public class Teleport {
    private static final String PLAYER_NOT_FOUND = "player_not_found_online";

    public static final String TELEPORT_COMMAND = "teleport";
    public static final String TELEPORT_GLOBAL_COMMAND = "teleportGlobal";
    public static final String TELEPORT_USER_COMMAND = "teleportUser";

    public static void command(CommandSender sender, String[] args, String label) {
        // check
        if (args.length < 1) {
            // INTERFACE TO-DO
            Message.sendMessage(sender, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
            return;
        }

        boolean teleport = Command.isCommand(label, TELEPORT_COMMAND);
        boolean teleportGlobal = Command.isCommand(label, TELEPORT_GLOBAL_COMMAND);
        boolean teleportUser = Command.isCommand(label, TELEPORT_USER_COMMAND);

        Boolean[] commandTypes = {teleport, teleportGlobal, teleportUser};

        if (teleport && !(sender instanceof Player)) {
            return;
        }

        if (teleportUser && args.length < 2) {
            // INTERFACE TO-DO
            Message.sendMessage(sender, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
            return;
        }

        Location senderLocation;
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            senderLocation = player.getLocation();
        } else {
            BlockCommandSender blockCommandSender = (BlockCommandSender) sender;
            senderLocation = blockCommandSender.getBlock().getLocation();
        }

        String directory = getDirectory(sender, commandTypes, args);
        if (directory.equals(PLAYER_NOT_FOUND)) {
            Message.sendMessage(sender, PLAYER_NOT_FOUND);
            return;
        }

        String locationName = args[0];
        if (teleportUser) locationName = args[1];

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
        if ((teleport || teleportGlobal) && args.length > 1) targetPlayerName = args[1];
        else if (teleportUser && args.length > 2) targetPlayerName = args[2];
        else if (player != null) targetPlayer = player;
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

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        boolean teleport = Command.isCommand(label, TELEPORT_COMMAND);
        boolean teleportGlobal = Command.isCommand(label, TELEPORT_GLOBAL_COMMAND);
        boolean teleportUser = Command.isCommand(label, TELEPORT_USER_COMMAND);

        Boolean[] commandTypes = {teleport, teleportGlobal, teleportUser};

        String directory = getDirectory(sender, commandTypes, args);

        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1 -> {
                if (teleport || teleportGlobal) {
                    List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
                    arguments.addAll(locations);
                }

                if (teleportUser) {
                    List<String> players = PlayerListManager.getPlayers();
                    System.out.println(players);
                    arguments.addAll(players);
                }
            }
            case 2 -> {
                if (teleport ||teleportGlobal) {
                    List<String> players = SpigotMethods.getPossibleTargets();
                    arguments.addAll(players);
                }

                if (teleportUser) {
                    if (directory.equals(PLAYER_NOT_FOUND)) break;
                    List<String> locations = ListManager.getItems(directory, Finals.ItemType.LOCATION.label);
                    System.out.println(locations);
                    arguments.addAll(locations);
                }
            }
            case 3 -> {
                if (teleportUser ) {
                    if (directory.equals(PLAYER_NOT_FOUND)) break;
                    List<String> players = SpigotMethods.getPossibleTargets();
                    arguments.addAll(players);
                }
            }

        }
        return arguments;
    }

    public static String getDirectory(CommandSender sender, Boolean[] commandType, String[] args) {
        boolean teleport = commandType[0];
        boolean teleportGlobal = commandType[1];
        boolean teleportUser = commandType[2];

        Player player = null;
        if (sender instanceof Player) player = (Player) sender;

        String directory = "";
        if (teleportGlobal) directory = Finals.GLOBAL;
        else if (teleport) directory = player.getUniqueId().toString();
        else if (teleportUser) {
            String playerName = args[0];

            boolean playerExists = PlayerListManager.existsByName(playerName);
            if (!playerExists) {
                return PLAYER_NOT_FOUND;
            }

            directory = PlayerListManager.getByName(playerName);
        }
        return directory;
    }
}
