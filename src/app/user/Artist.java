package app.user;

import lombok.Data;

import java.util.*;

@Data
/**
 * The type Artist.
 * This class extends User and is used to create an artist.
 */
public class Artist extends User {
    private Map<String, Set<String>> albums;
    public Artist() {
    }

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.albums = new HashMap<>();
    }

    /**
     * Switch connection status of the user
     * @return
     */
    final public String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }

    public final Map<String, Set<String>> getAlbums() {
        return albums;
    }

    /**
     * Check if an album exists
     * @param albumName
     * @return
     */
    public final boolean hasAlbum(final String albumName) {
        return albums.containsKey(albumName);
    }

    /**
     * Add an album to the artist
     * @param albumName
     * @param songList
     */
    public final void addAlbum(final String albumName, final List<String> songList) {
        albums.put(albumName, new HashSet<>(songList));
    }

    /**
     * Add a song to an album
     */
    public final void showAlbums() {
        for (String albumName : albums.keySet()) {
            System.out.println(albumName);
        }
    }
}
