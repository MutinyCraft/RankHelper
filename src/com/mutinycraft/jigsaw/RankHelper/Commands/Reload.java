package com.mutinycraft.jigsaw.RankHelper.Commands;

import com.mutinycraft.jigsaw.RankHelper.RankHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:50 AM
 */

public class Reload {

    RankHelper plugin;

    public Reload(RankHelper p) {
        plugin = p;
    }

    /**
     * Reloads the config.yml.
     *
     * @param sender that issued the command.
     */
    public void execute(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.YELLOW + "RankHelper config.yml has been reloaded.");
    }
}
