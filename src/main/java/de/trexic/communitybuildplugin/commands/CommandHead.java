package de.trexic.communitybuildplugin.commands;

import de.trexic.inventoryguiapi.Api.inventoryContainer.InventoryContainer;
import de.trexic.inventoryguiapi.Api.inventoryDesigner.InventoryDesigner;
import de.trexic.inventoryguiapi.Api.inventoryGui.InventoryGui;
import de.trexic.inventoryguiapi.Api.inventoryGui.eventCall.ItemInteract;
import de.trexic.inventoryguiapi.Api.inventoryGui.items.GuiHeadItem;
import de.trexic.inventoryguiapi.Api.inventoryGui.items.GuiItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CommandHead implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;


            try {
                if(args.length == 0) {
                    player.openInventory(openHeadInventory(null));
                }
                else {
                    player.openInventory(openHeadInventory(args[0]));
                }
            }
            catch (Exception ex) {
                player.sendMessage("§cDer Kopf konnte nicht erstellt werden.");
                ex.printStackTrace();
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

    private Inventory openHeadInventory(String userName_base64Str) throws Exception {
        String pattern =
                "+ + + - - - + + +" +
                "+ * + - ! - + ~ +" +
                "+ + + - - - + + +";

        InventoryDesigner inventoryDesigner = new InventoryDesigner(pattern, "§5§lHeads");

        inventoryDesigner.assignNoNameGuiItem('-', Material.WHITE_STAINED_GLASS_PANE);
        inventoryDesigner.assignNoNameGuiItem('+', Material.BLACK_STAINED_GLASS_PANE);

        GuiHeadItem middleHeadGuiItem = new GuiHeadItem(0);;

        if(userName_base64Str == null) {
            middleHeadGuiItem = createSearchHead();
            inventoryDesigner.assignNoNameGuiItem('*', Material.BLACK_STAINED_GLASS_PANE);
        }
        else {
            inventoryDesigner.assignGuiItem('*', createSearchHead());

            if(userName_base64Str.length() < 48)
                middleHeadGuiItem.setPlayerHead(userName_base64Str);
            else
                middleHeadGuiItem.setHead_base64(userName_base64Str);


            ItemStack itemStack = middleHeadGuiItem.getItemStack();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§5§lHead");
            itemStack.setItemMeta(itemMeta);
            middleHeadGuiItem.setItemStack(itemStack);

            middleHeadGuiItem.setCollectable(true);
        }

        inventoryDesigner.assignGuiItem('!', middleHeadGuiItem);

        inventoryDesigner.assignGuiItem('~', createFavoriteHeadsGuiHeadItem());





        return inventoryDesigner.getInventoryGui().create();
    }

    private GuiHeadItem createSearchHead() {
        GuiHeadItem guiHeadItem = new GuiHeadItem(1);
        guiHeadItem.setHead_base64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZmOGEyMTlmMDgwMzk0MGYxZDI3MzQ5ZmIwNTBjMzJkYzdjMDUwZGIzM2NhMWUwYjM2YzIyZjIxYjA3YmU4NiJ9fX0=");
        ItemStack headItem = guiHeadItem.getItemStack();
        ItemMeta headItemMeta = headItem.getItemMeta();
        headItemMeta.setDisplayName("§b§lSuche online");
        headItemMeta.setLore(
                Arrays.asList(
                        "§f_____________________",
                        "§7Leitet dich weiter nach: minecraft-heads.com",
                        "",
                        "§f§lInformation:",
                        "§e1. Wähle einen Kopf",
                        "§e2. Kopiere unter Other, Value",
                        "§e3. Nutze /head <Der kopierte Value>"));
        headItem.setItemMeta(headItemMeta);
        guiHeadItem.setItemStack(headItem);

        class middleHeadGuiItemEvent extends ItemInteract {
            @Override
            public void onClick(int slot, ItemStack itemStack, Player playerClicked, InventoryClickEvent inventoryClickEvent, InventoryGui inventoryGui, GuiItem guiItem, InventoryContainer inventoryContainer) {
                TextComponent textComponent = new TextComponent("§a§l[Hier Klicken]");
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecraft-heads.com/custom-heads"));
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bGehe zu Minecraft-Heads")));

                playerClicked.spigot().sendMessage(textComponent);
                playerClicked.closeInventory();
            }
        }

        guiHeadItem.setClickable(true);
        guiHeadItem.setEventCall(new middleHeadGuiItemEvent());

        return guiHeadItem;
    }

    private GuiHeadItem createFavoriteHeadsGuiHeadItem() {
        GuiHeadItem favoriteHeads = new GuiHeadItem(1);
        favoriteHeads.setHead_base64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc4MjZjMWFkYmVkNTM3YzcyMTM0MjUwZmEzMGE0YTI4M2ZmYTM5NTM1ZjE4ZGY3NWI4YzdkN2FmMjNmYjAzZSJ9fX0=");
        ItemStack favoriteHeadsItemStack = favoriteHeads.getItemStack();
        ItemMeta favoriteHeadsMeta = favoriteHeadsItemStack.getItemMeta();
        favoriteHeadsMeta.setDisplayName("§6§lFavoriten");
        favoriteHeadsItemStack.setItemMeta(favoriteHeadsMeta);
        favoriteHeads.setItemStack(favoriteHeadsItemStack);

        favoriteHeads.setClickable(true);

        class favoriteHeadsEvent extends ItemInteract {
            @Override
            public void onClick(int slot, ItemStack itemStack, Player playerClicked, InventoryClickEvent inventoryClickEvent, InventoryGui inventoryGui, GuiItem guiItem, InventoryContainer inventoryContainer) {
                inventoryGui.changePage(2);
            }
        }

        favoriteHeads.setEventCall(new favoriteHeadsEvent());

        return favoriteHeads;
    }
}