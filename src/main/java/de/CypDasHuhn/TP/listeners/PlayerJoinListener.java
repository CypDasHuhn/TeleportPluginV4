package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.filemanager.PlayerListManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        boolean nameExists = PlayerListManager.existsName(playerName);
        if (!nameExists) {
            String playerUUID = player.getUniqueId().toString();
            boolean uuidExists = PlayerListManager.existsUUID(playerUUID);
            if (!uuidExists) {
                PlayerListManager.add(playerName, playerUUID);
            } else {
                PlayerListManager.replaceName(playerName, playerUUID);
            }
        }
    }
}
