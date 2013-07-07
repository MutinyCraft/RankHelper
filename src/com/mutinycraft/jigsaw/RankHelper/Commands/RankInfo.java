package com.mutinycraft.jigsaw.RankHelper.Commands;

import com.mutinycraft.jigsaw.RankHelper.RankHelper;
import com.mutinycraft.jigsaw.RankHelper.Util.Validation;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:50 AM
 */

public class RankInfo {

    RankHelper plugin;
    Validation v;

    public RankInfo(RankHelper p) {
        plugin = p;
        v = new Validation(plugin);
    }

    /**
     * Sends the command sender the groups that the provided player is part of in the provided world.
     *
     * @param sender     that issued the command.
     * @param playerName to check groups of.
     * @param worldName  to check groups in.
     */
    public void execute(CommandSender sender, String playerName, String worldName) {
        if (v.isValidPlayer(playerName, sender) && v.isValidWorld(worldName, sender)) {
            World world = plugin.getServer().getWorld(worldName);
            StringBuilder sb = new StringBuilder();
            for (String group : RankHelper.permission.getPlayerGroups(world, playerName)) {
                sb.append(group);
                sb.append(" ");
            }
            sender.sendMessage(ChatColor.GREEN + "The player " + playerName + "has the following groups in " +
                    worldName + ": " + sb.toString());
        }
    }

}
