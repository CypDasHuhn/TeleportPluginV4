package de.CypDasHuhn.TP.interfaces.FolderInterface;

import de.CypDasHuhn.TP.filemanager.PlayerDataManager;
import de.CypDasHuhn.TP.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FolderInterface {
    public static final String interfaceName = "Folder_Interface";
    public static final Class listener = FolderInterfaceListener.class;

    public static final int previousArrowSlot = 6*9-7-1;
    public static final int nextArrowSlot = 6*9-1-1;

    public static Inventory getInterface(Player player, Object... vars) {
        String parentName = (String) vars[0];
        int page = (int) vars[1];

        PlayerDataManager.setParent(player, parentName);
        PlayerDataManager.setPage(player, page);

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§6§l"+parentName+" #"+page);

        for (int i = 0; i < 5*9; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE," ",false, null));
        }
        for (int i = 5*9; i < 6*9; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null));
        }
        if (page > 0) inventory.setItem(previousArrowSlot, SpigotMethods.createItem(Material.ARROW, "previous", false, null));
        inventory.setItem(nextArrowSlot, SpigotMethods.createItem(Material.ARROW, "next", false, null));

        return inventory;
    }
}
