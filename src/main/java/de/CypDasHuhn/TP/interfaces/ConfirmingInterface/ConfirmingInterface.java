package de.CypDasHuhn.TP.interfaces.ConfirmingInterface;

import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterface;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfirmingInterface extends SkeletonInterface {
    public static final String interfaceName = "Confirming_Interface";

    private static final int inventorySize = 6*9;

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        Inventory inventory = Bukkit.createInventory(null, inventorySize, "§a§lConfirming Interface");

        ItemStack cancelItem = SpigotMethods.createItem(Material.RED_STAINED_GLASS_PANE, "§ccancel", false, null);
        for (int i = 0; i < inventorySize; i++) {
            inventory.setItem(i, cancelItem);
        }

        int randomSlot = (int) (Math.random()*inventorySize);
        ItemStack confirmItem = SpigotMethods.createItem(Material.LIME_STAINED_GLASS_PANE, "§aconfirm", true, null);
        inventory.setItem(randomSlot, confirmItem);

        return inventory;
    }
}
