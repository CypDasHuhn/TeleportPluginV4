package de.CypDasHuhn.TP.interfaces;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.filemanager.PlayerListManager;
import de.CypDasHuhn.TP.interfaces.ConfirmingInterface.ConfirmingInterface;
import de.CypDasHuhn.TP.interfaces.ConfirmingInterface.ConfirmingInterfaceListener;
import de.CypDasHuhn.TP.interfaces.FolderInterface.FolderInterface;
import de.CypDasHuhn.TP.interfaces.FolderInterface.FolderInterfaceListener;
import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterface;
import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterfaceListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Interface {
    public static HashMap<Player, Boolean> opening = new HashMap<Player, Boolean>();
    public static final HashMap<String, SkeletonInterface> interfaceMap = new HashMap<String, SkeletonInterface>(){{
       put(FolderInterface.interfaceName, new FolderInterface());
       put(ConfirmingInterface.interfaceName, new ConfirmingInterface());
    }};

    public static final HashMap<String, SkeletonInterfaceListener> listenerMap = new HashMap<String, SkeletonInterfaceListener>(){{
        put(FolderInterface.interfaceName, new FolderInterfaceListener());
        put(ConfirmingInterface.interfaceName, new ConfirmingInterfaceListener());
    }};

    public static void openTargetInterface(Player player, String interfaceName, Object... vars) {
        if (!interfaceMap.containsKey(interfaceName)) return;

        opening.put(player, true);
        PlayerDataManager.setInventory(player, interfaceName);

        SkeletonInterface skeletonInterface = interfaceMap.get(interfaceName);

        Inventory customInterface = skeletonInterface.getInterface(player, vars);
        player.openInventory(customInterface);

        opening.put(player, false);
    }

    public static void openCurrentInterface(Player player, Object... vars) {
        String interfaceName = PlayerDataManager.getInventory(player);

        openTargetInterface(player, interfaceName, vars);
    }
}
