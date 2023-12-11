package app.user;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
/**
 * The type Artist.
 * This class extends User and is used to create an artist.
 */
public class Artist extends User {
    private Map<String, Set<String>> albums;
    private List<Event> events = new ArrayList<>();
    private List<Merch> merchList = new ArrayList<>();
    public static final int MAXYEAR = 2023;
    public static final int MINYEAR = 1900;
    public static final int MAXMONTH = 12;
    public static final int MAXDAY = 31;
    public static final int FEBRUARY = 2;
    public static final int FEBRUARYDAYS = 28;

    public Artist() {
    }

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.albums = new HashMap<>();
        this.events = new ArrayList<>();
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

    /**
     * formats the page content
     * @param pageName
     * @return
     */
    public String formatPageContent(final String pageName) {
        return "Artist";
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
    }
