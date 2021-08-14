package dev.lightdream.skywars.files.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Cage {

    public String name;
    public String permission;

    @Override
    public String toString() {
        return "Cage{" +
                "name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
