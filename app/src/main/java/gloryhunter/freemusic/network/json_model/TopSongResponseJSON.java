package gloryhunter.freemusic.network.json_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SNOW on 10/24/2017.
 */

public class TopSongResponseJSON {
    private FeedJSON feed;

    public FeedJSON getFeedJSON() {
        return feed;
    }

    public class FeedJSON {
        List<EntryJSON> entry;

        public List<EntryJSON> getEntry() {
            return entry;
        }

        public class EntryJSON {
            @SerializedName("im:name")
            private NameJSON nameJSON;

            public NameJSON getNameJSON() {
                return nameJSON;
            }

            public class NameJSON {
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            @SerializedName("im:image")
            private List<ImageJSON> imageJSONList;

            public List<ImageJSON> getImageJSONList() {
                return imageJSONList;
            }

            public class ImageJSON {
                private String label;

                public String getLabel() {
                    return label;
                }
            }

            @SerializedName("im:artist")
            private ArtistJSON artistJSON;

            public ArtistJSON getArtistJSON() {
                return artistJSON;
            }

            public class ArtistJSON {
                private String label;

                public String getLabel() {
                    return label;
                }
            }
        }
    }
}
