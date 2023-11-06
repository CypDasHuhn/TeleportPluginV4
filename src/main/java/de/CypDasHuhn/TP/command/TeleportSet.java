package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.message.Locale;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FinalVariables;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportSet {
    public static void command(CommandSender sender, String[] args) {
        // check
        if (!(sender instanceof Player player)) return; // The Command Block cannot give a directory for the command.

        if (args.length < 1) {
            Message.sendMessage(player, "teleport_set_short_argument");
            return;
        }

        String directory = player.getUniqueId().toString();
        String locationName = args[0];
        Location playerLocation = player.getLocation();

        LocationManager.register(sender, directory, locationName, FinalVariables.DEFAULT_PARENT, playerLocation);
    }

    public static List<String> completer(CommandSender sender, String[] args) {
        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1:
                arguments.add("[Name]");
                break;
        }

        return arguments;
    }
}
