package de.CypDasHuhn.TP.interfaces;

import de.CypDasHuhn.TP.file_manager.player_manager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.confirming_interface.ConfirmingInterface;
import de.CypDasHuhn.TP.interfaces.confirming_interface.ConfirmingInterfaceListener;
import de.CypDasHuhn.TP.interfaces.folder_interface.FolderInterface;
import de.CypDasHuhn.TP.interfaces.folder_interface.FolderInterfaceListener;
import de.CypDasHuhn.TP.interfaces.skeleton.SkeletonInterface;
import de.CypDasHuhn.TP.interfaces.skeleton.SkeletonInterfaceListener;
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
