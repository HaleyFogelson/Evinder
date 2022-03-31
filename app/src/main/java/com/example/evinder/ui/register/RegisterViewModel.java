package com.example.evinder.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.evinder.data.RegisterRepository;


public class RegisterViewModel {

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
    }
}
