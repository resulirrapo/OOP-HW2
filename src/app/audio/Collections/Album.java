package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class Album extends AudioCollection {
    private final String description;
    private final ArrayList<Song> songs;

    public Album(final String name, final String owner, final String description) {
        super(name, owner);
        this.description = description;
        this.songs = new ArrayList<>();
    }

    /**
     * matches the description of the album
     * @param description1
     * @return
     */
    public final boolean matchesDescription(final String description1) {
        return getDescription().toLowerCase().startsWith(description.toLowerCase());
    }

    @Override
    public final int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public final AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }
}
