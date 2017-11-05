package gloryhunter.freemusic.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadManager;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import gloryhunter.freemusic.R;
import gloryhunter.freemusic.database.TopSongModel;
import gloryhunter.freemusic.events.OnTopSongEvent;
import gloryhunter.freemusic.utils.MusicHandle;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_dowload)
    ImageView ivDownload;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_artist_name)
    TextView tvArtistName;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_duration_time)
    TextView tvDurationTime;
    @BindView(R.id.sb_song)
    SeekBar sbSong;
    @BindView(R.id.iv_preview)
    ImageView ivPrevious;
    @BindView(R.id.fa_play)
    FloatingActionButton faPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.av_downloading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    private ThinDownloadManager thinDownloadManager;
    private TopSongModel topSongModel;
    private boolean isDowload;

    public MainPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_player, container, false);

        setupUI(view);

        EventBus.getDefault().register(this);

        faPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandle.playPause();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });



        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isDowload) {
                    Log.d("mydir", "setupUI: " + getContext().getExternalCacheDir().toString());

                    avLoadingIndicatorView.show();
                    isDowload = true;
                    ivDownload.setImageResource(R.drawable.ic_file_download_other_24dp);

                    Uri downloadUri = Uri.parse(topSongModel.getUrl());
                    Uri destinationUri = Uri.parse(getContext().getExternalCacheDir().toString() + "/"
                            + topSongModel.getSong() + " - " + topSongModel.getArtist() + ".mp3");
                    final DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .addCustomHeader("Auth-Token", "YourTokenApiKey")
                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setDownloadContext(getContext())//Optional
                            .setDownloadListener(new DownloadStatusListener() {
                                @Override
                                public void onDownloadComplete(int id) {
                                    avLoadingIndicatorView.hide();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                    Toast.makeText(getContext(), "Download failed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {

                                }
                            });
                    thinDownloadManager = new ThinDownloadManager();
                    thinDownloadManager.add(downloadRequest);

                }else {
                    Toast.makeText(getContext(), "Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);


    }

    @Subscribe(sticky = true)
    public void onReceivedTopSong(OnTopSongEvent onTopSongEvent){
        topSongModel = onTopSongEvent.getTopSongModel();

        tvArtistName.setText(topSongModel.getArtist());
        tvSongName.setText(topSongModel.getSong());
        Picasso.with(getContext()).load(topSongModel.getLargeImage()).transform(new CropCircleTransformation()).into(ivSong);

        File file = new File(getContext().getExternalCacheDir().toString()+"/"
                + topSongModel.getSong() + " - " + topSongModel.getArtist() + ".mp3");
        if(file.exists() || topSongModel.isDowload()) {
            isDowload = true;
            ivDownload.setImageResource(R.drawable.ic_file_download_other_24dp);
        }

        avLoadingIndicatorView.hide();

        MusicHandle.updateRealTime(sbSong, faPlay, ivSong, tvCurrentTime, tvDurationTime);

    }

}
