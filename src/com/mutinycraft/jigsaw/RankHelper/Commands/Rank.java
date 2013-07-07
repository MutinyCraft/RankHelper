package com.mutinycraft.jigsaw.RankHelper.Commands;

import com.mutinycraft.jigsaw.RankHelper.MessageHandler;
import com.mutinycraft.jigsaw.RankHelper.RankHelper;
import org.bukkit.ChatColor;
import org.bukkit.World;
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
        if (isValidPlayer(playerToRank, sender) && isAllowedGroupChange(playerToRank, sender) && isValidGroup(group
                .toLowerCase(), sender)) {
            Player player = plugin.getServer().getPlayerExact(playerToRank);
            removeGroup(player);
            addGroup(playerToRank, group);
            sender.sendMessage(msg.replaceTags(plugin.getSenderMessage(), group, playerToRank, sender.getName()));
            player.sendMessage(msg.replaceTags(plugin.getRankedMessage(), group, playerToRank, sender.getName()));
            plugin.getServer().broadcastMessage(msg.replaceTags(plugin.getBroadcastMessage(), group, playerToRank,
                    sender.getName()));
        }
    }

    /**
     * Set the group of a player in only the world specified by the command sender.
     *
     * @param sender
     * @param playerToRank
     * @param group
     * @param worldName
     */
    public void execute(CommandSender sender, String playerToRank, String group, String worldName) {
        if (isValidGroup(group.toLowerCase(), sender) && isValidWorld(worldName)) {
            RankHelper.permission.playerRemoveGroup(plugin.getServer().getWorld(worldName), playerToRank, group);
            RankHelper.permission.playerAddGroup(plugin.getServer().getWorld(worldName), playerToRank, group);
            sender.sendMessage(msg.replaceTags(plugin.getSenderMessage(), group, playerToRank, sender.getName()));
            plugin.getServer().broadcastMessage(msg.replaceTags(plugin.getBroadcastMessage(), group, playerToRank,
                    sender.getName()));
        }
    }

    /**
     * Removes all groups from the provided player.
     *
     * @param player to remove groups from.
     */
    private void removeGroup(Player player) {
        String[] groups = RankHelper.permission.getPlayerGroups(player);
        World world;
        for (String worldName : plugin.getWorlds()) {
            world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                for (String group : groups) {
                    RankHelper.permission.playerRemoveGroup(world, player.getName(), group);
                }
            }
        }
    }

    /**
     * Adds the provided group to the provided player.
     *
     * @param player to add group to.
     * @param group  to add to player.
     */
    private void addGroup(String player, String group) {
        World world;
        for (String worldName : plugin.getWorlds()) {
            world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                RankHelper.permission.playerAddGroup(world, player, group);
            }
        }
    }

    /**
     * Checks whether the provided group is a valid group as specified in the config.yml
     *
     * @param group  to validate.
     * @param sender that issued command.
     * @return true if valid, false otherwise.
     */
    private boolean isValidGroup(String group, CommandSender sender) {
        if (plugin.getGroups().contains(group)) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "The rank {" + group + "} is not a valid rank.");
            return false;
        }
    }

    /**
     * Checks whether the provided player is online.
     *
     * @param playerToRank to check if online
     * @param sender       that issued command.
     * @return true if online, false otherwise.
     */
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

    /**
     * Checks if the provided player is allowed to have their group changed based on permissions.
     *
     * @param playerToRank to check if able to change group.
     * @param sender       that issued command.
     * @return true if allowed to change group, false otherwise.
     */
    private boolean isAllowedGroupChange(String playerToRank, CommandSender sender) {
        Player player = plugin.getServer().getPlayerExact(playerToRank);
        if (!player.hasPermission("rankhelper.norank")) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You are not allowed to change the rank of {" + playerToRank + "}.");
            return false;
        }
    }

    /**
     * Checks if the provided world is a valid (loaded) world.
     *
     * @param worldName of world to check.
     * @return true if valid world, false otherwise.
     */
    private boolean isValidWorld(String worldName) {
        if (plugin.getServer().getWorld(worldName) != null) {
            return true;
        }
        return false;
    }

}
