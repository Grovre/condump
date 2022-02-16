package me.grovre.condump;

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
        if(!(new File(getDataFolder().getAbsolutePath() + "/config.yml").exists())) {
            System.out.println("Config is being generated. Once done, please edit it!");
            getPlugin().setEnabled(false);
        }

        getServer().getPluginCommand("condump").setExecutor(new ConDumpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
