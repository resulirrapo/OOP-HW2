package app.user;

import lombok.Data;

@Data
public class Event {
    private String name;
    private String date;
    private String description;

    public Event(final String name, final String date, final String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
