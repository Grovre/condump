package me.grovre.condump;

import me.grovre.condump.commands.ConDumpCommand;
import me.grovre.condump.commands.ConWipeCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ConDump extends JavaPlugin {

    public static ConDump plugin;
    public static FileConfiguration config;

    public static ConDump getPlugin() {
        return plugin;
    }

    /*
    Permissions:
    condump.dump
    condump.wipe
     */

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        plugin = this;
        config = getConfig();

        // Command hashmap with commands and CommandExecutor
        HashMap<String, CommandExecutor> commands = new HashMap<>();
        // New commands here, no /
        commands.put("condump", new ConDumpCommand());
        commands.put("conwipe", new ConWipeCommand());

        // Makes sure the config.yml exists because this plugin won't work without it
        try {
            String oauthToken = config.getString("GitHubOAuthToken");
            String repoPath = config.getString("RepoPath");
            assert oauthToken != null;
            assert repoPath != null;

            // If the OAuth token or repopath in config are the default values
            if (oauthToken.equalsIgnoreCase("tokenhere") || repoPath.equalsIgnoreCase("githubName/repoName")) {
                System.out.println("Please change the config located at " + config.getCurrentPath() + "!");
                System.out.println(getName() + " will be disabled until the changes are made.");
                getServer().getPluginManager().disablePlugin(this);
            }

            // Only catches if the config retrieves null values
        } catch (NullPointerException e) {
            System.out.println("Config values either empty or contains null values." +
                    "\nSee if the plugin created the config correctly or if the config has values.");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Command EXECUTIONATOR(s)!
        // Makes sure setExecutor doesn't return null for whatever reason. No clue why it would but...
        try {
            // Registers the command executors
            for(String command : commands.keySet()) {
                CommandExecutor cmdExec = commands.get(command); // CommandExecutor, file of command
                PluginCommand pluginCmd = getServer().getPluginCommand(command); // Plugin command, registers the actual command
                assert pluginCmd != null; // Makes sure neither are null
                assert cmdExec != null;
                pluginCmd.setExecutor(cmdExec);
            }
        } catch (NullPointerException e) {
            System.out.println("A con executor was null? Disabling plugin to avoid further errors.");
            e.printStackTrace();
            System.out.println(Ghub.errorMessage);
            System.out.println("The plugin will be disabled to prevent further errors.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
     // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "condump"); // Throws an error, disabled plugins can't use commands
    }
}
