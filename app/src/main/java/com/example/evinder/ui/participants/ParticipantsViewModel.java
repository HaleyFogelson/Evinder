package com.example.evinder.ui.participants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParticipantsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ParticipantsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is participants fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
