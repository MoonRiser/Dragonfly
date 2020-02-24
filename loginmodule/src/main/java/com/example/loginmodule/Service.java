package com.example.loginmodule;

import com.example.router.app.AppService;

import io.github.prototypez.appjoint.AppJoint;

public class Service {

   public static AppService appService = AppJoint.service(AppService.class);
}
