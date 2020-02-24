package com.example.loginmodulestandalone.mock;

import android.content.Context;
import android.widget.Toast;

import com.example.router.app.AppService;

public class AppServiceMock implements AppService {
    @Override
    public void startActivity(Context context) {
        Toast.makeText(context, "mock数据：已执行跳转", Toast.LENGTH_SHORT).show();
    }
}
