package de.trexic.communitybuildplugin.permissionMangement;

import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import de.trexic.communitybuildplugin.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserLoadEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsPrefixLoader {
    private static LuckPerms luckPerms = CommunityBuildPlugin.getLuckPerms();
    private static EventBus eventBus = luckPerms.getEventBus();

    public static void subscribeEvents() {
        eventBus.subscribe(CommunityBuildPlugin.getInstance(), UserLoadEvent.class, LuckPermsPrefixLoader::userLoadListener);
    }

    private static void userLoadListener(UserLoadEvent event) {
        loadPrefix(event.getUser());
    }

    public static void loadPrefix(User user) {
        String userGroup = user.getPrimaryGroup();
        net.luckperms.api.model.group.Group group = luckPerms.getGroupManager().getGroup(userGroup);

        Group teamGroup;

        if(!Utils.groups.containsKey(userGroup)) {
            int weight = 0;
            String prefix = group.getCachedData().getMetaData().getPrefix();

            if(prefix != null) {
                if(group.getWeight().isPresent()) {
                    weight = group.getWeight().getAsInt();
                }


                teamGroup = new Group(userGroup, prefix, weight);

                Utils.groups.put(userGroup, teamGroup);
            }
            else {
                teamGroup = null;
            }
        }
        else {
            teamGroup = Utils.groups.get(userGroup);
        }

        Player player = Bukkit.getPlayer(user.getUniqueId());

        if(player != null && teamGroup != null) {
            teamGroup.addPlayerToGroup(player);
        }
    }
}
