package com.example.evinder.ui.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evinder.AppDatabase;
import com.example.evinder.R;
import com.example.evinder.Users;
import com.example.evinder.data.RegisterRepository;
import com.example.evinder.data.Result;
import com.example.evinder.data.model.LoggedInUser;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    public RegisterViewModel(RegisterRepository registerRepository){this.registerRepository=registerRepository;}


    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void registerDataChanged(String username, String name, String age,
                                    String phoneNumber, String password, String confirmPassword) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null, null));
        }else if (!isConfirmPasswordValid(password, confirmPassword)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_confirm_password, null, null));
        }else if (!confirmAge(age)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_confirm_password, null, null));
        }   else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }


    public void register(String username, String name, String age,
                         String phoneNumber, String password, String confirmPassword) {

        int ageNum = Integer.parseInt(age);
        //CREATE A new user using the App database
        if(password.matches(confirmPassword)) {
            registerRepository.register(username, name, age, phoneNumber, password);
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
         return (username.contains("@")) && Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    // A placeholder password validation check
    private boolean confirmAge(String age) {
        try{
            int number = Integer.parseInt(age);
            return (number >= 18);
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

}
