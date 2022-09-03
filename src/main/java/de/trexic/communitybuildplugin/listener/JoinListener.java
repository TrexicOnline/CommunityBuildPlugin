package de.trexic.communitybuildplugin.listener;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.permissionMangement.LuckPermUtils;
import de.trexic.communitybuildplugin.permissionMangement.LuckPermsPrefixLoader;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //teleports the player to the set spawnLocation
        Location spawnLocation = CommunityBuildPlugin.getInstance().getConfig().getLocation("SpawnLocation");
        if(spawnLocation != null)
            player.teleport(spawnLocation);

        //Load players Prefix
        User user = CommunityBuildPlugin.getLuckPerms().getUserManager().getUser(player.getUniqueId());
        LuckPermsPrefixLoader.loadPrefix(user);

        //Send join message
        event.setJoinMessage("");
        Bukkit.broadcastMessage("§f[§a+§f] " + LuckPermUtils.getPrefix(player.getUniqueId()) + player.getDisplayName());
        player.sendMessage("§aWillkommen auf dem §6CommunityServer§a!");


        //Join Sound
        Sound join_sound = Sound.ENTITY_PLAYER_LEVELUP;
        player.playSound(player.getLocation(), join_sound, 1, 0);
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //send quit message
        event.setQuitMessage("");
        Bukkit.broadcastMessage("§f[§c-§f] " + LuckPermUtils.getPrefix(player.getUniqueId()) + player.getDisplayName());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission("communityServer.join")) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cSorry, but you are not allowed to join!");
        }
    }
}
