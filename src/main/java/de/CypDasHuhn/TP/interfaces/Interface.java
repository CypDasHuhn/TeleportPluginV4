package de.CypDasHuhn.TP.interfaces;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.ConfirmingInterface.ConfirmingInterface;
import de.CypDasHuhn.TP.interfaces.ConfirmingInterface.ConfirmingInterfaceListener;
import de.CypDasHuhn.TP.interfaces.FolderInterface.FolderInterface;
import de.CypDasHuhn.TP.interfaces.FolderInterface.FolderInterfaceListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Interface {
    public static HashMap<Player, Boolean> opening = new HashMap<Player, Boolean>();
    public static final HashMap<String, Class> interfaceMap = new HashMap<String, Class>(){{
       put(FolderInterface.interfaceName, FolderInterface.class);
       put(ConfirmingInterface.interfaceName, ConfirmingInterface.class);
    }};

    public static final HashMap<Class, Class> listenerMap = new HashMap<Class, Class>() {{
        put(FolderInterface.class, FolderInterfaceListener.class);
        put(ConfirmingInterface.class, ConfirmingInterfaceListener.class);
    }};

    private static void routeInterface(Player player, String interfaceName) {
        if (!interfaceMap.containsKey(interfaceName)) return;

        opening.put(player, true);
        PlayerDataManager.setInventory(player, interfaceName);

        Class<?> interfaceClass = interfaceMap.get(interfaceName);

        try {
            java.lang.reflect.Method method = interfaceClass.getMethod("getInterface", Player.class);

            Inventory inventory = (Inventory) method.invoke(null, player);

            player.openInventory(inventory);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        opening.put(player, false);
    }
}
