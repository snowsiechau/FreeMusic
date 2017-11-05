package gloryhunter.freemusic.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by SNOW on 10/17/2017.
 */

public class Utils {
    public static void openFragment(FragmentManager fragmentManager, int layoutID, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void rotateImage(ImageView imageView, boolean isRotate){
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        if (isRotate){
            if (imageView.getAnimation() == null)
                imageView.startAnimation(rotateAnimation);
        }else {
            imageView.setAnimation(null);
        }
    }

    public static String convertTime(long milis){
        long min = TimeUnit.MILLISECONDS.toMinutes(milis);
        return String.format("%02d:%02d",
                min,
                TimeUnit.MILLISECONDS.toSeconds(milis - min * 60 * 1000)
                );
    }
}
