package gloryhunter.freemusic.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import gloryhunter.freemusic.R;
import gloryhunter.freemusic.database.MusicTypeModel;
import gloryhunter.freemusic.events.OnClickMusicTypeEvent;
import gloryhunter.freemusic.fragment.TopSongFragment;
import gloryhunter.freemusic.utils.Utils;

/**
 * Created by SNOW on 10/15/2017.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    private List<MusicTypeModel> musicTypeModels;
    private Context context;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModels, Context context) {
        this.musicTypeModels = musicTypeModels;
        this.context = context;
    }

    //create item view if need

    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_music, parent, false);

        return new MusicTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModels.get(position));
    }

    @Override
    public int getItemCount() {
        return musicTypeModels.size();
    }

    /*Todo 1: tao view holder*/
    class MusicTypeViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMusicType;
        private TextView tvMusicType;
        View view;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);

            ivMusicType = itemView.findViewById(R.id.iv_music_type);
            tvMusicType = itemView.findViewById(R.id.tv_music_type);

            view = itemView;
        }

        public void setData(final MusicTypeModel musicTypeModel){
            //ivMusicType.setImageResource(musicTypeModel.getImageID());
            Picasso.with(context).load(musicTypeModel.getImageID()).into(ivMusicType);

            tvMusicType.setText(musicTypeModel.getKey());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().postSticky(new OnClickMusicTypeEvent(musicTypeModel));
                    Utils.openFragment(((FragmentActivity)context).getSupportFragmentManager(),
                            R.id.layout_main,
                            new TopSongFragment());

                }
            });
        }
    }
}
