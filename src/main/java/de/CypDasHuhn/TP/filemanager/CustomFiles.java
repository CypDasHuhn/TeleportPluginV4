package de.CypDasHuhn.TP.filemanager;

import de.CypDasHuhn.TP.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomFiles {
    private File file;
    private FileConfiguration customFile;
    //GetFileConfiguration
    public FileConfiguration gfc(String name, String folder) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin(Main.pluginName).getDataFolder()+"/"+folder, name+".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        return customFile;
    }

    public void save() {
        try {
            if (this.customFile != null){
                customFile.save(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveArray(CustomFiles[] cf) {
        for (CustomFiles customFile : cf) {
            customFile.save();
        }
    }

    public static CustomFiles[] getCustomFiles(int amount) {
        CustomFiles[] cf = new CustomFiles[amount];
        for (int i = 0; i < amount; i++) {
            cf[i] = new CustomFiles();
        }return cf;
    }

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public void delete(String name, String folder) {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin(Main.pluginName).getDataFolder() + "/" + folder, name + ".yml");

        if (file.exists()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }

        directory.delete();
    }

}