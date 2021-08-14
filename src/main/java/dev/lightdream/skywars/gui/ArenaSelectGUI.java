package dev.lightdream.skywars.gui;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.arena.Arena;
import dev.lightdream.skywars.files.dto.Item;
import dev.lightdream.skywars.utils.ItemBuilder;
import dev.lightdream.skywars.utils.PlaceholderUtils;
import dev.lightdream.skywars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArenaSelectGUI implements GUI {

    private final Main plugin;

    public ArenaSelectGUI(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (item == null) {
            return;
        }
        if (item.getType().equals(Material.AIR)) {
            return;
        }

        Object guiProtect = Utils.getNBT(item, "gui_protect");
        Object guiUse = Utils.getNBT(item, "gui_use");

        if (guiProtect != null && (Boolean) guiProtect) {
            event.setCancelled(true);
        }

        if (guiUse != null) {
            switch ((String) guiUse) {
                case "arena":
                    String arenaName = (String) Utils.getNBT(item, "arena");
                    Arena arena = plugin.getArenaManager().getArena(arenaName);
                    arena.addPlayer(player);
                    break;
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, Utils.color(plugin.getGUIs().arenaSelectGUITitle));
        plugin.getArenaManager().getArenas().forEach(arena -> {
            Item base;
            if (!arena.canJoin()) {
                base = plugin.getGUIs().notAccessibleArenaItem.clone();
            } else {
                base = plugin.getGUIs().accessibleArenaItem.clone();
            }
            base.lore = PlaceholderUtils.parse(base.lore, null, arena);
            base.displayName = PlaceholderUtils.parse(base.displayName, null, arena);
            ItemStack item = ItemBuilder.makeItem(base);
            item = Utils.setNBT(item, "gui_protect", true);
            item = Utils.setNBT(item, "gui_use", "arena");
            item = Utils.setNBT(item, "arena", arena.arenaMap.name);
            inventory.addItem(item);
        });

        return inventory;
    }
}
