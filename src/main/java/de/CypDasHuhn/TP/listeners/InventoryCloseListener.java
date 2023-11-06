package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void inventoryCloseListener(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        PlayerDataManager.setInventory(player, null);
    }
}