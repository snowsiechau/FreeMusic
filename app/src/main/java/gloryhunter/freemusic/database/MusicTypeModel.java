package gloryhunter.freemusic.database;

/**
 * Created by SNOW on 10/15/2017.
 */

public class MusicTypeModel {
    private String id;
    private String key;
    private int imageID;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public int getImageID() {
        return imageID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
