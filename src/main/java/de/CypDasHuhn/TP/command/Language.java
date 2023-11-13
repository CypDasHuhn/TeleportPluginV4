package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.filemanager.LocaleManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Language {
    public static final String LANGUAGE_COMMAND = "teleportLanguage";
    public static void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;
        if (args.length < 1) {
            Message.sendMessage(player, Finals.Messages.LANGUAGE_SHORT_ARGS.label);
            return;
        }

        String language = args[0];
        boolean isLanguage = LocaleManager.isLocale(language);
        if (!isLanguage) {
            Message.sendMessage(player, Finals.Messages.LANGUAGE_NOT_FOUND.label);
            return;
        }

        String currentLanguage = LocaleManager.getLanguage(player);
        boolean sameLanguage = language.equals(currentLanguage);
        if (sameLanguage) {
            Message.sendMessage(player, Finals.Messages.LANGUAGE_ALREADY_SELECTED.label);
            return;
        }

        LocaleManager.setLanguage(player, language);
        Message.sendMessage(player, Finals.Messages.LANGUAGE_SUCCESS.label);
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1:
                List<String> locales = LocaleManager.locales;
                arguments.addAll(locales);
                break;
        }
        List <String> result = new ArrayList<String>();
        for (String a : arguments) {
            if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                result.add(a);
            }
        }
        return result;
    }
}
