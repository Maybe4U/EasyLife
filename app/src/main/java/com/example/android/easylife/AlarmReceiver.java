package com.example.android.easylife;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.android.easylife.Utils.LogUtil;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//        alert.setTitle("闹铃")
//                .setPositiveButton("推迟", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//        AlertDialog dialog = alert.create();
//        dialog.show();


        Toast.makeText(context,"时间到",Toast.LENGTH_SHORT).show();
        Intent service = new Intent(context,AlarmService.class);
        service.setAction("com.example.android.easylife.MUSIC");
        context.startService(service);
    }
}
