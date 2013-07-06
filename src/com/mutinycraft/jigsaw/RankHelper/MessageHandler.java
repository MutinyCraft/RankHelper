package com.mutinycraft.jigsaw.RankHelper;

import org.bukkit.ChatColor;

/**
 * User: Jigsaw
 * Date: 7/6/13
 * Time: 4:31 PM
 */

public class MessageHandler {

    public String replaceTags(String msg, String group, String ranked, String sender) {
        StringBuilder sb = new StringBuilder();
        String groupTag = "{GROUP}";
        String rankedTag = "{RANKED}";
        String senderTag = "{SENDER}";

        sb.append(msg);

        int position = sb.lastIndexOf(groupTag);
        if (position != -1) {
            sb.replace(position, position + groupTag.length(), group);
        }

        position = sb.lastIndexOf(rankedTag);
        if (position != -1) {
            sb.replace(position, position + rankedTag.length(), ranked);
        }

        position = sb.lastIndexOf(senderTag);
        if (position != -1) {
            sb.replace(position, position + senderTag.length(), sender);
        }

        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
}
