package com.mutinycraft.jigsaw.RankHelper.Commands;

import com.mutinycraft.jigsaw.RankHelper.MessageHandler;
import com.mutinycraft.jigsaw.RankHelper.RankHelper;
import com.mutinycraft.jigsaw.RankHelper.Util.Validation;
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
    Validation v;

    public Rank(RankHelper p) {
        plugin = p;
        msg = new MessageHandler();
        v = new Validation(plugin);
    }

    /**
     * Set the group of a player in all worlds defined in the config.yml.
     *
     * @param sender
     * @param playerToRank
     * @param group
     */
    public void execute(CommandSender sender, String playerToRank, String group) {
        if (v.isValidPlayer(playerToRank, sender) && v.isAllowedGroupChange(playerToRank,
                sender) && v.isValidGroup(group.toLowerCase(), sender) && v.hasPermissionForGroup(group, sender)) {
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
        if (v.isValidPlayer(playerToRank, sender) && v.isAllowedGroupChange(playerToRank,
                sender) && v.isValidGroup(group.toLowerCase(), sender) && v.isValidWorld(worldName,
                sender) && v.hasPermissionForGroup(group, sender)) {
            Player player = plugin.getServer().getPlayerExact(playerToRank);
            removeGroup(player, worldName);
            addGroup(playerToRank, group, worldName);
            sender.sendMessage(msg.replaceTags(plugin.getSenderMessage(), group, playerToRank, sender.getName()));
            plugin.getServer().getPlayerExact(playerToRank).sendMessage(msg.replaceTags(plugin.getRankedMessage(),
                    group, playerToRank, sender.getName()));
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
        String[] groups;
        World world = null;
        if(plugin.isGlobalRank()){
            groups = RankHelper.permission.getPlayerGroups(world, player.getName());
            for (String group : groups) {
                RankHelper.permission.playerRemoveGroup(world, player.getName(), group);
            }
        }
        else{
            groups = RankHelper.permission.getPlayerGroups(player);
            for (String worldName : plugin.getWorlds()) {
                world = plugin.getServer().getWorld(worldName);
                if (world != null) {
                    for (String group : groups) {
                        RankHelper.permission.playerRemoveGroup(world, player.getName(), group);
                    }
                }
            }
        }
    }

    /**
     * Removes all groups from the provided player in only a single provided world.
     *
     * @param player    to remove groups from.
     * @param worldName world to remove groups in.
     */
    private void removeGroup(Player player, String worldName) {
        String[] groups = RankHelper.permission.getPlayerGroups(player);
        World world = plugin.getServer().getWorld(worldName);
        if (world != null) {
            for (String group : groups) {
                RankHelper.permission.playerRemoveGroup(world, player.getName(), group);
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
        World world = null;
        if(plugin.isGlobalRank()){
            RankHelper.permission.playerAddGroup(world, player, group);
        }
        else{
            for (String worldName : plugin.getWorlds()) {
                world = plugin.getServer().getWorld(worldName);
                if (world != null) {
                    RankHelper.permission.playerAddGroup(world, player, group);
                }
            }
        }
    }

    /**
     * Adds the provided group to the provided player in the provided world.
     *
     * @param player    to add group to.
     * @param group     to add to player.
     * @param worldName world to add group in.
     */
    private void addGroup(String player, String group, String worldName) {
        World world = plugin.getServer().getWorld(worldName);
        if (world != null) {
            RankHelper.permission.playerAddGroup(world, player, group);
        }
    }
}
