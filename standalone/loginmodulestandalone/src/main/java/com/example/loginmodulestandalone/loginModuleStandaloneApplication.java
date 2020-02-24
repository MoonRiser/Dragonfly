package com.example.loginmodulestandalone;


import com.example.loginmodule.LoginModuleApplication;
import com.example.loginmodule.Service;
import com.example.loginmodulestandalone.mock.AppServiceMock;

public class loginModuleStandaloneApplication extends LoginModuleApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Service.appService = new AppServiceMock();
    }
}
