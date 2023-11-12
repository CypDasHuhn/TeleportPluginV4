package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void inventoryClickListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventory = PlayerDataManager.getInventory(player);

        boolean emptyInventory = inventory.equals(Finals.EMPTY);
        if (emptyInventory) return;

        boolean illegalInventory = !Interface.interfaceMap.containsKey(inventory);
        if (illegalInventory) return;

        Class<?> InterfaceClass = Interface.interfaceMap.get(inventory);

        if (Interface.listenerMap.containsKey(InterfaceClass)) {
            Class<?> ListenerClass = Interface.listenerMap.get(InterfaceClass);
            try {
                java.lang.reflect.Method listenerMethod = ListenerClass.getMethod("listener", InventoryClickEvent.class);
                listenerMethod.invoke(null, event);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}