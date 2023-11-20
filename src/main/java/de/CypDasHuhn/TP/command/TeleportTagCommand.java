package de.CypDasHuhn.TP.command;

import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.TP.file_manager.item_manager.ListManager;
import de.CypDasHuhn.TP.file_manager.player_manager.PermissionManager;
import de.CypDasHuhn.TP.file_manager.item_manager.TagManager;
import de.CypDasHuhn.TP.message.Message;
import de.CypDasHuhn.TP.shared.FileManagerMethods;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeleportTagCommand extends SkeletonCommand {
    public static final String TAG_COMMAND = "teleportTag";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return; // command blocks cannot access directories

        boolean isGlobal = args.length >= 1 && args[0].equals(Finals.Attributes.GLOBAL.label);
        int bonus = isGlobal ? 1 : 0;

        if (args.length < 1+bonus) { // Item Type Start//////////
            Message.sendMessage(player, Finals.Messages.ITEM_TYPE_NOT_GIVEN.label);
            return;
        }

        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();

        String itemType = args[0+bonus];
        boolean itemTypeExists = itemType.equalsIgnoreCase(Finals.ItemType.LOCATION.label) || itemType.equalsIgnoreCase(Finals.ItemType.FOLDER.label);
        if (!itemTypeExists) {
            Message.sendMessage(player, Finals.Messages.ITEM_TYPE_NOT_FOUND.label, itemType);
            return;
        }///////////////////////////// Item Type End////////////


        if (args.length < 2+bonus) {  // Item Name Start //////////
            Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_TARGET_GIVEN.label);
            return;
        }

        String itemName = args[1+bonus];
        boolean itemExists = FileManagerMethods.itemExists(directory, itemName, itemType);
        if (!itemExists) {
            Message.sendMessage(player, Finals.Messages.NO_LOCATION_NAME_TARGET_FOUND.label, itemName);
            return;
        }/////////////////////////////////// Item Name End

        if (args.length < 3+bonus) {  // Tag Mode Start //////////
            Message.sendMessage(player, Finals.Messages.TAG_MODE_NOT_GIVEN.label);
            return;
        }

        String tagMode = args[2+bonus];
        boolean tagModeExists = tagMode.equals(Finals.TagModes.ADD.label) || tagMode.equals(Finals.TagModes.REMOVE.label) || tagMode.equals(Finals.TagModes.TOGGLE.label) ;
        if (!tagModeExists) {
            Message.sendMessage(player, Finals.Messages.TAG_MODE_NOT_FOUND.label, itemName);
            return;
        }/////////////////////////////////// Tag Mode End

        if (args.length < 4+bonus) {  // Tag Mode Start //////////
            Message.sendMessage(player, Finals.Messages.TAG_MODE_NOT_GIVEN.label);
            return;
        }

        String tagName = args[3+bonus]; ////////////////// Tag Mode End

        if (args.length < 5+bonus) { ///// Tag Name Start ///////////
            Message.sendMessage(player, Finals.Messages.TAG_NOT_GIVEN.label);
            return;
        }
        String tag = args[4+bonus]; ////// Tag Name End

        Finals.TagModes tagModeEnum = Arrays.stream(Finals.TagModes.values())
                .filter(mode -> mode.label.equals(tagName))
                .findFirst()
                .orElseThrow();

        switch (tagModeEnum) {
            case TOGGLE -> {
                boolean exists = TagManager.findID(directory, tagName, itemName, itemType) != Finals.NULL_INT;
                if (!exists) TagManager.addItem(player, directory, tagName, itemName, itemType);
                else TagManager.removeItem(player, directory, tagName, itemName, itemType);
            }
            case ADD -> {
                TagManager.addItem(player, directory, tagName, itemName, itemType);
            }
            case REMOVE -> {
                TagManager.removeItem(player, directory, tagName, itemName, itemType);
            }
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        if (!(sender instanceof  Player player)) return arguments; // command blocks cannot access a directory
        player.sendMessage("GRR");
        boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
        boolean isGlobal = args.length > 0 && args[0].equals(Finals.Attributes.GLOBAL.label);
        int isGlobalBonus = isGlobal ? 1 : 0;
        String directory = isGlobal ? Finals.GLOBAL : player.getUniqueId().toString();

        if (isGlobal && !isPermissioned) return arguments;

        int itemTypeIndex = 1+isGlobalBonus;
        boolean isItemType = args.length > itemTypeIndex && ( args[itemTypeIndex].equals(Finals.ItemType.LOCATION.label) || args[itemTypeIndex].equals(Finals.ItemType.FOLDER.label));
        if (args.length > itemTypeIndex && !isItemType) return arguments;
        String itemType = isItemType ? args[itemTypeIndex] : "";

        int itemNameIndex = 2+isGlobalBonus;
        boolean itemExists = args.length > itemNameIndex && FileManagerMethods.itemExists(directory, args[itemNameIndex], itemType);
        if (args.length > itemNameIndex && !itemExists) return arguments;

        if (args.length == 1) {
            if (isPermissioned) arguments.add(Finals.Attributes.GLOBAL.label);
        }
        if (args.length == 1+isGlobalBonus) {
            arguments.add(Finals.ItemType.LOCATION.label);
            arguments.add(Finals.ItemType.FOLDER.label);
        } else if (args.length == 2+isGlobalBonus) {
            List<String> items = ListManager.getItems(directory, itemType);
            arguments.addAll(items);
        } else if (args.length == 3+isGlobalBonus) {
            arguments.add(Finals.TagModes.ADD.label);
            arguments.add(Finals.TagModes.REMOVE.label);
            arguments.add(Finals.TagModes.TOGGLE.label);
        } else if (args.length == 4+isGlobalBonus) {
            List<String> tags = ListManager.getItems(directory, Finals.ItemType.TAG.label);
            arguments.addAll(tags);
        }
        return arguments;
    }
}