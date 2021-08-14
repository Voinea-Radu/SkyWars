package dev.lightdream.skywars.files.config;

import dev.lightdream.skywars.files.dto.Item;
import dev.lightdream.skywars.utils.XMaterial;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class GUIs {

    public Item fillItem = new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, "", new ArrayList<>());

    public Item backItem = new Item(XMaterial.ARROW, 1, 45, "Back", new ArrayList<>());
    public Item nextItem = new Item(XMaterial.ARROW, 1, 53, "Next", new ArrayList<>());

    //Leave empty for full inventory fill
    public List<Integer> fillItemPositions = Arrays.asList(46, 47, 48, 49, 50, 51, 52);

    public String arenaSelectGUITitle = "Arenas";
    public Item notAccessibleArenaItem = new Item(XMaterial.RED_WOOL, 1, "%arena%", Arrays.asList(
            "Players: %players%/%max_players%",
            "Status: %status%"
    ));
    public Item accessibleArenaItem = new Item(XMaterial.GREEN_WOOL, 1, "%arena%", Arrays.asList(
            "Players: %players%/%max_players%",
            "Status: %status%"
    ));
    public String notStartedStatus = "Not Started";
    public String inGameStatus = "In Game";

    @Override
    public String toString() {
        return "GUIConfig{" +
                "fillItem=" + fillItem +
                ", backItem=" + backItem +
                ", nextItem=" + nextItem +
                ", fillItemPositions=" + fillItemPositions +
                '}';
    }
}
