package dev.lightdream.skywars.commands;

import dev.lightdream.skywars.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinCommand extends Command{
    public JoinCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("join"), "Join an arena", Main.PROJECT_ID+".join", true, false, "join [arena]");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        if(args.size()==0){
            plugin.getArenaManager().join((Player) sender);
            return;
        }
        if(args.size()!=1){
            return;
        }
        plugin.getArenaManager().join((Player) sender, args.get(0));
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return new ArrayList<>();
    }
}
