package com.example.dragonfly.ui.fragment.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

//    private MutableLiveData<String> mText;
//
//    public DashboardViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }


    private MutableLiveData<Boolean> isShowDialog;

    public DashboardViewModel() {
        isShowDialog = new MutableLiveData<Boolean>();
        isShowDialog.setValue(false);
    }


    public MutableLiveData<Boolean> getIsShowDialog() {
        return isShowDialog;
    }
}