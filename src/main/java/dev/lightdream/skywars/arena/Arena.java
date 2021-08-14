package dev.lightdream.skywars.arena;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.files.dto.*;
import dev.lightdream.skywars.utils.ItemBuilder;
import dev.lightdream.skywars.utils.ItemStackUtils;
import dev.lightdream.skywars.utils.NbtUtils;
import dev.lightdream.skywars.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena {

    public final ArenaMap arenaMap;
    @Getter
    private final Main plugin;
    public List<ArenaLocation> availableLocations = new ArrayList<>();
    public HashMap<Player, ArenaLocation> players = new HashMap<>();
    public int timer = -30; //seconds
    public BukkitTask timerTask;
    public boolean started;
    public int borderSize;

    public Arena(Main plugin, ArenaMap arenaMap) {
        this.plugin = plugin;
        this.arenaMap = arenaMap;
        reset();
    }

    public void addPlayer(Player player) {
        if (started) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().alreadyStarted);
            return;
        }
        if (players.containsKey(player)) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().alreadyInThisArena);
            return;
        }
        players.put(player, availableLocations.get(0));
        List<ArenaLocation> newAvailableLocations = new ArrayList<>();
        for (int i = 1; i < availableLocations.size(); i++) {
            newAvailableLocations.add(availableLocations.get(i));
        }
        availableLocations = newAvailableLocations;
        plugin.getMessageManager().sendMessage(player, plugin.getMessages().joinedArena);
        plugin.getWorldEditManager().paste(new PluginLocation(arenaMap.world, players.get(player)), plugin.getWorldEditManager().load("cages", plugin.getDatabaseManager().getUser(player.getUniqueId()).cage));
        player.teleport(new PluginLocation(arenaMap.world, players.get(player)).toLocation());
        player.getInventory().clear();
        if (players.size() >= arenaMap.minPlayers) {
            start();
        }
        plugin.getMessageManager().sendMessage(getPlayers(), parse(plugin.getMessages().playerJoined, player.getName()));
    }

    public void removePlayer(Player player, boolean silent) {
        if (!players.containsKey(player) && !silent) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().notInThisArena);
            return;
        }
        players.remove(player);
        availableLocations.add(players.get(player));
        if (!silent) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().leftArena);
        }
        player.teleport(plugin.getSettings().lobbySpawn.toLocation());

        player.getInventory().clear();
        player.getInventory().addItem(NbtUtils.setNBT(ItemBuilder.makeItem(plugin.getSettings().joinItem), "use", "open_arena_select_gui"));

        if (players.size() < arenaMap.minPlayers && !started) {
            stop();
            return;
        }
        if (started && getPlayers().size() == 0) {
            reset();
            return;
        }
        if (!started) {
            plugin.getMessageManager().sendMessage(getPlayers(), parse(plugin.getMessages().playerLeft, player.getName()));
        } else {
            if (getPlayers().size() == 1) {
                plugin.getMessageManager().sendMessage(getPlayers(), parse(plugin.getMessages().endGame, getPlayers().get(0).getName()));
                removePlayer(getPlayers().get(0), true);
                plugin.getDatabaseManager().getUser(player.getUniqueId()).wins++;
                stop();
                return;
            }
            plugin.getMessageManager().sendMessage(getPlayers(), parse(plugin.getMessages().playerDeath, player.getName()));
        }
        plugin.getDatabaseManager().getUser(player.getUniqueId()).losses++;
    }

    public void reset() {
        arenaMap.reset(plugin);
        availableLocations = arenaMap.spawnLocations;
        players = new HashMap<>();
        if (timerTask != null && !timerTask.isCancelled()) {
            timerTask.cancel();
        }
        timer = -30;
    }

    public void start() {
        timerTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            ArenaEvents event = plugin.getSettings().events.getOrDefault(timer, null);
            if (event != null) {
                event.events.forEach(eventConfig -> {
                    ArenaEvent.valueOf(eventConfig.event).executor.execute(this, eventConfig.args);
                    getPlayers().forEach(player -> {
                        if (Utils.distance(player.getLocation(), new Location(Bukkit.getWorld(arenaMap.world), 0, player.getLocation().getY(), 0)) > borderSize) {
                            player.damage(plugin.getSettings().borderDamage);
                        }
                    });
                });
            }

            timer++;
        }, 0, 20);
        started = true;
    }

    public void stop() {
        if (timerTask != null && !timerTask.isCancelled()) {
            timerTask.cancel();
        }
        timer = -30;    
        started = false;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(this.players.keySet());
    }

    public String parse(String raw, String player) {
        String parsed = raw;

        parsed = parsed.replace("%player%", player);
        parsed = parsed.replace("%current%", String.valueOf(getPlayers().size()));
        parsed = parsed.replace("%max%", String.valueOf(arenaMap.spawnLocations.size()));

        return parsed;
    }

    public void startGame() {
        getPlayers().forEach(player -> {
            player.setInvisible(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.setInvisible(false);

            }, 5 * 20);
        });
        clearCages();
        refillChests();
    }

    public void clearCages() {
        players.keySet().forEach(player -> {
            plugin.getWorldEditManager().paste(new PluginLocation(arenaMap.world, players.get(player)), plugin.getWorldEditManager().load("cages", "remover"));
        });
    }

    public void refillChests() {
        World world = Bukkit.getWorld(arenaMap.world);
        arenaMap.chests.forEach(chestConfig -> {

            List<Integer> droppedItems = new ArrayList<>();
            List<Item> toAdd = new ArrayList<>();

            for (int i = 0; i < plugin.getLoot().numberOfGuaranteedItems; i++) {
                Integer rnd = null;
                while (rnd == null || droppedItems.contains(rnd)) {
                    rnd = Utils.generateRandom(0, plugin.getLoot().getByName(chestConfig.loot).size() - 1);
                }

                droppedItems.add(rnd);
                toAdd.add((Item) plugin.getLoot().getByName(chestConfig.guaranteedLoot).get(rnd));
            }


            plugin.getLoot().getByName(chestConfig.loot).forEach(item -> {
                if (Utils.checkExecute(((ChanceItem) item).chance)) {
                    toAdd.add(((ChanceItem) item).item);
                }
            });


            for (int i = 0; i < chestConfig.locations.size(); i++) {
                Block block = world.getBlockAt(new PluginLocation(arenaMap.world, chestConfig.locations.get(i)).toLocation());
                if (!(block.getState() instanceof Chest)) {
                    System.out.println("ERROR: This block is not a chest!");
                    return;
                }
                Chest chest = (Chest) block.getState();
                chest.update();

                chest.getInventory().clear();
                for (int j = toAdd.size() / chestConfig.locations.size() * i; j < toAdd.size() / chestConfig.locations.size() * (i + 1); j++) {
                    int index = -1;

                    while (index == -1 || chest.getInventory().getItem(index) != null) {
                        index = Utils.generateRandom(0, 26);
                    }
                    chest.getInventory().setItem(index, ItemStackUtils.makeItem(toAdd.get(j)));
                }
            }


        });
    }

    public boolean canJoin() {
        if (!started) {
            return false;
        }
        return getPlayers().size() < availableLocations.size();
    }

}
