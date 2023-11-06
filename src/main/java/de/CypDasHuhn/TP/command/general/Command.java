package de.CypDasHuhn.TP.command.general;

import de.CypDasHuhn.TP.command.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        switch (label.toLowerCase()) {
            case "testcommand" -> Testcommand.command(sender, args, label);
            case "t", "teleport", "tu", "teleportuser", "tg", "teleportglobal" -> Teleport.command(sender, args, label);
            case "ts", "teleportset" -> TeleportSet.command(sender, args);
            case "te", "teleportedit" -> TeleportEdit.command(sender, args);
            case "td", "teleportdelete" -> TeleportDelete.command(sender, args);
            case "tl", "teleportlanguage" -> Language.command(sender, args);
        }
        return false;
    }

}