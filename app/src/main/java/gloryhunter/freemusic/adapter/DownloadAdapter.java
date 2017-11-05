package gloryhunter.freemusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gloryhunter.freemusic.R;
import gloryhunter.freemusic.database.TopSongModel;
import gloryhunter.freemusic.events.OnTopSongEvent;

/**
 * Created by SNOW on 11/3/2017.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {
    private List<TopSongModel> topSongModels;

    public DownloadAdapter(List<TopSongModel> topSongModels) {
        this.topSongModels = topSongModels;
    }

    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_dowload, parent, false);

        return new DownloadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DownloadViewHolder holder, int position) {
        holder.setData(topSongModels.get(position));
    }

    @Override
    public int getItemCount() {
        return topSongModels.size();
    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_top_song)
        ImageView ivSongDownload;
        @BindView(R.id.tv_top_song_name)
        TextView tvSongDownload;
        @BindView(R.id.tv_top_song_artist)
        TextView tvArtist;
        View view;

        public DownloadViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void setData(final TopSongModel topSongModel){
            tvSongDownload.setText(topSongModel.getSong());
            tvArtist.setText(topSongModel.getArtist());
            ivSongDownload.setImageResource(R.drawable.ic_file_download_other_24dp);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().postSticky(new OnTopSongEvent(topSongModel));
                }
            });
        }
    }
}
