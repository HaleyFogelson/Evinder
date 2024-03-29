package com.example.evinder.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evinder.AppDatabase;
import com.example.evinder.Users;
import com.example.evinder.databinding.ActivityRegisterBinding;
import com.example.evinder.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;
    private AppDatabase db;

    private Users user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(getApplicationContext());

        final EditText usernameEditText = binding.username;
        final EditText nameEditText = binding.name;
        final EditText phoneNumberEditText = binding.phoneNumber;
        final EditText ageEditText = binding.age;
        final EditText passwordEditText = binding.password;
        final EditText passwordConfirmEditText = binding.confirmPassword;
        final Button registerButton = binding.signup;
        final ProgressBar loadingProgressBar = binding.loading;

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {

            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getConfirmPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getConfirmPasswordError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        nameEditText.getText().toString(),
                        phoneNumberEditText.getText().toString(),
                        ageEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        passwordConfirmEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        nameEditText.addTextChangedListener(afterTextChangedListener);
        phoneNumberEditText.addTextChangedListener(afterTextChangedListener);
        ageEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfirmEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Users user = new Users(usernameEditText.getText().toString(),nameEditText.getText().toString(),phoneNumberEditText.getText().toString(),Integer.parseInt(ageEditText.getText().toString()),passwordEditText.getText().toString());
                    db.usersDao().insert(user);
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                setResult(Activity.RESULT_OK);
                //Complete and destroy register activity once successful
                finish();
                loadingProgressBar.setVisibility(View.VISIBLE);
                user = new Users(usernameEditText.getText().toString(),nameEditText.getText().toString(),phoneNumberEditText.getText().toString(),Integer.parseInt(ageEditText.getText().toString()),passwordEditText.getText().toString());
                db.usersDao().insert(user);
                Users inserted_user = db.usersDao().getUserByCredentials(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                if(inserted_user != null) {
                    Toast.makeText(getApplicationContext(),"Created user successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"User not created",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClick(View v) {
        db.usersDao().insert(user);
    }
}
