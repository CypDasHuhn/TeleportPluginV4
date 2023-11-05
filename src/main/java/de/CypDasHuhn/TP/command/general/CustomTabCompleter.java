package de.CypDasHuhn.TP.command.general;

import de.CypDasHuhn.TP.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CustomTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (label.toLowerCase()) {
            case "testcommand" -> { return Testcommand.completer(sender, args, label); }
            case "t", "teleport", "tu", "teleportuser", "tg", "teleportgeneral" -> { return Teleport.completer(sender, args, label); }
            case "ts", "teleportset" -> { return TeleportSet.completer(sender, args); }
            case "te", "teleportedit" -> { return TeleportEdit.completer(sender, args, label); }
            case "td", "teleportdelete" -> { return TeleportDelete.completer(sender, args, label); }
            case "tl", "teleportlanguage" -> { return Language.completer(args); }
        }
        return null;
    }
}
