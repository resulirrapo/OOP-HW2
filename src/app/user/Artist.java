package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Data
/**
 * The type Artist.
 * This class extends User and is used to create an artist.
 */
public class Artist extends User {
    private List<Event> events = new ArrayList<>();
    private List<Merch> merchList = new ArrayList<>();
    private static final int MAXALBUMS = 5;
    private static final int MAXLIST = 5;
    private static final int MAXEVENTS = 5;
    public static final int MAXYEAR = 2023;
    public static final int MINYEAR = 1900;
    public static final int MAXMONTH = 12;
    public static final int MAXDAY = 31;
    public static final int FEBRUARY = 2;
    public static final int FEBRUARYDAYS = 28;

    public Artist() {
        setPage("ArtistPage");
    }

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.events = new ArrayList<>();
        setPage("ArtistPage");
    }

    /**
     * Change page
     * @param nextPage
     * @return
     */
    @Override
    public final String changePage(final String nextPage) {
        return getUsername() + " is not authorized to change pages.";
    }

    /**
     * Switch connection status of the user
     * @return
     */
    public String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }

    /**
     * Check if an album exists
     * @param albumName
     * @return
     */
    public final String addAlbum(final String albumName,
                               final Integer releaseYear, final String albumDescription,
                               final List<SongInput> songNames) {
        List<Album> albums = Admin.getAlbums().stream()
                .filter(album -> album.getOwner().equalsIgnoreCase(getUsername()))
                .collect(Collectors.toList());

        if (albums.stream().anyMatch(album -> album.getName().equalsIgnoreCase(albumName))) {
            return getUsername() + " has another album with the same name.";
        } else if (songNames.stream().map(SongInput::getName).collect(Collectors.toSet()).size()
                        != songNames.size()) {
            return getUsername() + " has the same song at least twice in this album.";
        }

        Album newAlbum = new Album(albumName, getUsername(), albumDescription);

        for (SongInput song : songNames) {
            Song newSong = new Song(song.getName(), song.getDuration(), albumName,
                    song.getTags(), song.getLyrics(), song.getGenre(), releaseYear, getUsername());
            newAlbum.getSongs().add(newSong);
            Admin.addSong(newSong);
        }
        Admin.addAlbum(newAlbum);

        return getUsername() + " has added new album successfully.";
    }
    /**
     * removes an album
     * @param albumName
     * @return
     */
//    public String removeAlbum(final String albumName) {
//        if (albums.stream().noneMatch(album -> album.getName().equalsIgnoreCase(albumName))) {
//            return getUsername() + " doesn't have an album with the given name.";
//        } else if (albums.stream().anyMatch(album -> album.getName().equalsIgnoreCase(albumName)
//                && album.getNumberOfSongs() > 0)) {
//            return getUsername() + " has songs in this album, cannot remove.";
//        }
//    }

    /**
     * formats the page content
     * @param pageName
     * @return
     */
    public String formatPageContent(final String pageName) {
        if (pageName.equals("Artist")) {
           return formatArtistPage();
        } else {
            return "Error";
        }
    }

    /**
     * formats the artist page
     * @return
     */
    public String formatArtistPage() {
        StringBuilder sb = new StringBuilder();
        List<Album> albums = getAlbums();

        if (!albums.isEmpty()) {
            sb.append("Albums:\n\t[");
            albums.stream().limit(MAXALBUMS)
                    .forEach(album -> sb.append(album.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append("]");
        } else {
            sb.append("Albums:\n\t[]");
        }

        sb.append("\n\n");

        if (!merchList.isEmpty()) {
            sb.append("Merchandise:\n\t[");
            merchList.stream().limit(MAXLIST)
                    .forEach(merch -> sb.append(merch.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append("]");
        } else {
            sb.append("Merchandise:\n\t[]");
        }

        sb.append("\n\n");

        if (!events.isEmpty()) {
            sb.append("Events:\n\t[");
            events.stream().limit(MAXEVENTS)
                    .forEach(event -> sb.append(event.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append("]");
        } else {
            sb.append("Events:\n\t[]");
        }
        return sb.toString();
    }

    /**
     * Add an event to the artist
     * @param eventName
     * @param eventDescription
     * @param eventDate
     * @return
     */
    @Override
    public String addEvent(final String eventName,
                           final String eventDescription, final String eventDate) {
        if (hasEvent(eventName)) {
            return getUsername() + " has another event with the same name.";
        }
        if (!isValidDate(eventDate)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        return getUsername() + " has added new event successfully.";
    }

    /**
     * Check if an event exists
     * @param eventName
     * @return
     */
    public boolean hasEvent(final String eventName) {
        return events.stream().anyMatch(event -> event.getName().equalsIgnoreCase(eventName));
    }

    /**
     * Check if a date is valid
     * @param dateString
     * @return
     */
    public boolean isValidDate(final String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Do not allow dates like "30-02-2020" to be parsed
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (year < MINYEAR || year > MAXYEAR) {
                return false;
            }
            if (month > MAXMONTH) {
                return false;
            }
            if (day > MAXDAY || (month == FEBRUARY && day > FEBRUARYDAYS)) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Get the user type
     * @return
     */
    @Override
    public String getUserType() {
        return "Artist";
    }

    /**
     * Add a merchandise to the artist
     * @param merchName
     * @param merchDescription
     * @param merchPrice
     * @return
     */
        @Override
        public String addMerch(final String merchName,
                               final String merchDescription, final Integer merchPrice) {
            if (merchList.stream().anyMatch(merch -> merch.getName().equalsIgnoreCase(merchName))) {
                return getUsername() + " has merchandise with the same name.";
            }

            if (merchPrice == null || merchPrice < 0) {
                return "Price for merchandise can not be negative.";
            }

            // Add the new merchandise
            Merch newMerch = new Merch(merchName, merchDescription, merchPrice);
            merchList.add(newMerch);

            return getUsername() + " has added new merchandise successfully.";
        }

    /**
     * searches for a song
     * @return
     */
        public List<Album> getAlbums() {
            return Admin.getAlbums().stream()
                    .filter(album -> album.getOwner().equalsIgnoreCase(getUsername()))
                    .collect(Collectors.toList());
        }
}
