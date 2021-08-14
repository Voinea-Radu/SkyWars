package dev.lightdream.skywars;

import dev.lightdream.skywars.commands.*;
import dev.lightdream.skywars.files.config.*;
import dev.lightdream.skywars.files.dto.ArenaMap;
import dev.lightdream.skywars.files.dto.ChestConfig;
import dev.lightdream.skywars.files.dto.Loot;
import dev.lightdream.skywars.files.dto.PluginLocation;
import dev.lightdream.skywars.managers.*;
import dev.lightdream.skywars.utils.PlaceholderUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public final class Main extends JavaPlugin {
    //Settings
    public final static String PROJECT_NAME = "SkyWars";
    public final static String PROJECT_ID = "sw";
    private final List<Command> commands = new ArrayList<>();

    //Managers
    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private EventManager eventManager;
    private InventoryManager inventoryManager;
    private MessageManager messageManager;
    private SchedulerManager schedulerManager;
    private ArenaManager arenaManager;
    private WorldEditManager worldEditManager;

    //Utils
    private FileManager fileManager;

    //DTO
    private Config settings;
    private Messages messages;
    private SQL sql;
    private Arenas arenas;
    private Loot loot;
    private GUIs GUIs;

    @Override
    public void onEnable() {

        //Utils
        PlaceholderUtils.init(this);

        //Config
        loadConfigs();
        worldEditManager = new WorldEditManager(this);
        if (arenas.arenaMaps.size() == 0) {
            arenas.arenaMaps.add(new ArenaMap(
                    "Test",
                    new PluginLocation("world", 10, 95, -10),
                    new PluginLocation("world", -10, 110, 10),
                    Arrays.asList(
                            new PluginLocation("world", -3.5f, 105, -3.5f),
                            new PluginLocation("world", 3.5f, 105, -3.5f),
                            new PluginLocation("world", -3.5f, 105, 3.5f),
                            new PluginLocation("world", 3.5f, 105, 3.5f)
                    ),
                    Arrays.asList(
                            new ChestConfig(new PluginLocation("world", 3, 99, 3), "islandLoot"),
                            new ChestConfig(new PluginLocation("world", 3, 99, -3), "islandLoot"),
                            new ChestConfig(new PluginLocation("world", -3, 99, 3), "islandLoot"),
                            new ChestConfig(new PluginLocation("world", -3, 99, -3), "islandLoot"),
                            new ChestConfig(new PluginLocation("world", 0, 99, 0), "midLoot")
                    ),
                    2
            ));
            arenas.arenaMaps.get(0).saveMap(this);
        }

        //Commands
        commands.add(new ReloadCommand(this));
        commands.add(new JoinCommand(this));
        commands.add(new SetCage(this));
        commands.add(new LeaveCommand(this));

        //Managers
        commandManager = new CommandManager(this, PROJECT_NAME.toLowerCase());
        try {
            databaseManager = new DatabaseManager(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        eventManager = new EventManager(this);
        inventoryManager = new InventoryManager(this);
        messageManager = new MessageManager(this);
        schedulerManager = new SchedulerManager(this);
        arenaManager = new ArenaManager(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI(this).register();
        } else {
            System.out.println("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        //Save files
        fileManager.save(arenas);
        fileManager.save(loot);

        //Save to db
        databaseManager.saveUsers();
    }

    public void loadConfigs() {
        fileManager = new FileManager(this, FileManager.PersistType.YAML);
        settings = fileManager.load(Config.class);
        messages = fileManager.load(Messages.class);
        sql = fileManager.load(SQL.class);
        arenas = fileManager.load(Arenas.class);
        loot = fileManager.load(Loot.class);
        GUIs = fileManager.load(GUIs.class);
    }
}
