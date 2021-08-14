package dev.lightdream.skywars.managers;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.gui.ArenaSelectGUI;
import dev.lightdream.skywars.utils.ItemBuilder;
import dev.lightdream.skywars.utils.NbtUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener {

    public final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getDatabaseManager().getUser(player.getUniqueId());
        player.getInventory().clear();
        player.getInventory().addItem(NbtUtils.setNBT(ItemBuilder.makeItem(plugin.getSettings().joinItem), "use", "open_arena_select_gui"));
        player.teleport(plugin.getSettings().lobbyLocation.toLocation());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        plugin.getArenaManager().leave(event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getDatabaseManager().getUser(event.getEntity().getUniqueId()).deaths++;
        plugin.getArenaManager().leave(event.getEntity(), true);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        plugin.getDatabaseManager().getUser(killer.getUniqueId()).kills++;
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getArenaManager().canBreak(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().isAir()) {
            return;
        }
        String use = (String) NbtUtils.getNBT(player.getInventory().getItemInMainHand(), "use");
        if (use == null) {
            return;
        }

        if (use.equals("open_arena_select_gui")) {
            player.openInventory(new ArenaSelectGUI(plugin).getInventory());
        }


    }

}
