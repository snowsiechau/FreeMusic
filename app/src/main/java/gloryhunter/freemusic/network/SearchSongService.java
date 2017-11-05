package gloryhunter.freemusic.network;

import gloryhunter.freemusic.network.json_model.SearchSongJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SNOW on 10/29/2017.
 */

public interface SearchSongService {
    @GET("http://103.1.209.134/services/api/audio")
    Call<SearchSongJSON> getSearchSong(@Query("search_terms") String search);
}
