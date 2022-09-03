package de.trexic.communitybuildplugin.methods;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldManagement {

    public static void createWorld(String worldName, WorldType worldType) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.type(worldType);
        worldCreator.createWorld();
    }

    public static void createEmptyWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new VoidGenerator());
        worldCreator.createWorld();
    }

    public static void createIsland(String worldName, Biome biome) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new VoidGenerator());
        worldCreator.generateStructures(false);
        worldCreator.biomeProvider(new BiomeProvider() {
            @Override
            public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                return biome;
            }

            @Override
            public List<Biome> getBiomes(WorldInfo worldInfo) {
                return Collections.emptyList();
            }
        });
        worldCreator.createWorld();
    }

    public static void createEmptyWorld(World world) {
        WorldCreator worldCreator = new WorldCreator(world.getName());
        worldCreator.generator(new VoidGenerator());
        worldCreator.createWorld();
    }

    public static void unloadWorld(String name, boolean save) {
        //Check if world is loaded
        World world = isWorldLoaded(name);
        if(world != null) {
            Bukkit.unloadWorld(world, save);
        }
    }

    public static World isWorldLoaded(String name) {
        for(World world : Bukkit.getWorlds()) {
            if(world.getName().equals(name)) {
                return world;
            }
        }

        return null;
    }

    public static boolean warpToWorld(String worldName, Player player, Location location) {
        World world = isWorldLoaded(worldName);

        if(world != null) {
            location.setWorld(world);

            if(!world.isChunkLoaded(world.getChunkAt(location))) {
                world.loadChunk(world.getChunkAt(location));
            }

            player.teleport(location);

            return true;
        }
        else {
            return false;
        }
    }

    public static boolean warpToWorld(World world, Player player, Location location) {
        if(world != null) {
            location.setWorld(world);

            if(!world.isChunkLoaded(world.getChunkAt(location))) {
                world.loadChunk(world.getChunkAt(location));
            }

            player.teleport(location);

            return true;
        }
        else {
            return false;
        }
    }

    public static List<String> getAllWorldNames() {
        List<String> worldNames = new ArrayList<>();

        for(World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }

        return worldNames;
    }
}