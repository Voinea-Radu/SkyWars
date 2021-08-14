package dev.lightdream.skywars.commands;

import dev.lightdream.skywars.Main;
import dev.lightdream.skywars.databases.User;
import dev.lightdream.skywars.files.dto.Cage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetCage extends Command{
    public SetCage(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("setcage"), "Set you cage", Main.PROJECT_ID+".setcage", true, false, "setcage [name]");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        if(args.size()==0){
            StringBuilder output = new StringBuilder();

            for (String cage : getCages((Player) sender)) {
                output.append(plugin.getMessages().cageEntry.replace("%cage%", cage));
            }

            plugin.getMessageManager().sendMessage(sender, output.toString());
        }
        if(args.size()!=1){
            sendUsage(sender);
            return;
        }
        Cage cage = getCage(args.get(0));
        if(cage == null){
            plugin.getMessageManager().sendMessage(sender, plugin.getMessages().invalidCage);
            return;
        }
        if(!((Player) sender).hasPermission(cage.permission)){
            plugin.getMessageManager().sendMessage(sender, plugin.getMessages().noPermission);
            return;
        }

        plugin.getMessageManager().sendMessage(sender, plugin.getMessages().setCage);
        User user = plugin.getDatabaseManager().getUser(((Player) sender).getUniqueId());
        user.cage = cage.name;
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return new ArrayList<>();
    }

    public Cage getCage(String name){
        for (Cage cage : plugin.getSettings().cages) {
            if(cage.name.equals(name)){
                return cage;
            }
        }
        return null;
    }

    public List<String> getCages(Player player){
        List<String> output = new ArrayList<>();

        for (Cage cage : plugin.getSettings().cages) {
            if(player.hasPermission(cage.permission)){
                output.add(cage.name);
            }
        }

        return output;
    }
}
