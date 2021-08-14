package dev.lightdream.skywars.files.dto;

import dev.lightdream.skywars.Main;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ArenaMap {

    public String name;
    public String world;
    public ArenaLocation pos1;
    public ArenaLocation pos2;
    public List<ArenaLocation> spawnLocations;
    public List<ChestConfig> chests = new ArrayList<>();
    public int minPlayers;

    public void saveMap(Main plugin) {
        plugin.getWorldEditManager().save("arenas", name, plugin.getWorldEditManager().copy(new PluginLocation(world, pos1), new PluginLocation(world, pos2)));
    }

    public void loadMap(Main plugin) {
        plugin.getWorldEditManager().paste(new PluginLocation(world, pos1), new PluginLocation(world, pos2), plugin.getWorldEditManager().load("arenas", name));
    }


    public void reset(Main plugin) {
        loadMap(plugin);
    }
}
