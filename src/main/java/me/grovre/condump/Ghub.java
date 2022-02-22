package me.grovre.condump;

import org.bukkit.configuration.file.FileConfiguration;
import org.kohsuke.github.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Ghub {

    public static String lastCreatedCommitUrl;
    public static String errorMessage = "Please make a GitHub issue here: https://github.com/Grovre/condump/issues";


    public static void commitToLogRepo(String log) throws IOException {
        // Gets config
        FileConfiguration config = ConDump.getPlugin().getConfig();

        // Sets repo and OAuth token to what's in the config
        String oauthToken = config.getString("GitHubOAuthToken");
        String repoPath = config.getString("RepoPath");
        String fileName = "Console log from " + LocalDateTime.now();

        // Begins building the repo content using token and repo
        GitHub github = new GitHubBuilder().withOAuthToken(oauthToken).build();
        GHRepository repo = github.getRepository(repoPath);
        repo.createContent()
                .content(log)
                .message("/condump")
                .path(fileName)
                .commit();

        // On success, saves the url to a static variable that can be accessed from anywhere. OOP can suck it
        System.out.println("Repo commit success, creating link...");
        lastCreatedCommitUrl = ("https://github.com/" + repoPath + "/blob/main/" + fileName).replaceAll(" ", "%20");
    }

    public static void clearLogRepo() throws IOException {
        FileConfiguration config = ConDump.getPlugin().getConfig();
        // Sets repo and OAuth token to what's in the config
        String oauthToken = config.getString("GitHubOAuthToken");
        String repoPath = config.getString("RepoPath");

        GitHub github = new GitHubBuilder().withOAuthToken(oauthToken).build();
        GHRepository repo = github.getRepository(repoPath);
        List<GHContent> content = repo.getDirectoryContent("");
        content.forEach(c -> {
            try {
                c.delete("Wipe Repo");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
