package de.trexic.communitybuildplugin.utils;

import com.google.common.collect.Lists;
import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.permissionMangement.Group;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {
    private static Scoreboard scoreboard;
    private static Scoreboard emptyScoreboard;

    public static HashMap<String, Group> groups = new HashMap<>();

    public static void createNewScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        emptyScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static Scoreboard getEmptyScoreboard() {
        return emptyScoreboard;
    }

    public static String commandTextExtractor(String[] command, int startAt) {
        StringBuilder stringBuilder = new StringBuilder();

        //extract text out of command
        int counter = 1;
        for(String arg : command) {
            if (counter >= startAt + 1) {
                String argument = arg.replace("&", "§");

                if (counter == command.length) {
                    stringBuilder.append(argument);
                } else {
                    stringBuilder.append(argument).append(" ");
                }
                counter++;
            }
        }

        return stringBuilder.toString();
    }
}