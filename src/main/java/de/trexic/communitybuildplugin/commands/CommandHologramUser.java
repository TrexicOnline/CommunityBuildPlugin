package de.trexic.communitybuildplugin.commands;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.methods.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CommandHologramUser implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            Location location = player.getLocation();
            location.add(0,player.getEyeHeight(true),0);

            UUID hologramID = Hologram.spawnHolograms_registered("§b§lGaming§5§lUniverse/n§eNews und Infos werden hier angezeigt!", location);

            Bukkit.getScheduler().runTaskLater(CommunityBuildPlugin.getInstance(),(Runnable) new BukkitRunnable() {
                @Override
                public void run() {
                    Hologram.updateInfoHologram(hologramID, Hologram.getInfoHoloText());
                }
            }, 200L);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
