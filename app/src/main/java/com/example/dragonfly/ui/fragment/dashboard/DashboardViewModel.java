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


    public MutableLiveData<Boolean> isCarousel = new MutableLiveData<>(false);


}