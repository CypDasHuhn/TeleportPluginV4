package de.CypDasHuhn.TP.main;
import de.CypDasHuhn.TP.command.general.CustomTabCompleter;
import de.CypDasHuhn.TP.command.general.Command;
import de.CypDasHuhn.TP.listeners.InventoryClickListener;
import de.CypDasHuhn.TP.listeners.InventoryCloseListener;
import de.CypDasHuhn.TP.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;
    public static String pluginName;
    private static final String[] commands = {"testcommand", "teleport", "teleportUser", "teleportGlobal", "teleportSet", "teleportEdit", "teleportDelete", "teleportLanguage", "teleportPermission"};
    private static final Listener[] listeners = {new PlayerJoinListener(), new InventoryClickListener(), new InventoryCloseListener()};

    public void onEnable(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        plugin = this;
        pluginName = getDescription().getName();

        for (String a : commands) {
            getCommand(a).setExecutor(new Command());
            getCommand(a).setTabCompleter(new CustomTabCompleter());
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }

    }

    public static Main getPlugin(){
        return plugin;
    }
}
