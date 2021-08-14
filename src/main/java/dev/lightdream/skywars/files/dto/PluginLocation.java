package dev.lightdream.skywars.files.dto;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@NoArgsConstructor
@AllArgsConstructor
public class PluginLocation {

    public String world;
    public float x;
    public float y;
    public float z;

    public PluginLocation(String world, ArenaLocation location) {
        this.world = world;
        this.x = location.x;
        this.y = location.y;
        this.z = location.z;
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(x, y, z);
    }
}
