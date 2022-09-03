package de.trexic.communitybuildplugin.commands;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.methods.Hologram;
import de.trexic.communitybuildplugin.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class CommandHologramUpdate implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("communityServer.holograms.update")) {
            String infoText = "§b§lGaming§5§lUniverse/n" + Utils.commandTextExtractor(args, 0);
            Hologram.setInfoHoloText(infoText, true);

            CommunityBuildPlugin.getInstance().getConfig().set("BlackBoardText", infoText);
            CommunityBuildPlugin.getInstance().saveConfig();
            CommunityBuildPlugin.getInstance().reloadConfig();

            sender.sendMessage("§aSuccessfully updated all §0§lBlack§f§lBoards§a!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
