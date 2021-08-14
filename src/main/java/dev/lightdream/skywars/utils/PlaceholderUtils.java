package dev.lightdream.skywars.utils;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.arena.Arena;
import dev.lightdream.skywars.databases.User;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderUtils {

    public static Main plugin = null;

    public static void init(Main main) {
        plugin = main;
    }

    public static String parse(String raw, User user, Arena arena) {
        String parsed = raw;

        if (user != null) {
            parsed = parsed.replace("%player%", user.name);
            parsed = parsed.replace("%kills%", String.valueOf(user.kills));
            parsed = parsed.replace("%deaths%", String.valueOf(user.deaths));
            parsed = parsed.replace("%wins%", String.valueOf(user.wins));
            parsed = parsed.replace("%losses%", String.valueOf(user.losses));
        }

        if (arena != null) {
            parsed = parsed.replace("%arena%", arena.arenaMap.name);
            parsed = parsed.replace("%players%", String.valueOf(arena.getPlayers().size()));
            parsed = parsed.replace("%max_players%", String.valueOf(arena.arenaMap.spawnLocations.size()));

            if (arena.started) {
                parsed = parsed.replace("%status%", plugin.getGUIs().inGameStatus);
            } else {
                parsed = parsed.replace("%status%", plugin.getGUIs().notStartedStatus);
            }

        }

        return parsed;
    }

    public static List<String> parse(List<String> raw, User user, Arena arena) {
        List<String> parsed = new ArrayList<>();

        for (String line : raw) {
            parsed.add(parse(line, user, arena));
        }

        return parsed;
    }

}
