package com.fcu.android.bottlerecycleapp.ui.personal_data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalDataViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PersonalDataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("洪聖諺");
    }

    public LiveData<String> getText() {
        return mText;
    }
}