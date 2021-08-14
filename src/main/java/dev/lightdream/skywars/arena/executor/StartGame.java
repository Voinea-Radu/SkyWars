package dev.lightdream.skywars.arena.executor;

import dev.lightdream.skywars.arena.Arena;

import java.util.List;

public class StartGame extends ArenaEventExecutor{
    @Override
    public void execute(Arena arena, List<String> args) {
        arena.startGame();
    }
}
