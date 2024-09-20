package com.fcu.android.bottlerecycleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fcu.android.bottlerecycleapp.database.User;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<User> data = new MutableLiveData<>();

    public LiveData<User> getData() {
        return data;
    }

    public void setData(User user) {
        data.setValue(user);
    }
}


