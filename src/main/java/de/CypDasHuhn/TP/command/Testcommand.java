package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Testcommand extends SkeletonCommand {
    public static final String TEST_COMMAND = "testcommand";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (sender instanceof Player player) {
            player.sendMessage("Test");
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return null;
    }
}
