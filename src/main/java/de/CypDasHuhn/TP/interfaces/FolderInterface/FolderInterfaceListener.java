package de.CypDasHuhn.TP.interfaces.FolderInterface;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FolderInterfaceListener {
    public static final String interfaceName = FolderInterface.interfaceName;
    public static void listener(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        Material clickedMaterial = clickedItem.getType();
        int clickedSlot = event.getSlot();

        String parentName = PlayerDataManager.getParent(player);
        int page = PlayerDataManager.getPage(player);

        boolean selectedPreviousPage = clickedSlot == FolderInterface.previousArrowSlot && clickedMaterial.equals(Material.ARROW);
        if (selectedPreviousPage) {
            player.sendMessage("previous");
            Interface.openCurrentInterface(player, parentName, page-1);
            return;
        }
        boolean selectedNextPage = clickedSlot == FolderInterface.nextArrowSlot && clickedMaterial.equals(Material.ARROW);
        if (selectedNextPage) {
            player.sendMessage("next");
            Interface.openCurrentInterface(player, parentName, page+1);
            return;
        }

    }
}
