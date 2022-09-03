package de.trexic.communitybuildplugin.commands;

import de.trexic.communitybuildplugin.methods.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Command_WorldManagement implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("commutiyServer.worldmanagement")) {
            if(args.length >= 1) {
                switch (args[0]) {
                    case "create":
                        command_create(sender, args);
                        return true;
                    case "tp":
                        command_tp(sender, args);
                        return true;
                    case "unload":
                        command_unload(sender, args);
                        return true;
                    default:
                        sender.sendMessage("§c" + args[0] + " was not found!");
                }
            }
            else {
                sender.sendMessage("§cWrong command usage!");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return Arrays.asList("create", "tp", "unload");
        }

        else if(args.length == 2) {
            if(args[0].equals("tp")) {
                return WorldManagement.getAllWorldNames();
            }
            else if(args[0].equals("unload")) {
                return WorldManagement.getAllWorldNames();
            }
        }

        else if(args.length == 3) {
            if(args[0].equals("create")) {
                return Arrays.asList("NORMAL", "FLAT", "LARGE_BIOMES", "AMPLIFIED", "VOID", "ISLAND");
            }
        }

        else if(args.length == 4) {
            List<String> biomes = new ArrayList<>();
            for(Biome biome : Biome.values()) {
                biomes.add(biome.name());
            }
            return biomes;
        }

        return Collections.emptyList();
    }


    private void command_create(CommandSender sender, String[] args) {
        if(args.length == 2) {
            sender.sendMessage("§eLoading world...\n§7§o(This can take some time)");

            long time = System.currentTimeMillis();
            WorldManagement.createWorld(args[1], WorldType.NORMAL);
            sender.sendMessage("§aThe world was successfully loaded! §a(This took " + (new BigDecimal((System.currentTimeMillis() - time) / 1000L)).setScale(2, RoundingMode.HALF_UP) + " seconds)");
        }
        else if (args.length == 3){
            if(args[2].equalsIgnoreCase("VOID")) {
                sender.sendMessage("§eCreating Void...\n§7§o(This can take some time)");
                WorldManagement.createEmptyWorld(args[1]);
                sender.sendMessage("§aThe world was successfully loaded!");
            }
            else {
                WorldType worldType = WorldType.getByName(args[2]);

                if(worldType == null) {
                    sender.sendMessage("§cWorld-Type \"" + args[2] + "\" was not found!");
                }
                else {
                    sender.sendMessage("§eLoading world...\n§7§o(This can take some time)");
                    WorldManagement.createWorld(args[1], worldType);
                    sender.sendMessage("§aThe world was successfully loaded!");
                }
            }
        }
        else if(args.length > 3) {
            if(args[2].equalsIgnoreCase("ISLAND")) {
                try{
                    Biome biome = Biome.valueOf(args[3]);
                    long startTime = System.currentTimeMillis();
                    WorldManagement.createIsland(args[1], biome);
                    sender.sendMessage("§aIsland Prefab-World was successfully created! §a(This took " +
                            (new BigDecimal((System.currentTimeMillis() - startTime) / 1000L)).setScale(2,
                                    RoundingMode.HALF_UP) +
                            " seconds)");
                }catch (Exception e) {
                    sender.sendMessage("§cUnknown Biome!");
                    e.printStackTrace();
                }
            }
        }
        else {
            sender.sendMessage("§cWrong command usage!");
        }
    }

    private void command_tp(CommandSender sender, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length >= 2) {
                World world = WorldManagement.isWorldLoaded(args[1]);

                if(world != null) {
                    Location spawn = world.getSpawnLocation();

                    player.sendMessage("§eTeleporting...");

                    WorldManagement.warpToWorld(world, player, spawn);
                }
                else {
                    player.sendMessage("§cWorld is not loaded or wasn't created yet. \n§eTo load an already existing world, just use /world create <name>");
                }
            }
            else {
                player.sendMessage("§cPlease enter a World!");
            }
        }
        else {
            sender.sendMessage("§cOnly a Player can use this Command!");
        }
    }

    private void command_unload(CommandSender sender, String[] args) {
        if(args.length >= 2) {

            World world = WorldManagement.isWorldLoaded(args[1]);

            if(world != null) {
                sender.sendMessage("§eUnloading " + world.getName() + "...");
                Bukkit.unloadWorld(world, true);
                sender.sendMessage("§a" + world.getName() + " was unloaded!");
            }
            else {
                sender.sendMessage("§cWorld is not loaded!");
            }

        }
        else {
            sender.sendMessage("§cWrong command usage!");
        }
    }
}