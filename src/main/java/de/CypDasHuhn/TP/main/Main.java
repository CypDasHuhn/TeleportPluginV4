package de.CypDasHuhn.TP.main;
import de.CypDasHuhn.TP.command.general.CustomTabCompleter;
import de.CypDasHuhn.TP.command.general.Command;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;
    public static String pluginName;
    private static final String[] commands = {"testcommand", "teleport", "teleportUser", "teleportGlobal", "teleportSet", "teleportEdit", "teleportDelete", "teleportLanguage"};

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
    }

    public static Main getPlugin(){
        return plugin;
    }
}
