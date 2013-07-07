package com.mutinycraft.jigsaw.RankHelper.Util;

import org.bukkit.ChatColor;

/**
 * User: Jigsaw
 * Date: 7/6/13
 * Time: 4:31 PM
 */

public class MessageHandler {

    /**
     * Replaces customizable tags with the values in the config.yml.
     *
     * @param msg    to replace tags in.
     * @param group  to use as replacement for {GROUP} tag.
     * @param ranked to use as replacement for {RANKED} tag.
     * @param sender to use as replacement for {SENDER} tag.
     * @return
     */
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
