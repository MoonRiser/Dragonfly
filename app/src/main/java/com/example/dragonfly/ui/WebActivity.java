package com.example.dragonfly.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dragonfly.R;
import com.example.dragonfly.api.NetWork;
import com.example.dragonfly.bean.Categories;

public class WebActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        textView = findViewById(R.id.TVresult);

        findViewById(R.id.BTrequst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetWork.getGankCategories(new NetWork.ResultCallback<Categories>() {
                    @Override
                    public void onSuccess(Categories result) {
                        Log.i("xres", "response已成功接收");
                        textView.setText(result.toString());
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });


    }
}
