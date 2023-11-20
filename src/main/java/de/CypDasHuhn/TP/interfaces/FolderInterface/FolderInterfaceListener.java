package de.CypDasHuhn.TP.interfaces.FolderInterface;

import de.CypDasHuhn.TP.DTO.FolderInterfaceDTO;
import de.CypDasHuhn.TP.DTO.ItemDTO;
import de.CypDasHuhn.TP.filemanager.LocationManager;
import de.CypDasHuhn.TP.filemanager.ParentManager;
import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterfaceListener;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FolderInterfaceListener extends SkeletonInterfaceListener {
    
    @Override
    public void listener(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        Material clickedMaterial = clickedItem.getType();
        int clickedSlot = event.getSlot();

        if (clickedItem == null) return;

        FolderInterfaceDTO data = PlayerDataManager.getInterfaceInformation(player);

        String directory = data.directory;
        String parentName = data.parentName;
        int page = data.page;
        boolean isMoving = data.moving;

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
                int globalSlot = ((page - 1) * lastRow) + clickedSlot;
                ItemDTO child = ParentManager.getChild(directory, parentName, globalSlot);
                boolean isParent = child.itemType.equals(Finals.ItemType.FOLDER.label);

                if (isParent && (event.isLeftClick() || event.isRightClick())) { //
                    data.parentName = child.itemName;
                    Interface.openCurrentInterface(player, data);
                    return;
                }

                if (isMoving) {
                    if ((event.isLeftClick() || event.isRightClick()) || (isParent && event.isShiftClick())) { // swap location, swap parent
                        // swap
                    }
                    return;
                }

                if (event.isLeftClick()) { // teleport / edit
                    if (!event.isShiftClick()) { // teleport (cannot be entering parent)
                        if (!isParent) {
                            Location childLocation = LocationManager.getLocation(directory, child.itemName);
                            player.teleport(childLocation);

                            player.closeInventory();
                            return;
                        }
                    } else { // edit
                        return;
                    }
                } else if (event.isRightClick()){ // move / undefined
                    if (!event.isShiftClick()) { // move
                        return;
                    } else { // undefined
                        return;
                    }
                }


            } else {
                if (isMoving) {
                    if (!event.isShiftClick()) {
                        // move item to
                    }
                } else if (event.isShiftClick()) {
                    // create item
                }
            }
        }

    }
}
