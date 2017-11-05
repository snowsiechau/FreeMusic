package gloryhunter.freemusic.network;

import gloryhunter.freemusic.network.json_model.MainObjectJSON;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SNOW on 10/12/2017.
 */

public interface GetMusicTypeService {
    @GET("api")
    Call<MainObjectJSON> getMusicTypes();
}
