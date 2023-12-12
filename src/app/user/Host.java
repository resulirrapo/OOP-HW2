package app.user;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import fileio.input.EpisodeInput;
import lombok.Data;

import java.util.*;


@Data
/**
 * The type Host.
 * This class extends User and is used to create a host.
 */
public class Host extends User {
    private List<Podcast> podcasts;
    private List<Announcement> announcements = new ArrayList<>();
    public Host() {
    }

    public Host(final String username, final int age, final String city) {

        super(username, age, city);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Switch connection status of users
     * @return
     */
    public String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }


    /**
     * Format page content
     * @param pageName
     * @return
     */
    public final String formatPageContent(final String pageName) {
        return "Host";
    }

    /**
     * Get user type
     * @return
     */
    @Override
    public int getUserType() {
        return 2;
    }

    /**
     * Add a new podcast
     * @param podcastName
     * @param podcastDescription
     * @param episodeInputs
     * @return
     */
    @Override
    public String addPodcast(final String podcastName,
                             final String podcastDescription,
                             final List<EpisodeInput> episodeInputs) {
        // Check if a podcast with the same name already exists
        if (podcasts.stream().anyMatch(p -> p.getName().equalsIgnoreCase(podcastName))) {
            return getUsername() + " has another podcast with the same name.";
        }

        // Check for duplicate episodes in the new podcast
        Set<String> episodeNames = new HashSet<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            if (!episodeNames.add(episodeInput.getName())) {
                return getUsername() + " has the same episode in this podcast.";
            }
        }

        // Create new episodes and add them to the podcast
        List<Episode> newEpisodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            Episode newEpisode = new Episode(episodeInput.getName(),
                    episodeInput.getDuration(), episodeInput.getDescription());
            newEpisodes.add(newEpisode);
        }

        // Create the new podcast and add it to the host's list
        Podcast newPodcast = new Podcast(podcastName, podcastDescription, newEpisodes);
        podcasts.add(newPodcast);

        return getUsername() + " has added new podcast successfully.";
    }

    /**
     * adds an announcement
     * @param announcementName
     * @param announcementDescription
     * @return
     */
    @Override
    public String addAnnouncement(final String announcementName,
                                  final String announcementDescription) {
        if (announcements.stream().anyMatch(a -> a.getName().equalsIgnoreCase(announcementName))) {
            return getUsername() + " has already added an announcement with this name.";
        }
        return getUsername() + " has successfully added new announcement.";
    }

    /**
     * removes an announcement
     * @param announcementName
     * @return
     */
    @Override
    public String removeAnnouncement(final String announcementName) {
        System.out.println("Attempting to remove announcement: " + announcementName);
        if (announcements.stream().noneMatch(a -> a.getName().equalsIgnoreCase(announcementName))) {
            System.out.println("No such announcement found.");
            return getUsername() + " has no announcement with the given name.";
        } else {
            announcements.removeIf(a -> a.getName().equalsIgnoreCase(announcementName));
            System.out.println("Announcement removed successfully.");
        }
        return getUsername() + " has successfully deleted the announcement.";
    }
}
