package de.CypDasHuhn.TP.command.general;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomTabCompleter implements TabCompleter {
    // if the label (the command written) equals to one of the aliases in aliasesMap,
    // it takes the result of the linked completer and returns it.
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();

        for (HashMap.Entry<String, String[]> entry : de.CypDasHuhn.TP.command.general.Command.aliasesMap.entrySet()) {
            String commandLabel = entry.getKey();
            String[] aliases = entry.getValue();

            System.out.println("ein tt!");

            if (label.equalsIgnoreCase(commandLabel) || Arrays.stream(aliases).anyMatch(alias -> label.equalsIgnoreCase(alias))) {
                Class<?> commandClass = de.CypDasHuhn.TP.command.general.Command.commandMap.get(commandLabel);
                try {
                    Method completerMethod = commandClass.getMethod("completer", CommandSender.class, String[].class, String.class);
                    arguments = (List<String>) completerMethod.invoke(null, sender, args, label);
                    break;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        }

        int argEnd = args.length - 1;
        List<String> result = new ArrayList<>();
        for (String argument : arguments) {
            if (argument.toLowerCase().startsWith(args[argEnd].toLowerCase())) {
                result.add(argument);
            }
        }

        return result;
    }

}
