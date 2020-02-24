package com.example.dragonfly.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.dragonfly.R;
import com.example.dragonfly.utils.LyNotification;
import com.example.dragonfly.utils.SoftKeyboardUtil;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.BT1).setOnClickListener(this);
        findViewById(R.id.BT2).setOnClickListener(this);
        findViewById(R.id.BT3).setOnClickListener(this);
        findViewById(R.id.BT4).setOnClickListener(this);
        findViewById(R.id.BT5).setOnClickListener(this);
        findViewById(R.id.BT6).setOnClickListener(this);
        findViewById(R.id.BT7).setOnClickListener(this);
        findViewById(R.id.BT8).setOnClickListener(this);
        findViewById(R.id.BT9).setOnClickListener(this);
        findViewById(R.id.BT10).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BT1:
                new LyNotification(this)
                        //让通知左右滑的时候是否可以取消通知
                        .setOngoing(true)
                        //设置内容点击处理intent
                        //      .setContentIntent(resultPendingIntent)
                        //设置状态栏的标题
                        .setTicker("来通知消息啦")
                        //设置自定义view通知栏布局
                        //        .setContent(getRemoteViews())
                        //必须设置的属性，发送通知
                        .sendNotification(1, "普通通知", "数风流人物，还看今朝", R.drawable.ic_stat_name);
                break;
            case R.id.BT2:
                new LyNotification(this)
                        .setUrge(true)
                        .setColor(Color.RED)//设置通知图标的颜色
                        .sendNotification(2, "紧要通知", "When I was young ,I listen to the radio", R.drawable.ic_stat_name);
                break;
            case R.id.BT3:
                new LyNotification(this)
                        .sendNotification("自增id", "点一次多一个", R.drawable.ic_stat_name);
                break;
            case R.id.BT4:
                new LyNotification(this)
                        .setUrge(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(
                                "千古江山，英雄无觅，孙仲谋处。\n" +
                                        "舞榭歌台，风流总被、雨打风吹去。\n" +
                                        "斜阳草树，寻常巷陌，\n" +
                                        "人道寄奴曾住。\n" +
                                        "想当年，金戈铁马，气吞万里如虎。\n" +
                                        "\n" +
                                        "元嘉草草，封狼居胥，\n" +
                                        "赢得仓皇北顾。\n" +
                                        "四十三年，望中犹记，烽火扬州路。\n" +
                                        "可堪回首，佛狸祠下，\n" +
                                        "一片神鸦社鼓。\n" +
                                        "凭谁问：廉颇老矣，尚能饭否？"
                        )).sendNotification(4, "大文本测试", "", R.drawable.ic_stat_name);
                break;
            case R.id.BT5:
                new LyNotification(this)
                        .setUrge(true)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigLargeIcon(BitmapFactory.decodeResource(getResources(), com.example.commons.R.drawable.limbo))
                                .bigPicture(BitmapFactory.decodeResource(getResources(), com.example.commons.R.drawable.bkg_08_august)))
                        .sendNotification(5, "大图片测试", "", R.drawable.ic_stat_name);
                break;
            case R.id.BT6:
                new LyNotification(this)
                        .clearAllNotification();
                break;
            case R.id.BT7:
                new LyNotification(this)
                        .setIndeterminate(false)
                        .setMax(100)
                        .setProgress(75)
                        .sendNotification(7, "进度条通知", "", R.drawable.ic_stat_name);
                break;
            case R.id.BT8:
                SoftKeyboardUtil.showKeyboard(findViewById(R.id.ETinput));
                break;
            case R.id.BT9:
                SoftKeyboardUtil.hideKeyboard(findViewById(R.id.ETinput));
                break;
            case R.id.BT10:
                SoftKeyboardUtil.toggleSoftInput(findViewById(R.id.ETinput));
                break;

        }
    }
}
