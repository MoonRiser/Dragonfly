package com.example.dragonfly;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        Button button = findViewById(R.id.BTadd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
                Toast.makeText(MainActivity.this, "热修复技术!!!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.BTwithdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tinker.with(getApplicationContext()).cleanPatch();
                Toast.makeText(MainActivity.this, "撤销操作", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.BTtest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               test();
            }
        });

    }


    public void load(View view) {


        File patchDir = getExternalFilesDir("tinker");
        if (getExternalFilesDir(null) == null) {
            patchDir.mkdirs();
            //  File file = new File(patchDir.getPath(), "/patch_signed_7zip.apk");
        }
        String path = patchDir.getPath();
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                path + "/patch_signed_7zip.apk");
        Log.i("tinker文件路径:", path);

        //  String path = Environment.getExternalStorageDirectory().getAbsolutePath();


    }


    private void test(){
        File patchDir = SharePatchFileUtil.getPatchDirectory(this);
        List<String> list =  getDirsNames(patchDir);
        if (list==null)return;
        for (String patch:list
             ) {
            Log.i("tinker补丁路径:",patch);
        }
    }


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<String> getDirsNames(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        List<String> s = new ArrayList<>();
        for (File value : files) {
            s.add(value.getName());
        }
        return s;
    }


}
