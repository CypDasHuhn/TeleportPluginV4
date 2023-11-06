package de.CypDasHuhn.TP.command.general;

import de.CypDasHuhn.TP.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CustomTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List <String> arguments = new ArrayList<String>();
        switch (label.toLowerCase()) {
            case "testcommand" -> arguments = Testcommand.completer(sender, args, label);
            case "t", "teleport", "tu", "teleportuser", "tg", "teleportgeneral" -> arguments = Teleport.completer(sender, args, label);
            case "ts", "teleportset" -> arguments = TeleportSet.completer(sender, args);
            case "te", "teleportedit" -> arguments = TeleportEdit.completer(sender, args);
            case "td", "teleportdelete" -> arguments = TeleportDelete.completer(sender, args);
            case "tl", "teleportlanguage" -> arguments = Language.completer(args);
            case "tp", "teleportpermission" -> arguments = Permission.completer(sender, args);
        }

        List <String> result = new ArrayList<String>();
        for (String argument : arguments) {
            if (argument.toLowerCase().startsWith(args[0].toLowerCase())) {
                result.add(argument);
            }
        }

        return result;
    }
}
