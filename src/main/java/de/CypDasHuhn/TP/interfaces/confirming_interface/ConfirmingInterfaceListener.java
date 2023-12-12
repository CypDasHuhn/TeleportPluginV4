package de.CypDasHuhn.TP.interfaces.confirming_interface;

import de.CypDasHuhn.TP.interfaces.skeleton.SkeletonInterfaceListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ConfirmingInterfaceListener extends SkeletonInterfaceListener {
    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        if (clickedMaterial.equals(Material.LIME_STAINED_GLASS_PANE)) {
            // confirm

        } else if (clickedMaterial.equals(Material.RED_STAINED_GLASS_PANE)) {
            // cancel
        }
    }
}
