package dev.lightdream.skywars.arena;

import dev.lightdream.skywars.arena.executor.*;

public enum ArenaEvent {

    ANNOUNCE(new Announce()),
    START_GAME(new StartGame()),
    CHEST_REFILL(new ChestRefill()),
    SET_WORLD_BORDER(new SetWorldBorder());

    public ArenaEventExecutor executor;

    ArenaEvent(ArenaEventExecutor executor) {
        this.executor = executor;
    }
}
