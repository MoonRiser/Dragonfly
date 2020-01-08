package com.example.dragonfly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;

import com.example.dragonfly.R;
import com.example.dragonfly.utils.NotificationUtils;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    private NotificationUtils notificationUtils;
    long[] vibrate = new long[]{0, 500, 1000, 1500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        notificationUtils = new NotificationUtils(this);
        findViewById(R.id.BT1).setOnClickListener(this);
        findViewById(R.id.BT2).setOnClickListener(this);
        findViewById(R.id.BT3).setOnClickListener(this);
        findViewById(R.id.BT4).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BT1:
                notificationUtils
                        //让通知左右滑的时候是否可以取消通知
                        .setOngoing(true)
                        //设置内容点击处理intent
                        //      .setContentIntent(resultPendingIntent)
                        //设置状态栏的标题
                        .setTicker("来通知消息啦")
                        //设置自定义view通知栏布局
                        //        .setContent(getRemoteViews())
                        //设置sound
                        .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                        //设置优先级
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        //自定义震动效果
                        .setVibrate(vibrate)
                        //必须设置的属性，发送通知
                        .sendNotification(3, "Test，这是标题", "数风流人物，还看今朝", R.mipmap.ic_launcher);
                break;
            case R.id.BT2:
                notificationUtils.setOngoing(false)
                        .sendNotification(4, "默认的标题", "When I was young ,I listen to the radio", R.mipmap.ic_launcher);
                break;
            case R.id.BT3:
                notificationUtils.clearNotification();
                break;
            case R.id.BT4:
                break;

        }
    }
}
