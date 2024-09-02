package com.fcu.android.bottlerecycleapp.ui.recycle_map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecycleMapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecycleMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recycle Map fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}