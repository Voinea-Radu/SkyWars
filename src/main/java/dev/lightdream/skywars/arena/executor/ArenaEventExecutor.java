package dev.lightdream.skywars.arena.executor;

import dev.lightdream.skywars.arena.Arena;

import java.util.List;

public abstract class ArenaEventExecutor {
    public abstract void execute(Arena arena, List<String> args);
}
