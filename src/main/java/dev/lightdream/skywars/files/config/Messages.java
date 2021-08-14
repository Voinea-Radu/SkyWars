package dev.lightdream.skywars.files.config;

import dev.lightdream.skywars.Main;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@NoArgsConstructor
public class Messages {

    public String prefix = "[" + Main.PROJECT_NAME + "] ";

    public String mustBeAPlayer = "You must be a player to use this command.";
    public String mustBeConsole = "You must be console to use this command.";
    public String noPermission = "You do not have the permission to use this.";
    public String unknownCommand = "This is not a valid command.";
    public String cannotJoinArena = "You cannot join this arena.";
    public String notInThisArena = "You are not in this arena.";
    public String alreadyInThisArena = "You are already in this arena.";
    public String joinedArena = "You have successfully joined this arena.";
    public String leftArena = "You have successfully left this arena.";
    public String alreadyStarted = "This arena has already started.";

    public String playerJoined = "%player% has joined the arena (%current%/%max%)";
    public String playerLeft = "%player% has left the arena (%current%/%max%)";
    public String endGame = "The game has ended. %player% has won.";
    public String playerDeath = "%player% has died.";

    //Leave empty for auto-generated
    public List<String> helpCommand = new ArrayList<>();

    public String cageEntry = "- %cage%";
    public String invalidCage = "This is not a valid cage";
    public String setCage = "You have set your cage.";


}
