package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Testcommand {
    public static void command(CommandSender sender, String[] args, String label) {
        Message.sendMessage(sender, "test_message");
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        return null;
    }
}
