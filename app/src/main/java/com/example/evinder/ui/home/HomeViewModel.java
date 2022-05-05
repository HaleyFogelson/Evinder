package com.example.evinder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private SavedStateHandle state;


    private final MutableLiveData<String> mText;

    public HomeViewModel(SavedStateHandle savedStateHandle) {
        this.state = savedStateHandle;
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}