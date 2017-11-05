package gloryhunter.freemusic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SNOW on 10/12/2017.
 */

public class RetrofitFactory {
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
            .baseUrl("https://music-api-for-tk.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            ;
        }
        return retrofit;
    }
}
