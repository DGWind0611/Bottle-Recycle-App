package com.fcu.android.bottlerecycleapp.ui.qrcode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QrCodeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public QrCodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QrCode fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
