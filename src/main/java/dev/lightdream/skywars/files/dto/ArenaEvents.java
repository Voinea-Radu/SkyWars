package dev.lightdream.skywars.files.dto;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class ArenaEvents {

    public List<ArenaEvent> events = new ArrayList<>();

    public ArenaEvents(ArenaEvent... events) {
        Collections.addAll(this.events, events);
    }

    public static class ArenaEvent {
        public String event;
        public List<String> args = new ArrayList<>();

        public ArenaEvent(String event, String... args) {
            this.event = event;
            this.args.addAll(Arrays.asList(args));
        }
    }
}
