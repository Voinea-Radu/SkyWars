package dev.lightdream.skywars.files.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ChestConfig {

    public List<ArenaLocation> locations;
    public String loot;
    public String guaranteedLoot;

}
