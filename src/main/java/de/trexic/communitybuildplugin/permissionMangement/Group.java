package de.trexic.communitybuildplugin.permissionMangement;

import de.trexic.communitybuildplugin.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
    private Team team;
    private String name;
    private String prefix;
    private int weight;

    public Group(String name, String prefix, int weight) {
        createNewTeam(name, weight);
        setPrefix(prefix);
        Utils.groups.put(name, this);
    }

    public void setPrefix(String prefix) {
        this.team.setPrefix(prefix);
    }

    private void createNewTeam(String name, int weight) {
        this.team = Utils.getScoreboard().registerNewTeam(weight + name);
    }

    public Team getTeam() {
        return this.team;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getWeight() {
        return weight;
    }

    public void addPlayerToGroup(Player player) {
        this.team.addEntry(player.getDisplayName());
        player.setScoreboard(Utils.getScoreboard());
    }

    public void removePlayerFromGroup(Player player) {
        this.team.removeEntry(player.getDisplayName());
        player.setScoreboard(Utils.getScoreboard());
    }
}