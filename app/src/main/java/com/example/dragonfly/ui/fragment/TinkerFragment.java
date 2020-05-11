package com.example.dragonfly.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.dragonfly.R;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TinkerFragment extends Fragment {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_INTERNET = 2;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tinker, container, false);


        verifyStoragePermissions(getActivity());
        root.findViewById(R.id.BTadd).
                setOnClickListener(v -> {
                    load(v);
                    Toast.makeText(getActivity(), "热修复技术!!!", Toast.LENGTH_SHORT).show();
                });

        root.findViewById(R.id.BTwithdraw).setOnClickListener(view -> {
            Tinker.with(getActivity().getApplicationContext()).cleanPatch();
            Toast.makeText(getActivity(), "撤销操作", Toast.LENGTH_SHORT).show();
        });

        root.findViewById(R.id.BTtest).setOnClickListener(view -> test());


        return root;
    }


    private void load(View view) {


        File patchDir = getActivity().getExternalFilesDir("tinker");
        if (getActivity().getExternalFilesDir(null) == null) {
            patchDir.mkdirs();
            //  File file = new File(patchDir.getPath(), "/patch_signed_7zip.apk");
        }
        String path = patchDir.getPath();
        TinkerInstaller.onReceiveUpgradePatch(getActivity().getApplicationContext(),
                path + "/patch_signed_7zip.apk");
        Log.i("tinker文件路径:", path);

        //  String path = Environment.getExternalStorageDirectory().getAbsolutePath();


    }


    private void test() {
        File patchDir = SharePatchFileUtil.getPatchDirectory(getActivity());
        List<String> list = getDirsNames(patchDir);
        if (list == null) return;
        for (String patch : list
        ) {
            Log.i("tinker补丁路径:", patch);
        }
    }


    private void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<String> getDirsNames(File file) {
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
