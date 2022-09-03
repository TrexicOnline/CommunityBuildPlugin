package de.trexic.communitybuildplugin.methods;

import com.google.common.collect.Lists;
import de.trexic.communitybuildplugin.CommunityBuildPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Hologram {

    //HashMap<UUID ,Object[Location, List<UUID>]>
    public static HashMap<UUID ,Object[]> infoHolograms = new HashMap<>();
    private static String infoHoloText = CommunityBuildPlugin.getInstance().getConfig().getString("BlackBoardText");


    /**
     * A new line can be done with a /n
     */
    public static List<Entity> spawnHolograms(String text, Location destination) {
        List<String> hologramArray = Arrays.asList(text.split("/n"));
        List<String> hologramTexts = Lists.reverse(hologramArray);

        Location location = destination.clone();

        double originY = destination.getY();

        List<Entity> spawnedArmorStands = new ArrayList<>();

        for(String hologramText : hologramTexts) {
            location.add(0, 0.3, 0);
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            armorStand.setVisible(false);
            armorStand.setMarker(true);
            armorStand.setPersistent(true);
            armorStand.setGravity(false);
            armorStand.setCustomNameVisible(true);

            armorStand.setCustomName(hologramText);

            spawnedArmorStands.add(armorStand);
        }

        return spawnedArmorStands;
    }

    /**
     * A new line can be done with a /n
     * @return The UUID of the hologram
     */
    public static UUID spawnHolograms_registered(String text, Location destination) {
        List<String> hologramArray = Arrays.asList(text.split("/n"));
        List<String> hologramTexts = Lists.reverse(hologramArray);

        List<UUID> hologramIDs = new ArrayList<>();

        Location location = destination.clone();

        for(String hologramText : hologramTexts) {
            location.add(0, 0.3, 0);
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            armorStand.setVisible(false);
            armorStand.setMarker(true);
            armorStand.setPersistent(true);
            armorStand.setGravity(false);
            armorStand.setCustomNameVisible(true);

            armorStand.setCustomName(hologramText);

            hologramIDs.add(armorStand.getUniqueId());
        }

        UUID holoID = UUID.randomUUID();
        Object[] infoHolo_data = new Object[] {destination, hologramIDs};

        infoHolograms.put(holoID, infoHolo_data);

        return holoID;
    }

    public static void setInfoHoloText(String text, boolean updateHolos) {
        infoHoloText = text;

        if(updateHolos)
            updateAllInfoHolograms();
    }

    public static String getInfoHoloText() {
        return infoHoloText;
    }

    public static void updateAllInfoHolograms() {
        for(UUID holoID : infoHolograms.keySet()) {
            Location location = (Location) infoHolograms.get(holoID)[0];
            List<UUID> holoEntityUUIDs = (List<UUID>) infoHolograms.get(holoID)[1];

            //Remove holograms
            for(Entity entity : location.getWorld().getEntities()) {
                for(UUID uuid : holoEntityUUIDs) {
                    if(entity.getUniqueId() == uuid) {
                        entity.remove();
                    }
                }
            }

            //Spawn new holograms
            List<UUID> new_holoIDs = new ArrayList<>();
            for(Entity entity : spawnHolograms(infoHoloText, location)) {
                new_holoIDs.add(entity.getUniqueId());
            }

            Object[] holoData = new Object[] {location, new_holoIDs};

            infoHolograms.replace(holoID, holoData);
        }
    }

    public static void updateInfoHologram(UUID holoID, String text) {
        Location location = (Location) infoHolograms.get(holoID)[0];
        List<UUID> holoEntityUUIDs = (List<UUID>) infoHolograms.get(holoID)[1];

        //Remove holograms
        for(Entity entity : location.getWorld().getEntities()) {
            for(UUID uuid : holoEntityUUIDs) {
                if(entity.getUniqueId() == uuid) {
                    entity.remove();
                }
            }
        }

        //Spawn new holograms
        List<UUID> new_holoIDs = new ArrayList<>();
        for(Entity entity : spawnHolograms(text, location)) {
            new_holoIDs.add(entity.getUniqueId());
        }

        Object[] holoData = new Object[] {location, new_holoIDs};

        infoHolograms.replace(holoID, holoData);
    }

    public static void saveBlackBoards() {
        FileConfiguration config = CommunityBuildPlugin.getInstance().getConfig();

        config.set("BlackBoards", null);

        for(UUID holoID : infoHolograms.keySet()) {
            String pref = "BlackBoards." + holoID + ".";

            Location holoLocation = (Location) infoHolograms.get(holoID)[0];
            List<UUID> holograms = (List<UUID>) infoHolograms.get(holoID)[1];

            List<String> holograms_str = new ArrayList<>();
            for(UUID hologramID : holograms) {
                holograms_str.add(hologramID.toString());
            }

            config.set(pref + "location", holoLocation);
            config.set(pref + "holograms", holograms_str);
        }

        CommunityBuildPlugin.getInstance().saveConfig();
        CommunityBuildPlugin.getInstance().reloadConfig();
    }

    public static void loadBlackBoards() {
        infoHolograms.clear();

        FileConfiguration config = CommunityBuildPlugin.getInstance().getConfig();

        for(String blackBoardID_str : config.getConfigurationSection("BlackBoards").getKeys(false)) {
            String pref = "BlackBoards." + blackBoardID_str + ".";

            UUID holoID = UUID.fromString(blackBoardID_str);
            Location holoLocation = config.getLocation(pref + "location");
            List<String> holograms_str = config.getStringList(pref + "holograms");

            List<UUID> holograms = new ArrayList<>();

            for(String hologram_str : holograms_str) {
                holograms.add(UUID.fromString(hologram_str));
            }

            Object[] data = new Object[] {holoLocation, holograms};

            infoHolograms.put(holoID, data);
        }
    }
}