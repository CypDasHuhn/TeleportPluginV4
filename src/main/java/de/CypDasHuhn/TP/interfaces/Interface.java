package de.CypDasHuhn.TP.interfaces;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.ConfirmingInterface.ConfirmingInterface;
import de.CypDasHuhn.TP.interfaces.FolderInterface.FolderInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Interface {
    public static HashMap<Player, Boolean> opening = new HashMap<Player, Boolean>();
    public static final HashMap<String, Class> interfaceMap = new HashMap<String, Class>(){{
       put(FolderInterface.interfaceName, FolderInterface.class);
       put(ConfirmingInterface.interfaceName, ConfirmingInterface.class);
    }};

    private static void routeInterface(Player player, String interfaceName) {
        if (interfaceMap.containsKey(interfaceName)) return;

        Inventory inventory = null;

        opening.put(player, true);
        PlayerDataManager.setInventory(player, interfaceName);

        inventory = // call the method corresponding to the value of the key interfaceName in the interfaceMap

        player.openInventory(inventory);
        opening.put(player, false);
    }
}
