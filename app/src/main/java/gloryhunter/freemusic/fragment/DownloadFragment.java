package gloryhunter.freemusic.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gloryhunter.freemusic.R;
import gloryhunter.freemusic.adapter.DownloadAdapter;
import gloryhunter.freemusic.database.TopSongModel;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    private RecyclerView rvDownload;
    private List<TopSongModel> topSongModels = new ArrayList<>();
    private DownloadAdapter downloadAdapter;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        setupUI(view);

        return view;
    }

    public void setupUI(View view){

        File yourDir = new File(getContext().getExternalCacheDir().toString());
        Log.d("mydir", "setupUI: " + yourDir.toString());
        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {

                String name = f.getName();
                String[] splitSong = name.split("-", 2);
                int index = splitSong[1].lastIndexOf(".");
                String artist = splitSong[1].substring(0, index);
                TopSongModel topSongModel = new TopSongModel(splitSong[0], artist, null);
                topSongModels.add(topSongModel);
                topSongModel.setUrl(getContext().getExternalCacheDir().toString()+ "/" + name);
                topSongModel.setDowload(true);
            }
        }

        rvDownload = view.findViewById(R.id.rv_download);
        downloadAdapter = new DownloadAdapter(topSongModels);
        rvDownload.setAdapter(downloadAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        rvDownload.setLayoutManager(linearLayoutManager);
        rvDownload.setItemAnimator(new FadeInAnimator());
        rvDownload.getItemAnimator().setAddDuration(300);
    }

}
