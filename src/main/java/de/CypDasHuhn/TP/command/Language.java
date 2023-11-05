package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.message.Locale;
import de.CypDasHuhn.TP.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Language {
    public static void command(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;
        if (args.length < 1) {
            Message.sendMessage(player, "language_short_argument");
            return;
        }

        String language = args[0];
        boolean isLanguage = Locale.isLocale(language);
        if (!isLanguage) {
            Message.sendMessage(player, "language_locale_not_existing");
            return;
        }

        String currentLanguage = Locale.getLanguage(player);
        boolean sameLanguage = language.equals(currentLanguage);
        if (sameLanguage) {
            Message.sendMessage(player, "language_already_selected");
            return;
        }

        Locale.setLanguage(player, language);
        Message.sendMessage(player, "language_success");
    }

    public static List<String> completer(String[] args) {
        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1:
                List<String> locales = Locale.locales;
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
