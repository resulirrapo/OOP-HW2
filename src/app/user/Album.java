package app.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Album {
    private String name;
    private int releaseYear;
    private String description;
    private List<String> songs;

    public Album(final String name, final int releaseYear,
                 final String description, final List<String> songs) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = new ArrayList<>();
    }
}
