package me.grovre.condump.commands;

import me.grovre.condump.Ghub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ConWipeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Makes player null and if the sender is an instance of a player, reassigns player to the sender
        Player player = null;
        if(commandSender instanceof Player) {
            player = (Player) commandSender;
        }

        // Makes sure the player has permission
        if(player != null && !player.hasPermission("condump.wipe")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to dump the most recent console log.");
            return true;
        }

        // Tries to run condump with the player, otherwise console if player is null
        try {
            if(player != null) {
                Bukkit.dispatchCommand(player, "condump");
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "condump");
            }
            // Checks for bad permissions, otherwise prints a stack trace
        } catch (Exception e) {
            if(player != null) {
                if(!player.hasPermission("condump.dump")) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to dump the console, therefore no permission to wipe it!");
                    return true;
                }
            }
            if(player != null) {
                player.sendMessage("Failed to run /condump during conwipe");
            }
            System.out.println("Failed to run /condump during conwipe");
            e.printStackTrace();
            System.out.println(Ghub.errorMessage);
            return true;
        }

        // This is where the logs begin being wiped
        System.out.println("Attempting to wipe latest.log...");
        File log = new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + "logs" + File.separator + "latest.logs");
        System.out.println("latest.log is at " + log.getAbsolutePath());

        // Tries to make an non-appending file writer and write to latest.log with nothing, wiping it
        try {
            Writer fr = new FileWriter(log, false);
            fr.write("WIPED. Wiped content can be found at " + Ghub.lastCreatedCommitUrl);
            fr.close();
        } catch (IOException e) {
            if(player != null) {
                player.sendMessage("Failed to wipe latest.log. See console for details.");
            }
            System.out.println("Failed to wipe latest.log");
            e.printStackTrace();
            System.out.println(Ghub.errorMessage);
            return true;
        }

        // Success message
        if(player != null) {
            player.sendMessage("Successfully created repo commit and wiped log.");
        }
        System.out.println("Successfully created repo commit and wiped log.");

        return true;
    }
}
