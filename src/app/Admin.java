package app;

import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.*;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Data;

import java.util.*;

@Data
/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }
/**
 * Gets online users.
 */
    public static List<User> getOnlineUsers() {
        List<User> onlineUsers;
        onlineUsers = users.stream().filter(User::isOnline).toList();
        return onlineUsers;
    }

    /**
     * adds a user
     * @param username
     * @param type
     * @param age
     * @param city
     * @return
     */
    public static String addUser(final String username,
                                 final String type, final int age, final String city) {
        // Check for duplicate username
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }

        // Add user based on type
        switch (type.toLowerCase()) {
            case "artist":
                // Assuming there is an ArtistUser class
                users.add(new Artist(username, age, city));
                break;
            case "host":
                // Assuming there is a HostUser class
                users.add(new Host(username, age, city));
                break;
            default:
                users.add(new NormalUser(username, age, city));
                break;
        }

        return "The username " + username + " has been added successfully.";
    }

    /**
     * add an album
     * @param user
     * @param albumName
     * @param songList
     * @return
     */
    public static String addAlbum(final User user, final String albumName,
                                  final List<String> songList) {
        if (user.getClass() != Artist.class) {
            return user.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist) user;

        // Check for existing album
        if (artist.hasAlbum(albumName)) {
            return artist.getUsername() + " has another album with the same name.";
        }

        // Check for duplicate songs in the album
        HashSet<String> uniqueSongs = new HashSet<>(songList);
        if (uniqueSongs.size() != songList.size()) {
            return artist.getUsername() + " has the same song at least twice in this album.";
        }

        // Add the album
        artist.addAlbum(albumName, songList);

        return artist.getUsername() + " has added new album successfully.";
    }

    /**
     * show an album
     * @param artist
     * @return
     */
    public static List<Map<String, Object>> showAlbums(final Artist artist) {
        List<Map<String, Object>> albumDetails = new ArrayList<>();

        // Iterate over the map entries
        for (Map.Entry<String, Set<String>> entry : artist.getAlbums().entrySet()) {
            Map<String, Object> details = new HashMap<>();
            details.put("name", entry.getKey()); // Album name
            details.put("songs", entry.getValue()); // Song names
            albumDetails.add(details);
        }

        return albumDetails;
    }


    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new NormalUser(userInput.getUsername(),
                    userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
}
