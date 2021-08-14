package dev.lightdream.skywars.files.config;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.files.dto.ArenaEvents;
import dev.lightdream.skywars.files.dto.Cage;
import dev.lightdream.skywars.files.dto.Item;
import dev.lightdream.skywars.files.dto.PluginLocation;
import dev.lightdream.skywars.utils.XMaterial;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
public class Config {

    //todo implement and remove
    //43a3cd99-2945-4219-85c0-e88156133ebd
    public String key = "";

    public PluginLocation lobbySpawn = new PluginLocation("world", 0, 100, 0);
    public HashMap<Integer, ArenaEvents> events = new HashMap<Integer, ArenaEvents>() {{
        put(-30, new ArenaEvents(new ArenaEvents.ArenaEvent("ANNOUNCE", "The number of player is enough to start."), new ArenaEvents.ArenaEvent("ANNOUNCE", "The game will start in 30s.")));
        put(-20, new ArenaEvents(new ArenaEvents.ArenaEvent("ANNOUNCE", "The game will start in 20s.")));
        put(-10, new ArenaEvents(new ArenaEvents.ArenaEvent("ANNOUNCE", "The game will start in 10s.")));
        put(-5, new ArenaEvents(new ArenaEvents.ArenaEvent("ANNOUNCE", "The game will start in 5s.")));
        put(0, new ArenaEvents(new ArenaEvents.ArenaEvent("START_GAME"), new ArenaEvents.ArenaEvent("ANNOUNCE", "The game game has started.", "Good luck.")));
        put(2 * 60, new ArenaEvents(new ArenaEvents.ArenaEvent("CHEST_REFILL"), new ArenaEvents.ArenaEvent("ANNOUNCE", "Chests have refilled.")));
        put(3 * 60, new ArenaEvents(new ArenaEvents.ArenaEvent("CHEST_REFILL"), new ArenaEvents.ArenaEvent("ANNOUNCE", "Chests have refilled.")));
        put(4 * 60, new ArenaEvents(new ArenaEvents.ArenaEvent("SET_WORLD_BORDER", "50"), new ArenaEvents.ArenaEvent("ANNOUNCE", "Saying hello to every player that is still at 4m.")));
    }};

    public List<Cage> cages = Arrays.asList(
            new Cage("default", Main.PROJECT_ID + ".cage.default"),
            new Cage("vip", Main.PROJECT_ID + ".cage.vip")
    );
    public PluginLocation lobbyLocation = new PluginLocation("world", 0, 100, 0);

    public Item joinItem = new Item(XMaterial.COMPASS, 1, "Join Arena", new ArrayList<>());

    public int borderDamage = 1;
    public int guaranteedItemsPerChest;
}
