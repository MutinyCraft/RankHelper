package com.mutinycraft.jigsaw.RankHelper;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:29 AM
 */

public class RankHelper extends JavaPlugin {

    private Logger log;
    private File configFile;
    private FileConfiguration config;
    private List<String> groups;
    private List<String> worlds;
    private boolean globalRank;
    private boolean broadcast;
    private boolean notifyRanked;
    private boolean notifySender;
    private String broadcastMessage;
    private String senderMessage;
    private String rankedMessage;
    private RankHelperCommandExecutor cmdExecutor;
    private static final String VERSION = " v0.2";

    public static Permission permission;

    public void onEnable() {

        log = getLogger();
        try {
            configFile = new File(getDataFolder(), "config.yml");
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        config = new YamlConfiguration();
        if (!setupPermissions()) {
            log.severe("Vault was not successfully hooked.  Ensure you have the latest version of Vault.");
        }
        try {
            firstRun();
        } catch (Exception e) {
            log.info("There was an error loading the config.yml!");
            e.printStackTrace();
        }
        loadYamls();
        setConfigOptions();
        loadCommands();
    }

    private void loadCommands() {
        cmdExecutor = new RankHelperCommandExecutor(this);
        getCommand("rankhelper").setExecutor(cmdExecutor);
        getCommand("rank").setExecutor(cmdExecutor);
        getCommand("rankinfo").setExecutor(cmdExecutor);
    }

    private void setConfigOptions() {
        groups = config.getStringList("Groups");
        worlds = config.getStringList("Worlds");
        globalRank = config.getBoolean("Global-Rank", false);
        broadcast = config.getBoolean("Broadcast", true);
        notifyRanked = config.getBoolean("Notify-Ranked", true);
        notifySender = config.getBoolean("Notify-Sender", true);
        broadcastMessage = config.getString("Broadcast-Message", "&e{RANKED} is now a {GROUP}");
        rankedMessage = config.getString("Ranked-Message", "&bNOTICE: &cYour rank has been changed to {GROUP} by " +
                "{SENDER}");
        senderMessage = config.getString("Sender-Message", "&bNOTICE: &cYou have changed the rank of {RANKED} to " +
                "{GROUP}");
    }

    public void saveYamls() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadYamls() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void firstRun() throws Exception {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            copy(getResource("config.yml"), configFile);
        }
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream fout = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                fout.write(buf, 0, len);
            }
            fout.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration
                (net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public boolean isNotifyRanked() {
        return notifyRanked;
    }

    public boolean isNotifySender() {
        return notifySender;
    }

    public boolean isGlobalRank() {
        return globalRank;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public String getRankedMessage() {
        return rankedMessage;
    }

    @Override
    public void reloadConfig() {
        loadYamls();
        setConfigOptions();
    }

    @Override
    public void onDisable() {
        log.info(getName() + VERSION + " disabled!");
    }
}
