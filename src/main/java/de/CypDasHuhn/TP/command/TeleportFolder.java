package de.CypDasHuhn.TP.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeleportFolder {
    public static final String FOLDER_COMMAND = "teleportFolder";
    public static void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return; // command blocks cannot access directories


    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        return null;
    }
}
