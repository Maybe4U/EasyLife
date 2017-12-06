package com.example.android.easylife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.easylife.Utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button addAlarmBtn;
    private TextView alarmTv;
    private String time;
    private int alarmHour;
    private int alarmMinute;
    private Long alarmSecond;
    private Long currentSecond;
    private String alarmTime;
    private Long alarmMillSecond;
    private int hourOfDay;
    private int minute;
    private InterfaceControl ic;
    private Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取存放时间的textview
        alarmTv = (TextView)findViewById(R.id.alarm_item) ;
        addAlarmBtn = (Button)findViewById(R.id.add_alarm);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
                startActivityForResult(intent,1);
            }
        });

        playBtn = (Button)findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ic.playMusic();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    //获取闹钟时间

                    time = data.getStringExtra("time");
                    hourOfDay = data.getIntExtra("alarmHour",0);
                    minute = data.getIntExtra("alarmMinute",0);

                    alarmTv.setText(time);
                    alarmSecond = timeStrToMillSecond(alarmTime);

                    //获取当前时间
                    Calendar calendar = Calendar.getInstance();
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);

                    LogUtil.d("hourOfDay",hourOfDay + "");
                    LogUtil.d("minute",minute + "");

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);

                    //int requestCode = 0;
                    Intent i = new Intent(MainActivity.this,AlarmReceiver.class);
                    //i.putExtra("requestCode",requestCode);
                    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,0,i,0);
                    //获取AlarmManager实例
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
                    am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5 * 1000,pi);
                    Toast.makeText(MainActivity.this,"设置的时间为："+String.valueOf(hourOfDay)+
                            ":"+ String.valueOf(minute),Toast.LENGTH_SHORT).show();

//                    Intent in = new Intent(MainActivity.this,AlarmService.class);
//                    startService(in);
//                    bindService(in,new MyServiceConn(),BIND_AUTO_CREATE);

                }else{
                    Toast.makeText(MainActivity.this,"闹钟未设置",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public static Long timeStrToMillSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Long millSecond = format.parse(time).getTime() * 1000;
            return millSecond;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    class MyServiceConn implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ic = (InterfaceControl) iBinder;
            //ic.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}
