package de.trexic.communitybuildplugin.listener;

import de.trexic.communitybuildplugin.permissionMangement.LuckPermUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColoredChatListener implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(player.hasPermission("communityServer.chat.colors")) {
            message = message.replace("&0", "§0");
            message = message.replace("&1", "§1");
            message = message.replace("&2", "§2");
            message = message.replace("&3", "§3");
            message = message.replace("&4", "§4");
            message = message.replace("&5", "§5");
            message = message.replace("&6", "§6");
            message = message.replace("&7", "§7");
            message = message.replace("&8", "§8");
            message = message.replace("&9", "§9");
            message = message.replace("&a", "§a");
            message = message.replace("&b", "§b");
            message = message.replace("&c", "§c");
            message = message.replace("&d", "§d");
            message = message.replace("&e", "§e");
            message = message.replace("&f", "§f");
        }
        if(player.hasPermission("communityServer.chat.formats")) {
            message = message.replace("&k", "§k");
            message = message.replace("&l", "§l");
            message = message.replace("&m", "§m");
            message = message.replace("&n", "§n");
            message = message.replace("&o", "§o");
            message =  message.replace("&r", "§r");
        }

        message = LuckPermUtils.getPrefix(player.getUniqueId()) + "§f" + player.getName() + " §8» §7" + message;

        event.setCancelled(true);
        Bukkit.broadcastMessage(message);
    }
}
