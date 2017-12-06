package com.example.android.easylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AlarmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_set);
        Button OKButton = (Button)findViewById(R.id.Ok_btn);
        Button CancelButton = (Button)findViewById(R.id.cancel_btn);
    }

    public void Ok(View v){
        //转换成TimePicker
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        //格式化时间，不足两位时显示为加0，如09:08
        final StringBuilder mFormatBuilder = new StringBuilder();
        final Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int alarmHour = timePicker.getCurrentHour();
        int alarmMinute = timePicker.getCurrentMinute();
        int alarmSecond = 0;
        String time = mFormatter.format("%02d:%02d",alarmHour,alarmMinute).toString();
        Long alarmMillSecond = Long.valueOf(alarmHour *60 * 60 * 1000 + alarmMinute * 60 * 1000);
        Intent intent = new Intent();
        intent.putExtra("time",time);
        intent.putExtra("alarmHour",alarmHour);
        intent.putExtra("alarmMinute",alarmMinute);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void Cancel(View v){
        Intent intent = new Intent();
        intent.putExtra("time","");
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}
