package ru.itmo.web.hw4.model;

import java.util.Map;

public class User {

    private final Map<Color, String> colors = Map.of(
            Color.RED, "red",
            Color.BLUE, "blue",
            Color.GREEN, "green");
    private final long id;
    private final String handle;
    private final String name;
    private final Color color;

    public User(long id, String handle, String name, Color color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public String getColor() {return colors.get(color);}
}
