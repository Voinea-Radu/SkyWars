package dev.lightdream.skywars.arena.executor;

import dev.lightdream.skywars.arena.Arena;

import java.util.List;

public class Announce extends ArenaEventExecutor {
    @Override
    public void execute(Arena arena, List<String> args) {
        args.forEach(line -> {
            arena.getPlugin().getMessageManager().sendMessage(arena.getPlayers(), line);
        });
    }
}
