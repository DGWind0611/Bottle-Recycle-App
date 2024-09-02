package com.fcu.android.bottlerecycleapp.ui.recycle_map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecycleMapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecycleMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("回收站地圖");
    }

    public LiveData<String> getText() {
        return mText;
    }
}