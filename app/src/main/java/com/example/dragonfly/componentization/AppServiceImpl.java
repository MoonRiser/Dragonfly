package com.example.dragonfly.componentization;

import android.content.Context;
import android.content.Intent;

import com.example.dragonfly.ui.MainActivity;
import com.example.router.app.AppService;

import io.github.prototypez.appjoint.core.ServiceProvider;

@ServiceProvider
public class AppServiceImpl implements AppService {
    @Override
    public void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
