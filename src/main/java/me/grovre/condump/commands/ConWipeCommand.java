package me.grovre.condump.commands;

import me.grovre.condump.ConDump;
import me.grovre.condump.Ghub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

public class ConWipeCommand implements CommandExecutor {

    // TODO make it clear a repo instead

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender,
                             @NonNull Command command,
                             @NonNull String s,
                             @NonNull String[] strings) {

        // Makes player null and if the sender is an instance of a player, reassigns player to the sender
        Player player = commandSender instanceof Player ? (Player) commandSender : null;

        // Makes sure the player has permission
        if(player != null && !player.hasPermission("condump.wipe")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to wipe.");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(ConDump.getPlugin(), () -> {
            boolean failFlag = false;
            
            long timeTaken = 0;
            try {
                long start = System.currentTimeMillis();
                Ghub.clearLogRepo();
                long end = System.currentTimeMillis();
                timeTaken = end - start;
                System.out.println("Repo successfully cleared in " + timeTaken / 1000D + " seconds " +
                        "(" + timeTaken + "ms)");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to wipe repo.");
                System.out.println(Ghub.errorMessage);
                failFlag = true;
            }
            if(failFlag) return;

            if (player != null) {
                player.sendMessage("Repo successfully cleared in " + timeTaken / 1000D + " seconds " +
                        "(" + timeTaken + "ms)");
            }
        });
        
        return true;
    }
}
