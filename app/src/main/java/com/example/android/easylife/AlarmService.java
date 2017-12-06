package com.example.android.easylife;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;

import com.example.android.easylife.Utils.LogUtil;

import java.io.File;
import java.io.IOException;

public class AlarmService extends Service {
    private MediaPlayer player = new MediaPlayer();;
    public IBinder onBind(Intent intent){
        return null;
    }

//    class MusicBinder extends Binder implements InterfaceControl{
//        @Override
//        public void playMusic() {
//            AlarmService.this.playMusic();
//        }
//    }
//    private void playMusic(){
//        //player = new MediaPlayer();
//        try {
//            File file = new File(Environment.getExternalStorageDirectory() ,"music.mp3");
//            player.setDataSource(file.getPath());
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        player.start();
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM)!=0){
            try {
                LogUtil.d("服务","开启服务");

                File file = new File(Environment.getExternalStorageDirectory() ,"music.mp3");

                //audioManager.setStreamVolume(AudioManager.STREAM_RING, 1, 0);

                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.setDataSource(file.getPath());
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.setLooping(true);
            player.start();
            //player.setVolume(0.1f, 0.1f);
            //if(flags!=0){
//                final int volume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
//                new Thread(){
//                    public void run(){
//                        super.run();
//                        for(int i=1;i<=volume;i++){
//                            audioManager.setStreamVolume(AudioManager.STREAM_RING, i, 0);
//                            LogUtil.d("线程","耗时操作");
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }.start();
            //}


            //渐强的时长，单位：毫秒；默认10秒
            //final long duration = 5 * 60 * 1000;
            final long duration = 10 * 1000;
            //音量调节的时间间隔
            long interval = duration / 10;
            new CountDownTimer(duration, interval){

                @Override
                public void onTick(long millisUntilFinished) {
                    float volume = 1f - millisUntilFinished * 1.0f / duration;
                    player.setVolume(volume, volume);
                }

                @Override
                public void onFinish() {
                    player.setVolume(1f, 1f);
                }
            }.start();
        //}

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy(){
        super.onDestroy();
        if(player!=null){
            player.stop();
        }
    }
}
