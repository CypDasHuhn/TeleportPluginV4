package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.general.Command;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TeleportTag {
    public static final String TAG_COMMAND = "teleportTag";
    public static void command(CommandSender sender, String[] args, String label) throws NoSuchMethodException {
        if (!(sender instanceof Player player)) return; // command blocks cannot access directories

        Method method = TeleportTag.class.getMethod("isItemType", String.class, Object.class) ;
        String itemType = Command.processArgument(sender, args, args.length, method, Finals.Messages.ITEM_TYPE_NOT_GIVEN, Finals.Messages.ITEM_TYPE_NOT_GIVEN, null, null);

        if (args.length < 1) {
            Message.sendMessage(player, Finals.Messages.ITEM_TYPE_NOT_GIVEN.label);
            return;
        }

        boolean isGlobal = args[0].equals(Finals.Attributes.GLOBAL.label);
        int bonus = isGlobal ? 1 : 0;

        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();

        String itemType = args[0+bonus];
        boolean itemTypeExists = itemType.equalsIgnoreCase(Finals.ItemType.LOCATION.label) || itemType.equalsIgnoreCase(Finals.ItemType.FOLDER.label);
        if (!itemTypeExists) {
            Message.sendMessage(player, Finals.Messages.ITEM_TYPE_NOT_FOUND.label, itemType);
            return;
        }

        if (args.length < 2+bonus) {
            Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
            return;
        }

        String itemName = args[1+bonus];
        boolean itemExists = FileManagerMethods.itemExists(directory, itemName, itemType);
        if (!itemExists) {
            Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_TARGET_FOUND.label, itemName);
            return;
        }

        if (args.length < 3+bonus) {
            Message.sendMessage(player, Finals.Messages.TAG_NOT_GIVEN.label);
            return;
        }
    }

    public static boolean isItemType(String argument, Object... vars) {
        return argument.equalsIgnoreCase(Finals.ItemType.LOCATION.label) || argument.equalsIgnoreCase(Finals.ItemType.FOLDER.label)
    }

    public static List<String> completer(CommandSender sender, String[] args, String label) {
        return null;
    }
}