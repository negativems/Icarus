package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

// TODO: Do it
public class VoteCommand extends ServerCommand<Icarus> {

    public VoteCommand() {
        super("vote", "Vote the server on NameMC.", "<message>", "bc");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String rankReward = plugin.getConfig().getString("reward.rank");
        ConfigurationSection config = plugin.getConfig().getConfigurationSection("lastReclaimed");
        int maxVotes = config.getInt("maxVotesPerDay");
        long lastVote = config.getLong("lastVote");

        if (args.length == 0) {
            player.sendMessage(Messages.COMMAND_VOTE_USAGE.toString(rankReward, String.valueOf(maxVotes * 2)));
            return;
        }

        /*
         *  ARG: Check
         */

        if (args.length == 1 && args[0].equalsIgnoreCase("check")) {
            UserProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());

            if (profile.getVoted() != 0) {
                player.sendMessage(Messages.COMMAND_VOTE_ALREADY.toString());
                return;
            }

//            long cooldown = System.currentTimeMillis() - profile.getVoted();
//            if (profile.lastVoted() + 5000 > System.currentTimeMillis()) {
//                int cooldownInt = Math.toIntExact((5000 - cooldown) / 1000) + 1;
//                player.sendMessage(Messages.HAS_COOLDOWN.toString(String.valueOf(cooldownInt)));
//                return;
//            }

            long nextVoteMillis = (config.getLong("lastVote") + 86400000) - System.currentTimeMillis();
            if (nextVoteMillis < System.currentTimeMillis()){
                config.set("votesLeft",maxVotes);
            }

            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, profile::vote);
            return;
        }

        /*
         *  ARG: Reset
         */

        if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
            if (player.hasPermission("vote.reset")){
                config.set("votesLeft", config.getInt("maxVotesPerDay"));
                plugin.saveConfig();
                player.sendMessage(Messages.COMMAND_VOTE_RESETED.toString());
                return;
            }
            player.sendMessage(Messages.NO_PERMISSION.toString());
        }

        player.sendMessage(Messages.COMMAND_VOTE_USAGE.toString(rankReward, String.valueOf(maxVotes * 2)));

    }
}
