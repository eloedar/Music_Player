package com.natyiano.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 在这里设置音乐播放功能的服务
 *
 */

public class MusicService extends Service {

    // 设置两个成员变量
    private MediaPlayer player;//多媒体对象
    private Timer timer;//时钟

    public MusicService(){};//一个空的构造函数（为什么放？ ）

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();//这样的话，绑定服务的时候，可以把音乐控制器实例化。
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();//实例化多媒体
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //创建一个内部类MusicControl，功能是让主程序控制service里面的多媒体对象。IBinder 是Binder的子类，因此要返回MusicControl给IBinder。
    class MusicControl extends Binder{
        public void play() {
            try{
                player.reset();//重置音乐播放器
                player = MediaPlayer.create(getApplicationContext(),R.raw.song1); //加载多媒体文件
                player.start(); //开始播放音乐
                addTimer();//添加计时器
            }catch (Exception exception) {//catch用来处理播放时产生的异常
                exception.printStackTrace();
            }
        }
        public void pausePlay(){
            player.pause();    //暂停播放
        }
        public void continuePlay(){
            player.start();   //继续播放
        }
        public void stopPlay(){
            player.stop();
            player.release();
            try {
                timer.cancel();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public void seekTo(int progress){
            player.seekTo(progress);//设置播放位置播放
        }
    }

    //添加计时器，计时器是一个多线程的东西，用于设置音乐播放器中的进度条信息
    public void addTimer(){
        if (timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() { //run就是多线程的一个东西
                    if (player == null) return; //如果player没有实例化，就退出。
                    int duration = player.getDuration();//获取歌曲总长度
                    int currentDuration = player.getCurrentPosition();
                    //将音乐的总时长、播放时长封装到消息对象中去；
                    Message message = MainActivity.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentDuration",currentDuration);
                    message.setData(bundle);//使用bundle给主线程发消息

                    //将消息添加到主线程中
                    MainActivity.handler.sendMessage(message);

                }
            };
            //开始计时任务后5ms，执行第一次任务，以后每500ms执行一次任务
            timer.schedule(task, 5,1000);
        }

    }

}