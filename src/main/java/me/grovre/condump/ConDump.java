package me.grovre.condump;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConDump extends JavaPlugin {

    public static ConDump plugin;
    public static FileConfiguration config;

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
        saveDefaultConfig();
        plugin = this;
        config = getConfig();

        // Makes sure the config.yml exists because this plugin won't work without it
        if(config.getString("GitHubOAuthToken").equals("tokenhere")
        || config.getString("RepoPath").equals("githubName/repoName"))
        {
            System.out.println("Please change the config located at " + config.getCurrentPath() + "!");
            System.out.println(getName() + " will be disabled until the changes are made.");
            getServer().getPluginManager().disablePlugin(this);
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
