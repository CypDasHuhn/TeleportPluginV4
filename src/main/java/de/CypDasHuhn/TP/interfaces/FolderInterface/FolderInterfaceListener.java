package de.CypDasHuhn.TP.interfaces.FolderInterface;

import de.CypDasHuhn.TP.DTO.ItemDTO;
import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.filemanager.ParentManager;
import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import org.bukkit.Location;
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

        if (clickedItem == null) return;

        String directory = PlayerDataManager.getDirectory(player);
        String parentName = PlayerDataManager.getParent(player);
        int page = PlayerDataManager.getPage(player);

        int rowAmount = ParentManager.getRowAmount(directory, parentName);
        int lastRow = (rowAmount-1)*9;

        boolean selectedPreviousPage = clickedSlot == lastRow+FolderInterface.previousArrowSlot && clickedMaterial.equals(Material.ARROW);
        if (selectedPreviousPage) {
            Interface.openCurrentInterface(player, directory, parentName, page-1);
            return;
        }
        boolean selectedNextPage = clickedSlot == lastRow+FolderInterface.nextArrowSlot && clickedMaterial.equals(Material.ARROW);
        if (selectedNextPage) {
            Interface.openCurrentInterface(player, directory, parentName, page+1);
            return;
        }

        if (clickedSlot < lastRow+1) {
            boolean isBackground = clickedMaterial == Material.LIGHT_GRAY_STAINED_GLASS_PANE || clickedMaterial == Material.LIME_STAINED_GLASS_PANE;
            if (!isBackground) {
                int globalSlot = ((page-1)*lastRow)+clickedSlot;
                ItemDTO child = ParentManager.getChild(directory, parentName, globalSlot);

                Location childLocation = LocationManager.getLocation(directory, child.itemName);
                player.teleport(childLocation);

                player.closeInventory();
            }
        }

    }
}
