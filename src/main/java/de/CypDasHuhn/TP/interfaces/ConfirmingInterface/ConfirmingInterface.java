package de.CypDasHuhn.TP.interfaces.ConfirmingInterface;

import de.CypDasHuhn.TP.interfaces.Skeleton.SkeletonInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConfirmingInterface extends SkeletonInterface {
    public static final String interfaceName = "Confirming_Interface";
    public static final Class listener = ConfirmingInterfaceListener.class;

    @Override
    public Inventory getInterface(Player player, Object... vars) {

        return null;
    }
}
