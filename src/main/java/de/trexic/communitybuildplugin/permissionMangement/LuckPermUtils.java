package de.trexic.communitybuildplugin.permissionMangement;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LuckPermUtils {
    private static final LuckPerms luckPerms = CommunityBuildPlugin.getLuckPerms();

    public static String getPrefix(UUID uuid) {
        String p_group = luckPerms.getUserManager().getUser(uuid).getPrimaryGroup();
        Group group = luckPerms.getGroupManager().getGroup(p_group);

        String prefix = group.getCachedData().getMetaData().getPrefix();
        if(prefix == null) {
            prefix = "";
        }
        return prefix;
    }
}
