package de.CypDasHuhn.TP.command.general;

import de.CypDasHuhn.TP.command.*;
import de.CypDasHuhn.TP.message.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class Command implements CommandExecutor {
    public static final HashMap<String, Class> commandMap = new HashMap<String, Class>(){{
        put(Teleport.TELEPORT_COMMAND, Teleport.class);
        put(Teleport.TELEPORT_USER_COMMAND, Teleport.class);
        put(Teleport.TELEPORT_GLOBAL_COMMAND, Teleport.class);
        put(TeleportSet.TELEPORT_SET_COMMAND, TeleportSet.class);
        put(TeleportEdit.TELEPORT_EDIT_COMMAND, TeleportEdit.class);
        put(TeleportDelete.TELEPORT_DELETE_COMMAND, TeleportDelete.class);
        put(Permission.PERMISSION_COMMAND, Permission.class);
        put(Language.LANGUAGE_COMMAND, Language.class);
        put(TeleportFolder.FOLDER_COMMAND, TeleportFolder.class);
        put(TeleportTag.TAG_COMMAND, TeleportTag.class);
    }};

    public static final HashMap<String, String[]> aliasesMap = new HashMap<String, String[]>(){{
        put(Teleport.TELEPORT_COMMAND, new String[]{"teleport", "t"});
        put(Teleport.TELEPORT_USER_COMMAND, new String[]{"teleportUser", "tUser", "tu"});
        put(Teleport.TELEPORT_GLOBAL_COMMAND, new String[]{"teleportGlobal", "tGlobal", "tg"});
        put(TeleportSet.TELEPORT_SET_COMMAND, new String[]{"teleportSet", "tSet", "ts"});
        put(TeleportEdit.TELEPORT_EDIT_COMMAND, new String[]{"teleportEdit", "tEdit", "te"});
        put(TeleportDelete.TELEPORT_DELETE_COMMAND, new String[]{"teleportDelete", "tDelete", "td"});
        put(Permission.PERMISSION_COMMAND, new String[]{"teleportPermission", "tPermission", "tpm"});
        put(Language.LANGUAGE_COMMAND, new String[]{"teleportLanguage", "tLanguage", "tl"});
        put(TeleportFolder.FOLDER_COMMAND, new String[]{"teleportFolder", "tFolder", "tf"});
        put(TeleportTag.TAG_COMMAND, new String[]{"teleportTag", "tTag", "tt"});

        put(Testcommand.TEST_COMMAND, new String[]{"testCommand"});
    }};

    // if the label (the command written) equals to one of the aliases in aliasesMap,
    // it parses the command of the linked Class with the needed attributes.
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (HashMap.Entry<String, String[]> entry : aliasesMap.entrySet()) {
            String commandLabel = entry.getKey();
            String[] aliases = entry.getValue();

            if (label.equalsIgnoreCase(commandLabel) || Arrays.stream(aliases).anyMatch(alias -> label.equalsIgnoreCase(alias))) {
                Class commandClass = commandMap.get(commandLabel);
                try {
                    Method method = commandClass.getMethod("command", CommandSender.class, String[].class, String.class) ;
                    method.invoke(null, sender, args, label);
                    return true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean isCommand(String label, String command) {
        String[] aliases = Command.aliasesMap.get(command);
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }
}