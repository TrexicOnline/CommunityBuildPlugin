package de.trexic.communitybuildplugin.fixes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WorldEditBlockLimiter implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");

        //Check if a WorldEdit Command contains too high Values
        if(command[0].startsWith("//")) {
            for(String arg : command) {
                try {
                    long value = Long.parseLong(arg);

                    if(value > 40) {
                        event.setMessage("/This value is to high!");
                    }
                }
                catch (NumberFormatException ignored) {}
            }
        }
    }
}
