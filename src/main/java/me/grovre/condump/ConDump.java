package me.grovre.condump;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ConDump extends JavaPlugin {

    public static ConDump plugin;

    public static ConDump getPlugin() {
        return plugin;
    }

    /*
    Permissions:
    condump.dump
     */

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        saveDefaultConfig();

        // Makes sure the config.yml exists because this plugin won't work without it
        if(!(new File(getDataFolder().getAbsolutePath() + "/config.yml").exists())) {
            System.out.println("Config is being generated. Once done, please edit it!");
            setEnabled(false);
        }

        // Command EXECUTIONATOR!
        getServer().getPluginCommand("condump").setExecutor(new ConDumpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Always dumps console to repo on shutdown, just cuz
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "condump");
    }
}
