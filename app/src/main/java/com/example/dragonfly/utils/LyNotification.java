package com.example.dragonfly.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.COLOR_DEFAULT;
import static androidx.core.app.NotificationCompat.DEFAULT_SOUND;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;
import static androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE;
import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class LyNotification extends ContextWrapper {

    /**
     * 在Android O（8.0）默认配置两个Channel：     常规（“default”）    紧要（“urge”）
     * 区别：.......................................................................... 通知提示音.................通知提示音、震动、浮动通知
     * <p>
     * 简单用法：      new LyNotification(this)
     * .sendNotification(1, "testTitle", "通知测试内容", R.drawable.ic_stat_name, false);
     */
    public static final String CHANNEL_ID = "default";
    private static final String CHANNEL_NAME = "常规通知";

    public static final String CHANNEL_ID_URGE = "urge";
    private static final String CHANNEL_NAME_URGE = "重要通知";

    private NotificationManager mManager;
    private int[] flags;

    public LyNotification(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            createNotificationChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //第一个参数：channel_id
        //第二个参数：channel_name
        //第三个参数：设置通知重要性级别
        //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
        //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationChannel channelUrge = new NotificationChannel(CHANNEL_ID_URGE, CHANNEL_NAME_URGE,
                NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);//呼吸灯
        channel.setLockscreenVisibility(VISIBILITY_PUBLIC);//锁屏显示通知
        channel.setLightColor(COLOR_DEFAULT);//闪关灯的灯光颜色
        channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
        channel.setShowBadge(true);//设置图标右上角小圆点
        channel.setDescription("常规设置");

        channelUrge.enableVibration(true);
        channelUrge.enableLights(true);
        channelUrge.setLockscreenVisibility(VISIBILITY_PRIVATE);
        channelUrge.setLightColor(Color.RED);
        channelUrge.setBypassDnd(true);
        channelUrge.setShowBadge(true);
        //   channelUrge.setAllowBubbles(true);
        channelUrge.setDescription("开启震动、浮动通知");
//        channel.canBypassDnd();//是否绕过请勿打扰模式
//        channel.canShowBadge();//桌面launcher的消息角标
//        channel.getAudioAttributes();//获取系统通知响铃声音的配置
//        channel.getGroup();//获取通知取到组
//        channel.shouldShowLights();//是否会有灯光
        getManager().createNotificationChannel(channel);
        getManager().createNotificationChannel(channelUrge);

    }

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return NotificationManager对象
     */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 清空所有的通知
     */
    public void clearAllNotification() {
        getManager().cancelAll();
    }

    /**
     * 获取Notification
     *
     * @param title   title
     * @param content content
     */
    public Notification getNotification(String title, String content, @DrawableRes int icon) {
        Notification notification;
        NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
        notification = builder.build();
        //      }
        if (flags != null && flags.length > 0) {
            for (int flag : flags) {
                notification.flags |= flag;
            }
        }
        return notification;
    }

    /**
     * @param notifyId
     * @param title
     * @param content
     * @param icon
     */
    public void sendNotification(int notifyId, String title, String content, @DrawableRes int icon) {
        NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
        Notification notification = builder.build();
        if (flags != null && flags.length > 0) {
            for (int flag : flags) {
                notification.flags |= flag;
            }
        }
        getManager().notify(notifyId, notification);
    }

    /**
     * 自增id的发送消息
     *
     * @param title
     * @param content
     * @param icon
     */
    public void sendNotification(String title, String content, @DrawableRes int icon) {
        int id = (int) System.currentTimeMillis();
        sendNotification(id, title, content, icon);

    }


    private NotificationCompat.Builder getNotificationCompat(String title, String content, int icon) {
        NotificationCompat.Builder builder;
        builder = isUrge ? new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_URGE) : new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        builder.setOnlyAlertOnce(onlyAlertOnce);
        builder.setOngoing(ongoing);
        if (isUrge) {
            builder.setDefaults(DEFAULT_VIBRATE | DEFAULT_SOUND);
            builder.setPriority(PRIORITY_HIGH);
        } else {
            builder.setDefaults(DEFAULT_SOUND);
        }
        builder.setPriority(priority);
        if (color != 0) {
            builder.setColor(color);
        }
        if (remoteViews != null) {
            builder.setContent(remoteViews);
        }
        if (intent != null) {
            builder.setContentIntent(intent);
        }
        if (ticker != null && ticker.length() > 0) {
            builder.setTicker(ticker);
        }
        if (when != 0) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (defaults != 0) {
            builder.setDefaults(defaults);
        }
        if (style != null) {
            builder.setStyle(style);
        }
        if (max != 0) {
            builder.setProgress(max, progress, indeterminate);
        }
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }


    private boolean ongoing = false;
    private RemoteViews remoteViews = null;
    private PendingIntent intent = null;
    private String ticker = "";
    private int priority = Notification.PRIORITY_DEFAULT;
    private boolean onlyAlertOnce = false;
    private long when = 0;
    private Uri sound = null;
    private int defaults = 0;
    private long[] pattern = null;
    private boolean isUrge = false;
    private NotificationCompat.Style style;
    private int color = 0;

    private int progress = 0;
    private int max = 0;
    private boolean indeterminate = true;


    /**
     * 让通知左右滑的时候是否可以取消通知
     *
     * @param ongoing 是否可以取消通知.false:可以取消，true：不可取消
     * @return
     */
    public LyNotification setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
        return this;
    }

    /**
     * 设置自定义view通知栏布局
     *
     * @param remoteViews view
     * @return
     */
    public LyNotification setContent(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        return this;
    }

    /**
     * 设置内容点击
     *
     * @param intent intent
     * @return
     */
    public LyNotification setContentIntent(PendingIntent intent) {
        this.intent = intent;
        return this;
    }

    /**
     * 设置状态栏的标题
     *
     * @param ticker 状态栏的标题
     * @return
     */
    public LyNotification setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }


    /**
     * 设置优先级
     * 注意：
     * Android 8.0以及上，在 NotificationChannel 的构造函数中指定，总共要五个级别；
     * Android 7.1（API 25）及以下的设备，还得调用NotificationCompat 的 setPriority方法来设置
     *
     * @param priority 优先级，默认是Notification.PRIORITY_DEFAULT
     * @return
     */
    public LyNotification setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * 是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
     *
     * @param onlyAlertOnce 是否只提示一次，默认是false
     * @return
     */
    public LyNotification setOnlyAlertOnce(boolean onlyAlertOnce) {
        this.onlyAlertOnce = onlyAlertOnce;
        return this;
    }

    /**
     * 设置通知时间，默认为系统发出通知的时间，通常不用设置
     *
     * @param when when
     * @return
     */
    public LyNotification setWhen(long when) {
        this.when = when;
        return this;
    }

    /**
     * 设置sound
     *
     * @param sound sound
     * @return
     */
    public LyNotification setSound(Uri sound) {
        this.sound = sound;
        return this;
    }


    /**
     * 设置默认的提示音
     *
     * @param defaults defaults
     * @return
     */
    public LyNotification setDefaults(int defaults) {
        this.defaults = defaults;
        return this;
    }

    /**
     * 自定义震动效果
     *
     * @param pattern pattern
     * @return
     */
    public LyNotification setVibrate(long[] pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * 设置flag标签
     *
     * @param flags flags
     * @return
     */
    public LyNotification setFlags(int... flags) {
        this.flags = flags;
        return this;
    }


    public LyNotification setStyle(NotificationCompat.Style style) {
        this.style = style;
        return this;
    }

    /**
     * @param isUrge false：默认通知（只有通知提示音）。true：紧要通知（具有提示音、震动、浮动通知）
     * @return
     */
    public LyNotification setUrge(boolean isUrge) {
        this.isUrge = isUrge;
        return this;
    }

    /**
     * @param progress 通知栏进度条的进度
     * @return
     */
    public LyNotification setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    /**
     * @param max 进度条进度的最大值
     * @return
     */
    public LyNotification setMax(int max) {
        this.max = max;
        return this;
    }

    /**
     * @param indeterminate 进度条进度不是确定的？true:进度不确定。false：需要传入progress参数
     * @return
     */
    public LyNotification setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        return this;
    }

    public LyNotification setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
