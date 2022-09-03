package de.trexic.communitybuildplugin.listener;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class AntiLag implements Listener {

    public void onRedstone(BlockRedstoneEvent event) {
        event.setNewCurrent(0);
    }

    public void onPhysics(BlockPhysicsEvent event) {
        if(event.getSourceBlock().getType() == Material.ARMOR_STAND) {
            event.setCancelled(true);
        }
    }
}