package gloryhunter.freemusic.events;

import gloryhunter.freemusic.database.MusicTypeModel;

/**
 * Created by SNOW on 10/17/2017.
 */

public class OnClickMusicTypeEvent {
    private MusicTypeModel musicTypeModel;

    public OnClickMusicTypeEvent(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }

    public MusicTypeModel getMusicTypeModel() {
        return musicTypeModel;
    }
}
