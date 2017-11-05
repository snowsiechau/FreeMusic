package gloryhunter.freemusic.network.json_model;

import java.util.List;

/**
 * Created by SNOW on 10/12/2017.
 */

public class MainObjectJSON {
    List<SubgenresJSON> subgenres;

    public List<SubgenresJSON> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(List<SubgenresJSON> subgenres) {
        this.subgenres = subgenres;
    }
}
