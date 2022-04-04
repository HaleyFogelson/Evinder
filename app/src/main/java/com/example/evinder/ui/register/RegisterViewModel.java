package com.example.evinder.ui.register;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evinder.R;
import com.example.evinder.data.RegisterRepository;
import com.example.evinder.data.Result;
import com.example.evinder.data.model.LoggedInUser;
import com.example.evinder.ui.login.LoginActivity;


public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void registerDataChanged(String username, String name, String age,
                                    String phoneNumber, String password, String confirmPassword) {
    }


    public void register(String username, String name, String age,
                         String phoneNumber, String password, String confirmPassword) {
        Result<LoggedInUser> result = registerRepository.register(username,name,age,phoneNumber, password, confirmPassword);

        if (result instanceof Result.Success) {
            // Here is where the user is set
        } else {
            registerResult.setValue(new RegisterResult(R.string.login_failed));
        }

    }
}
