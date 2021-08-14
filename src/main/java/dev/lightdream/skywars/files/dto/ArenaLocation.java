package dev.lightdream.skywars.files.dto;

import org.bukkit.Bukkit;
import org.bukkit.Location;


public class ArenaLocation {

    public int x;
    public int y;
    public  int z;

    public Location toLocation(String world){
        return new Location(Bukkit.getWorld(world), x,y,z);
    }

}
