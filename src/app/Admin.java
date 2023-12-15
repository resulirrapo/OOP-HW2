package app;

import app.audio.Collections.Album;
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
    private static List<Album> albums = new ArrayList<>();
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
        switch (type.toLowerCase()) {
            case "artist":
                users.add(new Artist(username, age, city));
                break;
            case "host":
                users.add(new Host(username, age, city));
                break;
            default:
                users.add(new NormalUser(username, age, city));
                break;
        }

        return "The username " + username + " has been added successfully.";
    }

    /**
     * gets all users
     * @return
     */
//    public static List<String> getAllUsers() {
//        // add allusers and sort them normausers first then artists then hosts
//        List<String> allUsers = new ArrayList<>();
//        List<NormalUser> normalUsers = new ArrayList<>();
//        List<Artist> artists = new ArrayList<>();
//        List<Host> hosts = new ArrayList<>();
//        for (User user : users) {
//            // add all users comparing if its normal user or artist or host
//            if (user.getUserType().equals("NormalUser")) {
//                normalUsers.add((NormalUser) user);
//            } else if (user.getUserType().equals("Artist")) {
//                artists.add((Artist) user);
//            } else {
//                hosts.add((Host) user);
//            }
//        }
//        normalUsers.sort(Comparator.comparing(User::getUsername));
//        artists.sort(Comparator.comparing(User::getUsername));
//        hosts.sort(Comparator.comparing(User::getUsername));
//
//        allUsers.addAll(normalUsers.stream().map(User::getUsername).toList());
//        allUsers.addAll(artists.stream().map(User::getUsername).toList());
//        allUsers.addAll(hosts.stream().map(User::getUsername).toList());
//
//        return allUsers;
//    }

//        public static String deleteUser(String username) {
//            for (User user : users) {
//                if (user.getUsername().equals(username)) {
//                    users.remove(user);
//                    return "The username " + username + " was successfully deleted.";
//                } else if (user.getUserType() == 1) {
//                    return username + " can't be deleted.";
//                } else if (user.getUserType() == 2) {
//                    return username + " can't be deleted.";
//                }
//            }
//            return "The username " + username + " doesn't exist.";
//        }

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

    public static List<Album> getAlbums() {
        return new ArrayList<>(albums);
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
     * Gets artists
     * @return artists
     */
    public static List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("Artist")) {
                artists.add((Artist) user);
            }
        }
        return artists;
    }

    /**
     * Gets hosts
     * @return hosts
     */
    public static List<Host> getHosts() {
        List<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("Host")) {
                hosts.add((Host) user);
            }
        }
        return hosts;
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

    /**
     * Add song
     * @param song
     */
    public static void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Add album
     * @param album
     */
    public static void addAlbum(final Album album) {
        albums.add(album);
    }
}
