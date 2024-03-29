package me.grovre.condump.commands;

import com.google.common.io.Files;
import me.grovre.condump.ConDump;
import me.grovre.condump.Ghub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ConDumpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String[] args) {

        // Makes player null and if the sender is an instance of player, sets player to sender
        Player player = sender instanceof Player ? (Player) sender : null;

        // Makes sure player has permissions
        if(player != null && !player.hasPermission("condump.dump")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to dump the most recent console log.");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(ConDump.getPlugin(), () -> {
            boolean failFlag = false;

            // Tries to get the latest logs as a string, line breaks and all
            try {
                Ghub.commitToLogRepo(getLatestLogAsString());
            } catch (IOException e) {
                if (player != null) {
                    player.sendMessage(Ghub.errorMessage);
                }
                System.out.println("Failed to commit to repo.");
                e.printStackTrace();
                System.out.println(Ghub.errorMessage);
                failFlag = true;
            }
            if(failFlag) return;

            // Messages the player who executed /condump and the console about the new commit with the link for viewing
            // Success message
            if (player != null) {
                player.sendMessage("Commit is at: " + Ghub.lastCreatedCommitUrl);
            }
            System.out.println("Commit is at: " + Ghub.lastCreatedCommitUrl);
        });

        return true;
    }

    public File getLatestLogFile() {
        // Sets f = latest.log, or what the console is currently outputting to
        File f = new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + "logs" + File.separator + "latest.log");
        System.out.println("Latest log file is at " + f.getAbsolutePath());
        return f;
    }

    public String getLatestLogAsString() {
        // Sets f = Server/logs/latest.log
        File f = getLatestLogFile();
        // Gets all lines of the log as a list (thanks Google)
        List<String> allLogLines = null;
        try {
            allLogLines = Files.readLines(f, Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Cannot dump. Read lines failed.");
            e.printStackTrace();
            System.out.println(Ghub.errorMessage);
        }

        // With all log lines in a new list, goes through and puts them all back together, trimming them
        assert allLogLines != null;
        StringBuilder log = new StringBuilder(allLogLines.get(0));
        for (int i = 1; i < allLogLines.size(); i++) {
            String line = allLogLines.get(i);
            line = line.trim();
            log.append("\n").append(line);
        }

        return log.toString();
    }
}
