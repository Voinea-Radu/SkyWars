package dev.lightdream.skywars.arena.executor;

import dev.lightdream.skywars.arena.Arena;
import org.bukkit.Bukkit;

import java.util.List;

public class SetWorldBorder extends ArenaEventExecutor {


    @Override
    public void execute(Arena arena, List<String> args) {
        arena.borderSize = Integer.parseInt(args.get(0));
    }
}
