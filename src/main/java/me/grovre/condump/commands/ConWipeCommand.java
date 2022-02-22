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

    // TODO make it clear a repo instead

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Makes player null and if the sender is an instance of a player, reassigns player to the sender
        Player player = commandSender instanceof Player ? (Player) commandSender : null;

        // Makes sure the player has permission
        if(player != null && !player.hasPermission("condump.wipe")) {
            return true;
        }

        try {
            Ghub.clearLogRepo();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to wipe repo.");
            System.out.println(Ghub.errorMessage);
            return true;
        }

        if(player != null) {
            player.sendMessage("Repo successfully cleared");
        }
        return true;
    }
}
