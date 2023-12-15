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
    private String name;
    private List<Podcast> podcasts;
    private static final int MAXPODCAST = 5;
    private static final int MAXANNOUNCEMENT = 5;
    private List<Announcement> announcements = new ArrayList<>();
    public Host() {
        setPage("HostPage");
    }

    public Host(final String username, final int age, final String city) {

        super(username, age, city);
        this.announcements = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        setOnline(true);
        setPage("HostPage");
    }
    @Override
    public final String changePage(final String nextPage) {
        return getUsername() + " is not authorized to change pages.";
    }
    /**
     * Switch connection status of users
     * @return
     */
    public final String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }

    /**
     * Format page content
     * @param pageName
     * @return
     */
    public final String formatPageContent(final String pageName) {
        if (pageName.equals("Host")) {
            return formatHostPage();
        } else {
            return "Error";
        }
    }

    /**
     * Format host page
     * @return
     */
    public final String formatHostPage() {
        StringBuilder sb = new StringBuilder();
        if (!podcasts.isEmpty()) {
            sb.append("Podcast:\n\t[");
            podcasts.stream()
                    .limit(MAXPODCAST)
                    .forEach(podcast -> sb.append(podcast.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append("]");
        } else {
            sb.append("Liked songs:\n\t[]");
        }

        sb.append("\n\n");

        if (!announcements.isEmpty()) {
            sb.append("Announcements:\n\t[");
            announcements.stream()
                    .limit(MAXANNOUNCEMENT)
                    .forEach(announcement -> sb.append(announcement.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append("]");
        } else {
            sb.append("Announcements:\n\t[]");
        }
        return sb.toString();
    }

    /**
     * Get user type
     * @return
     */
    @Override
    public final String getUserType() {
        return "Host";
    }

    /**
     * Add a new podcast
     * @param podcastName
     * @param podcastDescription
     * @param episodeInputs
     * @return
     */
    @Override
    public final String addPodcast(final String podcastName,
                             final String podcastDescription,
                             final List<EpisodeInput> episodeInputs) {
        if (podcasts.stream().anyMatch(p -> p.getName().equalsIgnoreCase(podcastName))) {
            return getUsername() + " has another podcast with the same name.";
        }

        Set<String> episodeNames = new HashSet<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            if (!episodeNames.add(episodeInput.getName())) {
                return getUsername() + " has the same episode in this podcast.";
            }
        }
        List<Episode> newEpisodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            Episode newEpisode = new Episode(episodeInput.getName(),
                    episodeInput.getDuration(), episodeInput.getDescription());
            newEpisodes.add(newEpisode);
        }

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
    public final String addAnnouncement(final String announcementName,
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
    public final String removeAnnouncement(final String announcementName) {
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
