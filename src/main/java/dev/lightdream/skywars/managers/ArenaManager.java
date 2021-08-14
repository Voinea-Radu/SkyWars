package dev.lightdream.skywars.managers;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.arena.Arena;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArenaManager {

    private final Main plugin;
    @Getter
    private final List<Arena> arenas = new ArrayList<>();

    public ArenaManager(Main plugin) {
        this.plugin = plugin;

        plugin.getArenas().arenaMaps.forEach(arenaMap -> {
            Arena arena = new Arena(plugin, arenaMap);
            arena.reset();
            arenas.add(arena);
        });
    }

    public void join(Player player, String arenaName) {
        Arena arena = getArena(arenaName);
        if (arena == null) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().cannotJoinArena);
            return;
        }
        if (arena.canJoin()) {
            leave(player, false);
            arena.addPlayer(player);
        }
    }

    public void join(Player player) {
        Arena arena = null;

        for (Arena tmpArena : arenas) {
            if (tmpArena.canJoin()) {
                arena = tmpArena;
            }
        }

        if (arena == null) {
            plugin.getMessageManager().sendMessage(player, plugin.getMessages().cannotJoinArena);
            return;
        }
        leave(player, false);
        arena.addPlayer(player);
    }

    public void leave(Player player, boolean silent) {
        arenas.forEach(arena -> arena.removePlayer(player, silent));
    }

    public @Nullable Arena getArena(String name) {
        Optional<Arena> optionalArena = arenas.stream().filter(arena -> arena.arenaMap.name.equals(name)).findFirst();
        return optionalArena.orElse(null);
    }

    public boolean canBreak(Player player) {

        for (Arena arena : arenas) {
            if (!arena.started || arena.timer <= 0) {
                if (arena.getPlayers().contains(player)) {
                    return false;
                }
            }
        }

        return true;
    }


}
