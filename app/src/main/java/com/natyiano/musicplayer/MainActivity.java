package com.natyiano.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity{

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        //指定MainActivity与MusicService之间要连接；
        Intent mintent = new Intent(MainActivity.this,MusicService.class);
        bindService(mintent,connection,BIND_AUTO_CREATE);

    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_play:{}
                break;
                case R.id.btn_pause:{}
                break;
            }
        }
    }
    class seekBarlistener implements SeekBar.OnSeekBarChangeListener{
        @Override
        //进度条行进时候的监听
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        //点击进度条开始拖动进度条的监听
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        //停止拖动时候的监听
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public static Handler mhandler = new Handler(Looper.myLooper()){};

}