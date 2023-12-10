package app.pagesistem;

import lombok.Data;

import java.util.List;
@Data
public abstract class HomePage {
    private List<String> likedSongs;
    private List<String> followedPlaylists;

    private HomePage(final List<String> likedSongs, final List<String> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    /**
     * Prints the current page content
     * @param likedSongs
     * @param followedPlaylists
     * @return
     */
    public static String printCurrentPage(final List<String> likedSongs,
                                          final List<String> followedPlaylists) {
        // Format and return the page content
        StringBuilder pageContent = new StringBuilder();

        pageContent.append("Liked songs:\n\t");
        pageContent.append(String.join(", ", likedSongs));
        pageContent.append("\n\nFollowed playlists:\n\t");
        pageContent.append(String.join(", ", followedPlaylists));

        return pageContent.toString();
    }
}
