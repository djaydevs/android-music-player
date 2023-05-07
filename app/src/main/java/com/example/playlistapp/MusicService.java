package com.example.playlistapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer myPlayer;
    IBinder binder = new LocalBinder();

    public interface MyBinder {
        void stopPlayback();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder implements MyBinder {
        public void stopPlayback() {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;
            }
        }
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

//        myPlayer = MediaPlayer.create(this, R.raw.as_it_was);
//        myPlayer = MediaPlayer.create(this, R.raw.dont_blame_me);
//        myPlayer = MediaPlayer.create(this, R.raw.cruel_summer);

        myPlayer.setOnCompletionListener(this);
        myPlayer.setLooping(false);
        myPlayer.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        myPlayer.release();
        myPlayer = null;
    }
}
