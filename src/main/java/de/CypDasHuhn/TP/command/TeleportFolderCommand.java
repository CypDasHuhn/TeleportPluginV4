package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.Skeleton.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeleportFolderCommand extends SkeletonCommand {
    public static final String FOLDER_COMMAND = "teleportFolder";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return; // command blocks cannot access directories


    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return null;
    }
}
