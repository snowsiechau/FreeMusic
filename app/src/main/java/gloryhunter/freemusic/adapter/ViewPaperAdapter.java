package gloryhunter.freemusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import gloryhunter.freemusic.fragment.DownloadFragment;
import gloryhunter.freemusic.fragment.FavouritFragment;
import gloryhunter.freemusic.fragment.MusicFragment;

/**
 * Created by SNOW on 10/15/2017.
 */

public class ViewPaperAdapter extends FragmentStatePagerAdapter{

    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MusicFragment();

            case 1:
                return new FavouritFragment();

            case 2:
                return new DownloadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
