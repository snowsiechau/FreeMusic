package gloryhunter.freemusic.activities;

import android.app.Notification;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import gloryhunter.freemusic.adapter.ViewPaperAdapter;
import gloryhunter.freemusic.R;
import gloryhunter.freemusic.database.MusicTypeModel;
import gloryhunter.freemusic.database.TopSongModel;
import gloryhunter.freemusic.events.OnClickMusicTypeEvent;
import gloryhunter.freemusic.events.OnTopSongEvent;
import gloryhunter.freemusic.fragment.MainPlayerFragment;
import gloryhunter.freemusic.fragment.TopSongFragment;
import gloryhunter.freemusic.notification.MusicNotification;
import gloryhunter.freemusic.utils.MusicHandle;
import gloryhunter.freemusic.utils.Utils;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPaper;

    @BindView(R.id.layout_mini)
    RelativeLayout rlMini;
    @BindView(R.id.iv_top_song)
    ImageView ivTopSong;
    @BindView(R.id.tv_top_song_name)
    TextView tvTopSongName;
    @BindView(R.id.tv_top_song_artist)
    TextView tvArtistName;
    @BindView(R.id.sb_song)
    SeekBar sbSong;
    @BindView(R.id.fb_mini)
    FloatingActionButton fbMini;

    MusicTypeModel musicTypeModel;
    TopSongModel topSongModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        EventBus.getDefault().register(this);



    }

    @Subscribe(sticky = true)
    public void onReceivedTopSongEvent(OnTopSongEvent topSongEvent){
        rlMini.setVisibility(View.VISIBLE);
        topSongModel = topSongEvent.getTopSongModel();
        tvTopSongName.setText(topSongModel.getSong());
        tvArtistName.setText(topSongModel.getArtist());
        Log.d(TAG, "onReceivedTopSongEvent: " + topSongModel.getSmallImage());

        if (topSongModel.getSmallImage() != null) {
            Picasso.with(this).load(topSongModel.getSmallImage()).transform(new CropCircleTransformation()).into(ivTopSong);
        }else {
            ivTopSong.setImageResource(R.drawable.ic_file_download_other_24dp);
        }

        MusicHandle.searchSong(topSongModel, this);
        MusicHandle.updateRealTime(sbSong, fbMini, ivTopSong, null, null);
    }

    private void setupUI() {
        ButterKnife.bind(this);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPaper = (ViewPager) findViewById(R.id.view_paper);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_file_download_black_24dp));

        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(100);
        tabLayout.getTabAt(2).getIcon().setAlpha(100);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(255);
                viewPaper.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rlMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openFragment(getSupportFragmentManager(), R.id.ll_container, new MainPlayerFragment());
            }
        });

        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        viewPaper.setAdapter(viewPaperAdapter);
        viewPaper.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        sbSong.setPadding(0,0,0,0);

        fbMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandle.playPause();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());

        if (getSupportFragmentManager().getBackStackEntryCount() != 0){
            super.onBackPressed();
        }else {
            moveTaskToBack(true);
        }
    }
}
