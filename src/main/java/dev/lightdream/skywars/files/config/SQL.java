package dev.lightdream.skywars.files.config;

import dev.lightdream.skywars.Main;
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@NoArgsConstructor
public class SQL {

    public Driver driver = Driver.SQLITE;
    public String host = "localhost";
    public String database = Main.PROJECT_NAME;
    public String username = "";
    public String password = "";
    public int port = 3306;
    public boolean useSSL = false;

    public enum Driver {
        MYSQL,
        MARIADB,
        SQLSERVER,
        POSTGRESQL,
        H2,
        SQLITE
    }

    @Override
    public String toString() {
        return "SQL{" +
                "driver=" + driver +
                ", host='" + host + '\'' +
                ", database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", useSSL=" + useSSL +
                '}';
    }
}
