# condump
Dumps the latest console logs from your latest.log file in logs folder to your designated GitHub repo by using the owner's OAuth token and the GitHub API.

Here's a video of it in use:
https://youtu.be/vCH9S-FcMaQ

The plugin is open source as are pretty much all of my other plugins, it's also on GitHub as you're reading

This plugin is within the GitHub API ToS that can be found here:
https://docs.github.com/en/github/site-policy/github-terms-of-service#h-api-terms

I'm not responsible for you spamming /condump and then receiving an email from GitHub, or anything else that you do but if there are issues, please create one on the GitHub from the link above

# usage
To use, just put the jar in your server plugins folder.
Run the server, and edit the config afterwards with a repo and the OAuth token of the repo's owner.
You can get your GitHub OAuth token in your GitHub settings in the developer settings tab. Remember to get a new OAuth token if you make it so yours resets every so often.

After that, just run /condump and your server's console will be dumped into that repo. Any errors are from incorrectly setting up the repo.
If this happens:
Try to make a new repo but add a file with anything in it. Doesn't matter what's in it. For some reason, GitHub stopped some of my requests because of an empty repo.
Make sure the OAuth token is correct.
Make sure the token belongs to the repo owner.
Make sure your server is allowed to access GitHub, as in a firewall doesn't prevent it or anything.
