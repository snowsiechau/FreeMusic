package gloryhunter.freemusic.database;

/**
 * Created by SNOW on 10/24/2017.
 */

public class TopSongModel {
    private String song;
    private String artist;
    private String smallImage;
    private String url;
    private String largeImage;
    private boolean isDowload;

    public boolean isDowload() {
        return isDowload;
    }

    public void setDowload(boolean dowload) {
        isDowload = dowload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public TopSongModel(String song, String artist, String smallImage) {
        this.song = song;
        this.artist = artist;
        this.smallImage = smallImage;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}
