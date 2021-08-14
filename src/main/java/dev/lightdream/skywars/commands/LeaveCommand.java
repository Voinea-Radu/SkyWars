package dev.lightdream.skywars.commands;

import dev.lightdream.skywars.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class LeaveCommand extends  Command{
    public LeaveCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("leave"), "Leave the current arena", Main.PROJECT_ID+".leave", true, false, "leave");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        plugin.getArenaManager().leave((Player) sender, false);
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return null;
    }
}
