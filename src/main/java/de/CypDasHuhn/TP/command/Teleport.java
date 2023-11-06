package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.CustomFiles;
import de.CypDasHuhn.TP.filemanager.ListManager;
import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FinalVariables;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Teleport {
    private static final String PLAYER_NOT_FOUND= "player_not_found";
    public static void command(CommandSender sender, String[] args, String label) {
        // check
        if (args.length < 1) {
            // INTERFACE TO-DO
            Message.sendMessage(sender, "teleport_short_argument");
            return;
        }

        boolean teleport = label.equalsIgnoreCase("t") || label.equalsIgnoreCase("teleport");
        boolean teleportGlobal = label.equalsIgnoreCase("tg") || label.equalsIgnoreCase("teleportGlobal");
        boolean teleportUser = label.equalsIgnoreCase("tu") || label.equalsIgnoreCase("teleport");

        Boolean[] commandTypes = {teleport, teleportGlobal, teleportUser};

        if (teleport && !(sender instanceof Player)) {
            return;
        }

        if (teleportUser && args.length < 2) {
            // INTERFACE TO-DO
            Message.sendMessage(sender, "teleport_user_short_argument");
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

        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration locationConfig = customFiles[0].gfc(locationName, directory+"/"+FinalVariables.LOCATION);

        Location teleportLocation = locationConfig.getLocation("Location");

        Player targetPlayer = null;
        String targetPlayerName = null;
        if ((teleport || teleportGlobal) && args.length > 1) targetPlayerName = args[2];
        else if (teleportUser && args.length > 2) targetPlayerName = args[3];
        else if (player != null) targetPlayer = player;
        else return;


        if (targetPlayerName != null) {
            targetPlayer = Bukkit.getPlayer(targetPlayerName);
            if (targetPlayer == null) {
                Message.sendMessage(player, "player_not_found");
                return;
            }
        }

        targetPlayer.teleport(teleportLocation);
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        boolean teleport = label.equalsIgnoreCase("t") || label.equalsIgnoreCase("teleport");
        boolean teleportGlobal = label.equalsIgnoreCase("tg") || label.equalsIgnoreCase("teleportGlobal");
        boolean teleportUser = label.equalsIgnoreCase("tu") || label.equalsIgnoreCase("teleport");

        Boolean[] commandTypes = {teleport, teleportGlobal, teleportUser};

        String directory = getDirectory(sender, commandTypes, args);

        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1 -> {
                if (teleport || teleportGlobal) {
                    List<String> locations = ListManager.getItems(null,FinalVariables.LOCATION);
                    arguments.addAll(locations);
                }

                if (teleportUser) {

                }
            }
            case 2 -> {
                if (teleport ||teleportGlobal) {

                }

                if (teleportUser) {
                    List<String> locations = ListManager.getItems(null,FinalVariables.LOCATION);
                    arguments.addAll(locations);
                }
            }
            case 3 -> {
                if (teleportUser) {

                }
            }

        }
        arguments.add(Message.getMessage(sender, "wip"));
        return arguments;
    }

    public static String getDirectory(CommandSender sender, Boolean[] commandType, String[] args) {
        boolean teleport = commandType[0];
        boolean teleportGlobal = commandType[1];
        boolean teleportUser = commandType[2];

        Player player = null;
        BlockCommandSender blockCommandSender = null;
        if (sender instanceof Player) player = (Player) sender;
        if (sender instanceof BlockCommandSender) blockCommandSender = (BlockCommandSender) sender;
        Location senderLocation = null;
        if (player != null) senderLocation = player.getLocation();
        else senderLocation = blockCommandSender.getBlock().getLocation();

        String directory = "";
        if (teleportGlobal) directory = "General";
        else if (teleport) directory = player.getUniqueId().toString();
        else if (teleportUser) {
            directory = SpigotMethods.getPlayer(args[0], senderLocation).getUniqueId().toString();
            if (directory == null) {
                return PLAYER_NOT_FOUND;
            }
        }
        return directory;
    }
}
