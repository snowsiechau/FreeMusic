package gloryhunter.freemusic.fragment;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gloryhunter.freemusic.R;
import gloryhunter.freemusic.RetrofitFactory;
import gloryhunter.freemusic.adapter.TopSongAdapter;
import gloryhunter.freemusic.database.MusicTypeModel;
import gloryhunter.freemusic.database.TopSongModel;
import gloryhunter.freemusic.events.OnClickMusicTypeEvent;
import gloryhunter.freemusic.network.GetTopSongService;
import gloryhunter.freemusic.network.json_model.TopSongResponseJSON;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongFragment extends Fragment {
    private static final String TAG = "TopSongFragment";

    private  List<TopSongModel> topSongModels = new ArrayList<>();
    private TopSongAdapter topSongAdapter;

    @BindView(R.id.toolbar)
    Toolbar tbTopsong;
    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.iv_top_song)
    ImageView ivTopSong;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv_top_songs)
    RecyclerView rvTopSongs;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;


    MusicTypeModel musicTypeModel;
    public TopSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);

        setupUI();

        loadData();

        return view;
    }

    private void loadData() {
        GetTopSongService getTopSongService = RetrofitFactory.getInstance().create(GetTopSongService.class);
        getTopSongService.getTopSong(musicTypeModel.getId()).enqueue(new Callback<TopSongResponseJSON>() {
            @Override
            public void onResponse(Call<TopSongResponseJSON> call, Response<TopSongResponseJSON> response) {
                avLoadingIndicatorView.hide();
                List<TopSongResponseJSON.FeedJSON.EntryJSON> entryJSONList = response.body().getFeedJSON().getEntry();

                for (int i = 0; i < entryJSONList.size(); i++){
                    TopSongResponseJSON.FeedJSON.EntryJSON entryJSON = entryJSONList.get(i);
                    String song = entryJSON.getNameJSON().getLabel();
                    String artist= entryJSON.getArtistJSON().getLabel();
                    String smallImage = entryJSON.getImageJSONList().get(2).getLabel();
                    Log.d(TAG, "onResponse: " + smallImage);

                    TopSongModel topSongModel = new TopSongModel(song, artist, smallImage);
                    topSongModels.add(topSongModel);
                    topSongAdapter.notifyItemChanged(i);
                }
//                topSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TopSongResponseJSON> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Subscribe(sticky = true)
    public void onEventMusicTypeClicked(OnClickMusicTypeEvent onClickMusicTypeEvent){
        musicTypeModel = onClickMusicTypeEvent.getMusicTypeModel();
    }

    private void setupUI() {
        tbTopsong.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbTopsong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        tvMusicType.setText(musicTypeModel.getKey());
        Picasso.with(getContext()).load(musicTypeModel.getImageID()).into(ivTopSong);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0){
                    tbTopsong.setBackground(getResources().getDrawable(R.drawable.cust_imageview_gradient));
                }else {
                    tbTopsong.setBackground(null);
                }
            }
        });



        topSongAdapter = new TopSongAdapter(getContext(), topSongModels);
        rvTopSongs.setAdapter(topSongAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTopSongs.setLayoutManager(layoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTopSongs.getContext(),
//                layoutManager.getOrientation());
//        rvTopSongs.addItemDecoration(dividerItemDecoration);

        avLoadingIndicatorView.show();

        rvTopSongs.setItemAnimator(new FadeInAnimator());
        rvTopSongs.getItemAnimator().setAddDuration(300);
    }

}
