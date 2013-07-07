package com.mutinycraft.jigsaw.RankHelper;

import com.mutinycraft.jigsaw.RankHelper.Commands.Rank;
import com.mutinycraft.jigsaw.RankHelper.Commands.RankInfo;
import com.mutinycraft.jigsaw.RankHelper.Commands.Reload;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:47 AM
 */

public class RankHelperCommandExecutor implements CommandExecutor {

    private RankHelper plugin;
    private Rank rank;
    private RankInfo rankInfo;
    private Reload reload;

    public RankHelperCommandExecutor(RankHelper p) {
        plugin = p;
        rank = new Rank(p);
        rankInfo = new RankInfo(p);
        reload = new Reload(p);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankhelper")) {
            if (checkPermission(sender, "rank")) {
                if (args.length != 0 && args[0].equalsIgnoreCase("reload") && checkPermission(sender, "reload")) {
                    reload.execute(sender);
                } else {
                    sendHelpMessage(sender);
                }
                return true;
            } else {
                sendNoPermissionMessage(sender);
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (checkPermission(sender, "rank")) {
                if (args.length == 2) {
                    rank.execute(sender, args[0], args[1]);
                } else if (args.length == 3) {
                    rank.execute(sender, args[0], args[1], args[2]);
                } else {
                    sendHelpMessage(sender);
                }
                return true;
            } else {
                sendNoPermissionMessage(sender);
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("rankinfo")) {
            if (checkPermission(sender, "rankinfo")) {
                if (args.length == 2) {
                    rankInfo.execute(sender, args[0], args[1]);
                } else {
                    sendHelpMessage(sender);
                }
                return true;
            } else {
                sendNoPermissionMessage(sender);
                return true;
            }
        }
        return false;
    }

    /**
     * Sends the command sender a message when they have no permission for a command.
     *
     * @param sender to send the message to.
     */
    private void sendNoPermissionMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
    }

    /**
     * Sends the command sender a message with help information.
     *
     * @param sender to send the message to.
     */
    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "--- RankHelper Command Usage ---");
        sender.sendMessage(ChatColor.YELLOW + "/rank player group [world]");
        sender.sendMessage(ChatColor.YELLOW + "/rankinfo player world");
        sender.sendMessage(ChatColor.YELLOW + "/rankhelper reload");
    }

    /**
     * Check the permission of the provided player with the provided permission.
     *
     * @param sender     to check.
     * @param permission to check.
     */
    private boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission("rankhelper." + permission)) {
            return true;
        }
        return false;
    }
}
