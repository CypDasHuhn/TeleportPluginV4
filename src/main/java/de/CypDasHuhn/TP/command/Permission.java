package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.message.Message;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    public static void command(CommandSender sender, String[] args) {
        Message.sendMessage(sender, "wip");
    }

    public static List<String> completer(CommandSender sender, String[] args) {
        List<String> arguments = new ArrayList<String>();
        arguments.add(Message.getMessage(sender, "wip"));
        return arguments;
    }
}
