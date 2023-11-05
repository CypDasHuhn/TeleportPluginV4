package de.CypDasHuhn.TP.shared;

import de.CypDasHuhn.TP.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SpigotMethods {
    public static Player getPlayer(String playerString, Location location) {
        switch (playerString) {
            case "@p" -> {
                return getNearestPlayer(location);
            }
            case "@r" -> {
                return getRandomPlayer();
            }
            default -> {
                Player player = Bukkit.getPlayer(playerString);
                return player;
            }
        }
    }

    public static Player getRandomPlayer() {
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        if (players.length == 0) return null;
        int randomIndex = new Random().nextInt(players.length);
        Player randomPlayer = players[randomIndex];
        return randomPlayer;
    }


    public static Player getNearestPlayer(Location location) {
        double shortestDistance = Double.MAX_VALUE;
        Player nearestPlayer = null;

        for (Player player : Bukkit.getOnlinePlayers()) {
            double playerDistance = player.getLocation().distance(location);
            if (playerDistance < shortestDistance) {
                shortestDistance = playerDistance;
                nearestPlayer = player;
            }
        }

        return nearestPlayer;
    }

}
