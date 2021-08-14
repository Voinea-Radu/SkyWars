package dev.lightdream.skywars.databases;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.NoArgsConstructor;

import java.util.UUID;

@SuppressWarnings("unused")
@NoArgsConstructor
@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "uuid", unique = true)
    public UUID uuid;
    @DatabaseField(columnName = "name", unique = true)
    public String name;
    @DatabaseField(columnName = "cage")
    public String cage;
    @DatabaseField(columnName = "kills")
    public int kills;
    @DatabaseField(columnName = "deaths")
    public int deaths;
    @DatabaseField(columnName = "wins")
    public int wins;
    @DatabaseField(columnName = "losses")
    public int losses;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.cage = "default";
        this.wins = 0;
        this.deaths = 0;
        this.wins = 0;
        this.losses = 0;
    }

}
