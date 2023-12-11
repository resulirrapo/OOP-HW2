package app;

import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        if (user != null) {
            ArrayList<String> results = user.search(filters, type);
            String message;
            if (!results.isEmpty() && results.get(0).contains("is offline")) {
                message = results.get(0);
                results.remove(0);
            } else {
                message = "Search returned " + results.size() + " results";
            }

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);
            objectNode.putPOJO("results", results);

            return objectNode;
        }
        return null;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.select(commandInput.getItemNumber());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.load();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.playPause();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.repeat();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();

        if (user != null) {
            String message = user.shuffle(seed);
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.forward();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.backward();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.like();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.next();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.prev();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.createPlaylist(commandInput.getPlaylistName(),
                    commandInput.getTimestamp());
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            ArrayList<PlaylistOutput> playlists = user.showPlaylists();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("result", objectMapper.valueToTree(playlists));

            return objectNode;
        }
        return null;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String message = user.follow();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        return null;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            PlayerStats stats = user.getPlayerStats();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("stats", objectMapper.valueToTree(stats));

            return objectNode;
        }
        return null;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            ArrayList<String> songs = user.showPreferredSongs();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("stats", objectMapper.valueToTree(songs));

            return objectNode;
        }
        return null;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user != null) {
            String preferredGenre = user.getPreferredGenre();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("stats", objectMapper.valueToTree(preferredGenre));

            return objectNode;
        }
        return null;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
/**
 * switchConnectionStatus object node.
 */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = user.switchConnectionStatus();
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Gets online users.
     * @param commandInput
     * @return
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<User> users = Admin.getOnlineUsers();

        List<String> usernames = users.stream().map(User::getUsername).toList();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(usernames));

        return objectNode;
    }

    /**
     * Adds an user
     * @param commandInput
     * @return
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String message = Admin.addUser(commandInput.getUsername(),
                commandInput.getType(), commandInput.getAge(),
                commandInput.getCity());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

    /**
     * Adds an album
     * @param commandInput
     * @return
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String albumName = commandInput.getName();
        List<String> songNames = commandInput.getSongs().stream()
                .map(SongInput::getName)
                .collect(Collectors.toList());

        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!(user instanceof Artist)) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            Artist artist = (Artist) user;
            message = Admin.addAlbum(artist, albumName, songNames);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * ShowAlbum object node.
     * @param commandInput
     * @return
     */
    public static ObjectNode showAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

            Artist artist = (Artist) user;
            List<Map<String, Object>> albums = Admin.showAlbums(artist);

            ArrayNode albumsArray = objectMapper.createArrayNode();
            for (Map<String, Object> album : albums) {
                ObjectNode albumNode = objectMapper.createObjectNode();
                albumNode.put("name", (String) album.get("name"));
                albumNode.putPOJO("songs", album.get("songs"));
                albumsArray.add(albumNode);
            }
            objectNode.set("result", albumsArray);

        return objectNode;
    }

    /**
     * Prints the current page
     * @param commandInput
     * @return
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.formatPageContent(user.getPage());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
//    public static String changePage(User user, String nextPage) {
//        if (nextPage.equals("HomePage")) {
//            user.changePage(new HomePage());
//            return user.getUsername() + " accessed HomePage successfully.";
//        }
//        // Handle other page types similarly
//
//        return user.getUsername() + " is trying to access a non-existent page.";
//    }

    /**
     * Adds an event
     * @param commandInput
     * @return
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String eventName = commandInput.getName();
        String eventDate = commandInput.getDate();
        String eventDescription = commandInput.getDescription();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            objectNode.put("message",
                    "The username " + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }

        // The addEvent method is called, and it handles all the logic
        String message = user.addEvent(eventName, eventDescription, eventDate);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Adds a merch
     * @param commandInput
     * @return
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String merchName = commandInput.getName();
        String merchDescription = commandInput.getDescription();
        Integer merchPrice = commandInput.getPrice();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            objectNode.put("message",
                    "The username " + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }

        // The addEvent method is called, and it handles all the logic
        String message = user.addMerch(merchName, merchDescription, merchPrice);
        objectNode.put("message", message);
        return objectNode;
    }
}
