package com.example.loginmodulestandalone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loginmodule.LoginFragment;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, LoginFragment.newInstance())
                    .commit();
        }

    }


}
