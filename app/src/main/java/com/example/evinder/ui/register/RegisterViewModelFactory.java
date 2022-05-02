package com.example.evinder.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.AppDatabase;
import com.example.evinder.MainActivity;
import com.example.evinder.data.LoginDataSource;
import com.example.evinder.data.LoginRepository;
import com.example.evinder.data.RegisterDataSource;
import com.example.evinder.data.RegisterRepository;
import com.example.evinder.MainActivity;
import com.example.evinder.ui.login.LoginViewModel;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    private AppDatabase db;

    public RegisterViewModelFactory(AppDatabase db){this.db=db;}

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
           return (T) new RegisterViewModel(RegisterRepository.getInstance(new RegisterDataSource(this.db)));

                    //RegisterRepository.getInstance(new RegisterDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
