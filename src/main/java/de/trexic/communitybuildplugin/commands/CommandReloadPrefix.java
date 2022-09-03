package de.trexic.communitybuildplugin.commands;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.permissionMangement.LuckPermsPrefixLoader;
import de.trexic.communitybuildplugin.utils.Utils;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandReloadPrefix implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("communityServer.prefix.update")) {
            Utils.createNewScoreboard();
            Utils.groups.clear();

            for(Player player : Bukkit.getOnlinePlayers()) {
                player.setScoreboard(Utils.getEmptyScoreboard());
                User user = CommunityBuildPlugin.getLuckPerms().getUserManager().getUser(player.getUniqueId());
                LuckPermsPrefixLoader.loadPrefix(user);
            }
            sender.sendMessage("§aAll prefixes where updated!");
        }
        else {
            sender.sendMessage("§cSorry, but you don't have the permission to do that!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
