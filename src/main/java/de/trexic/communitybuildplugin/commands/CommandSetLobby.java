package de.trexic.communitybuildplugin.commands;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandSetLobby implements TabExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("communityServer.setLobby")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                Location spawnLocation = new Location(player.getWorld(), player.getLocation().getBlockX() + 0.5d,
                        player.getLocation().getBlockY(), player.getLocation().getBlockZ() + 0.5d, player.getLocation().getYaw(),
                        player.getLocation().getPitch());

                CommunityBuildPlugin.getInstance().getConfig().set("SpawnLocation", spawnLocation);
                CommunityBuildPlugin.getInstance().saveConfig();
                CommunityBuildPlugin.getInstance().reloadConfig();

                player.sendMessage("§aSuccessfully set the SpawnLocation!");
            }
            else {
                sender.sendMessage("§cOnly a player can use this command!");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
