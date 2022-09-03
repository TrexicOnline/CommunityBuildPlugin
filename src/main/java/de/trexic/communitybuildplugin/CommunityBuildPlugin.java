package de.trexic.communitybuildplugin;

import de.trexic.communitybuildplugin.commands.*;
import de.trexic.communitybuildplugin.fixes.WorldEditBlockLimiter;
import de.trexic.communitybuildplugin.listener.ColoredChatListener;
import de.trexic.communitybuildplugin.listener.JoinListener;
import de.trexic.communitybuildplugin.listener.AntiLag;
import de.trexic.communitybuildplugin.methods.Hologram;
import de.trexic.communitybuildplugin.permissionMangement.LuckPermsPrefixLoader;
import de.trexic.communitybuildplugin.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommunityBuildPlugin extends JavaPlugin {

    private static LuckPerms luckPerms;
    private static CommunityBuildPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        loadConfig();

        Utils.createNewScoreboard();

        loadLuckperms();

        loadListener();

        loadCommands();

        Hologram.loadBlackBoards();

        logMessage("§aSuccessfully loaded the CommunityBuildPlugin!");
    }

    @Override
    public void onDisable() {
        //save Blackboards
        Hologram.saveBlackBoards();
    }


    private void loadLuckperms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        else {
            logMessage("§cCouldn't load LuckPerms!");
            this.setEnabled(false);
        }

        LuckPermsPrefixLoader.subscribeEvents();
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new WorldEditBlockLimiter(), this);
        pluginManager.registerEvents(new ColoredChatListener(), this);
        pluginManager.registerEvents(new AntiLag(), this);
    }

    private void loadCommands() {
        this.getCommand("head").setExecutor(new CommandHead());
        this.getCommand("reloadPrefix").setExecutor(new CommandReloadPrefix());
        this.getCommand("blackboard").setExecutor(new CommandHologramUser());
        this.getCommand("updateblackboard").setExecutor(new CommandHologramUpdate());
        this.getCommand("setLobbySpawn").setExecutor(new CommandSetLobby());
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
    }

    public static void logMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static CommunityBuildPlugin getInstance() {
        return instance;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
}