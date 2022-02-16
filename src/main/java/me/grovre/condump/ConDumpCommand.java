package me.grovre.condump;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ConDumpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!player.hasPermission("condump.dump")) {
            System.out.println(ChatColor.RED + "You don't have permission to dump the most recent console log.");
            return true;
        }
        try {
            Ghub.commitToLogRepo(getLatestLogString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage("Commit is at: " + Ghub.lastCreatedCommitUrl);
        System.out.println("Commit is at: " + Ghub.lastCreatedCommitUrl);
        return true;
    }

    public File getLatestLog() {
        File f = new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + "logs" + File.separator + "latest.log");
        System.out.println("Latest log file is at " + f.getAbsolutePath());
        return f;
    }

    public String getLatestLogString() {
        File f = getLatestLog();
        List<String> allLogLines = null;
        try {
            allLogLines = Files.readLines(f, Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Cannot dump. Read lines failed.");
            e.printStackTrace();
        }

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
