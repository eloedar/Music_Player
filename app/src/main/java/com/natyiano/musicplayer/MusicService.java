package com.natyiano.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    class MusicControl extends Binder {
        public void play(){
            try{
                player.reset();
                player = MediaPlayer.create(getApplicationContext(),R.raw.song1);
                player.start();
                addTimer();
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        }

        public void pausePlayer(){
            player.pause();
        }

        public void continuePlay(){
            player.start();
        }

        public void stopPlay(){
            player.stop();
            player.release();
            timer.cancel();
        }

        public void musicSeekTo(int progress){
            player.seekTo(progress);
        }
    }

    private void addTimer() {
        if (timer == null){
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (player == null) return;
                    int duration = player.getDuration();
                    int currentDuration = player.getCurrentPosition();

                    Message message = MainActivity.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentDuration",currentDuration);
                    message.setData(bundle);

                    MainActivity.handler.sendMessage(message);
                }
            };
            timer.schedule(timerTask,5,1000);
        }
    }

}