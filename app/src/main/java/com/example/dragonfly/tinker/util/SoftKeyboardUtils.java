package com.example.dragonfly.tinker.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtils {


    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //切换软键盘的状态
    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }


    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                return inputManager.isActive() && activity.getWindow().getCurrentFocus() != null;
            }
        }
        return false;
    }


//    View.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
//
//        //当键盘弹出隐藏的时候会 调用此方法。
//        @Override
//        public void onGlobalLayout() {
//            final Rect rect = new Rect();
//            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//            final int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
//            final int heightDifference = screenHeight - rect.bottom;
//            boolean visible = heightDifference > screenHeight / 3;
//            if(visible){
//                Log.i(TAG,"软键盘显示");
//            }else {
//                Log.i(TAG,"软键盘隐藏");
//            }
//        }
//    });


}
