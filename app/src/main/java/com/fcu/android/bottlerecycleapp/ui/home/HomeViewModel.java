package com.fcu.android.bottlerecycleapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("愛地球-寶特瓶回收");
    }

    public LiveData<String> getText() {
        return mText;
    }
}