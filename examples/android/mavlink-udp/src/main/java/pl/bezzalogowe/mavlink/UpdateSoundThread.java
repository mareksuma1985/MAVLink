package pl.bezzalogowe.mavlink;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

/* https://stackoverflow.com/questions/18459122/play-sound-on-button-click-android */

public class UpdateSoundThread implements Runnable {
    private final static String TAG = UpdateTextThread.class.getName();
    public Activity activity;
    public int soundID;
    MediaPlayer player;

    /* https://rpg.hamsterrepublic.com/ohrrpgce/Free_Sound_Effects */
    int[] sounds = {R.raw.nope, R.raw.flame, R.raw.death};

    public UpdateSoundThread(Activity act, int id) {
        this.activity = act;
        this.soundID = id;
    }

    @Override
    public void run() {
        try {
            player = MediaPlayer.create(activity, sounds[soundID]);
            player.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.d(TAG, "Error playing sound: " + e);
        }
    }
}