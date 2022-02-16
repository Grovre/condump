package me.grovre.condump;

import org.bukkit.configuration.file.FileConfiguration;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

public class Ghub {

    public static String lastCreatedCommitUrl;

    public static void commitToLogRepo(String log) throws IOException {
        FileConfiguration config = ConDump.getPlugin().getConfig();

        String gistToken = config.getString("GitHubOAuthToken");
        String repoPath = config.getString("RepoPath");
        String fileName = "Console log from " + LocalDateTime.now();

        GitHub github = new GitHubBuilder().withOAuthToken(gistToken).build();
        GHRepository repo = github.getRepository(repoPath);
        repo.createContent()
                .content(log)
                .message("/condump")
                .path(fileName)
                .commit();
        System.out.println("Repo commit success, creating link...");
        lastCreatedCommitUrl = ("https://github.com/" + repoPath + "/blob/main/" + fileName).replaceAll(" ", "%20");
    }
}
