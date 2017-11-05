package gloryhunter.freemusic.events;

import gloryhunter.freemusic.database.TopSongModel;

/**
 * Created by SNOW on 10/29/2017.
 */

public class OnTopSongEvent {
 private TopSongModel topSongModel;

    public OnTopSongEvent(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }

    public TopSongModel getTopSongModel() {
        return topSongModel;
    }

    public void setTopSongModel(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }
}
