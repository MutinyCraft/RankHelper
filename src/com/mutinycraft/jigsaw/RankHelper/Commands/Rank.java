package com.mutinycraft.jigsaw.RankHelper.Commands;

import com.mutinycraft.jigsaw.RankHelper.MessageHandler;
import com.mutinycraft.jigsaw.RankHelper.RankHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:49 AM
 */

public class Rank {

    RankHelper plugin;
    MessageHandler msg;

    public Rank(RankHelper p) {
        plugin = p;
        msg = new MessageHandler();
    }

    /**
     * Set the group of a player in all worlds defined in the config.yml.
     *
     * @param sender
     * @param playerToRank
     * @param group
     */
    public void execute(CommandSender sender, String playerToRank, String group) {
        if (isValidGroup(group.toLowerCase(), sender) && isValidPlayer(playerToRank,
                sender) && isAllowedGroupChange(playerToRank, sender)) {
            Player player = plugin.getServer().getPlayerExact(playerToRank);
            removeGroup(player);
            if (addGroup(player, group)) {
                sender.sendMessage(msg.replaceTags(plugin.getSenderMessage(), group, playerToRank, sender.getName()));
                player.sendMessage(msg.replaceTags(plugin.getRankedMessage(), group, playerToRank, sender.getName()));
                plugin.getServer().broadcastMessage(msg.replaceTags(plugin.getBroadcastMessage(), group,
                        playerToRank, sender.getName()));
            } else {
                sender.sendMessage("Something went wrong!");
            }
        }
    }

    /**
     * Set the group of a player in only the world specified by the command sender.
     *
     * @param sender
     * @param playerToRank
     * @param rank
     * @param world
     */
    public void execute(CommandSender sender, String playerToRank, String rank, String world) {

    }

    private void removeGroup(Player player) {
        String[] groups = RankHelper.permission.getPlayerGroups(player);
        for (String group : groups) {
            RankHelper.permission.playerRemoveGroup(player, group);
        }
    }

    private boolean addGroup(Player player, String group) {
        RankHelper.permission.playerAddGroup(player, group);
        return true;
    }

    private boolean isValidGroup(String group, CommandSender sender) {
        if (plugin.getGroups().contains(group)) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "The rank {" + group + "} is not a valid rank.");
            return false;
        }
    }

    private boolean isValidPlayer(String playerToRank, CommandSender sender) {
        Player player = plugin.getServer().getPlayerExact(playerToRank);
        if (player != null && player.isOnline()) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "The player {" + playerToRank + "} is not currently online.  Use " +
                    "/rankoffline if you are sure you want to rank this player.");
            return false;
        }
    }

    private boolean isAllowedGroupChange(String playerToRank, CommandSender sender) {
        Player player = plugin.getServer().getPlayerExact(playerToRank);
        if (!player.hasPermission("rankhelper.norank")) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You are not allowed to change the rank of {" + playerToRank + "}.");
            return false;
        }
    }

}
