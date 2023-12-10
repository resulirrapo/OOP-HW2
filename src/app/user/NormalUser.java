package app.user;

import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.searchBar.Filters;
import app.utils.Enums;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
/**
 * The type Normal user.
 * This class extends User and is used to create a normal user.
 */
public class NormalUser extends User {

    private static List<User> users = new ArrayList<>();

    public NormalUser() {
        setOnline(true);
    }

    public NormalUser(final String username, final int age, final String city) {
        super(username, age, city);
        setOnline(true);
    }

    /**
     * Gets users after switching status.
     * @return
     */
    final public String switchConnectionStatus() {
        setOnline(!isOnline());
        return this.getUsername() + " has changed status successfully.";
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        if (!this.isOnline()) {
            ArrayList<String> result = new ArrayList<>();
            result.add(getUsername() + " is offline.");
            return result;
        }

        this.getSearchBar().clearSelection();
        this.getPlayer().stop();

        setLastSearched(true);
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = getSearchBar().search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }

        if (itemNumber == -1) {
            return "Please conduct a search before making a selection.";
        }

        setLastSearched(false);

        LibraryEntry selected = getSearchBar().select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        return "Successfully selected %s.".formatted(selected.getName());
    }
    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }

        if (getSearchBar().getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!getSearchBar().getLastSearchType().equals("song")
                && ((AudioCollection) getSearchBar().getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        getPlayer().setSource(getSearchBar().getLastSelected(), getSearchBar().getLastSearchType());
        getSearchBar().clearSelection();

        getPlayer().pause();

        return "Playback loaded successfully.";
    }
    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }
        getPlayer().pause();
        if (getPlayer().getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }
    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = getPlayer().repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }
    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }

        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!getPlayer().getType().equals("playlist")) {
            return "The loaded source is not a playlist.";
        }

       getPlayer().shuffle(seed);

        if (getPlayer().getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }
    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }

        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!getPlayer().getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        getPlayer().skipNext();

        return "Skipped forward successfully.";
    }
    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!getPlayer().getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        getPlayer().skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!getPlayer().getType().equals("song") && !getPlayer().getType().equals("playlist")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) getPlayer().getCurrentAudioFile();

        if (getLikedSongs().contains(song)) {
            getLikedSongs().remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        getLikedSongs().add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        getPlayer().next();

        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(getPlayer().getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        getPlayer().prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(getPlayer().getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlaylists().stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        getPlaylists().add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }
    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (getPlayer().getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (getPlayer().getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > getPlaylists().size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = getPlaylists().get(id - 1);

        if (playlist.containsSong((Song) getPlayer().getCurrentAudioFile())) {
            playlist.removeSong((Song) getPlayer().getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) getPlayer().getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        if (playlistId > getPlaylists().size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = getPlaylists().get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        if (!this.isOnline()) {
            return new ArrayList<>();
        }
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : getPlaylists()) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        if (!this.isOnline()) {
            return getUsername() + " is offline";
        }
        LibraryEntry selection = getSearchBar().getLastSelected();
        String type = getSearchBar().getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(getUsername())) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (getFollowedPlaylists().contains(playlist)) {
            getFollowedPlaylists().remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        getFollowedPlaylists().add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

}
