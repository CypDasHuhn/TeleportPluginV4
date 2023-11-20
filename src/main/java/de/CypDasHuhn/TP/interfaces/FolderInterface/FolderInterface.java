package de.CypDasHuhn.TP.interfaces.FolderInterface;

import de.CypDasHuhn.TP.DTO.FolderInterfaceDTO;
import de.CypDasHuhn.TP.DTO.ItemDTO;
import de.CypDasHuhn.TP.filemanager.ChildManager;
import de.CypDasHuhn.TP.filemanager.ParentManager;
import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterface;
import de.CypDasHuhn.TP.shared.Finals;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FolderInterface extends SkeletonInterface {
    public static final String interfaceName = "Folder_Interface";

    public static final int previousArrowSlot = 1;
    public static final int nextArrowSlot = 7;

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        String directory = ((FolderInterfaceDTO) vars[0]).directory;
        String parentName = ((FolderInterfaceDTO) vars[0]).parentName;
        int page = ((FolderInterfaceDTO) vars[0]).page;

        PlayerDataManager.setInterfaceInformation(player, (FolderInterfaceDTO) vars[0]);

        int rowAmount = ParentManager.getRowAmount(directory, parentName);
        int lastRow = (rowAmount-1)*9;

        Inventory inventory = Bukkit.createInventory(null, 6*9, getDirectoryColor(directory, player)+"§l"+parentName+" #"+page);

        for (int i = 0; i < lastRow; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE," ",false, null));
        }
        for (int i = lastRow; i < rowAmount*9; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null));
        }
        if (page > 1) inventory.setItem(lastRow+previousArrowSlot, SpigotMethods.createItem(Material.ARROW, "previous", false, null));
        inventory.setItem(lastRow+nextArrowSlot, SpigotMethods.createItem(Material.ARROW, "next", false, null));

        for (int i = 0; i < lastRow; i++) {
            int globalSlot = ((page-1)*lastRow)+i;
            ItemDTO child = ParentManager.getChild(directory, parentName, globalSlot);
            if (child != null) {
                boolean enchanted = child.itemType.equals(Finals.ItemType.FOLDER.label);
                ItemStack visualItem = ChildManager.getItem(directory,child.itemName,child.itemType);
                ItemStack item = SpigotMethods.createItemFromItem(visualItem, "§f"+child.itemName, enchanted, null);
                inventory.setItem(i, item);
            }
        }

        return inventory;
    }

    public static String getDirectoryColor(String directory, Player player) {
        if (player.getUniqueId().toString().equals(directory)) {
            return "§6"; // own directory
        } else if (directory.equals(Finals.GLOBAL)) {
            return "§5"; // global directory
        } else {
            return "§d"; // foreign directory
        }
    }
}
