package dev.lightdream.skywars.arena.executor;

import dev.lightdream.skywars.arena.Arena;

import java.util.List;

public class ChestRefill extends ArenaEventExecutor{
    @Override
    public void execute(Arena arena, List<String> args) {
        arena.refillChests();
    }
}
