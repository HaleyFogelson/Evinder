package com.example.evinder.ui.register;

import androidx.annotation.Nullable;

public class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    @Nullable
    Integer ageError;
    @Nullable
    Integer phoneNumberError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,  @Nullable Integer confirmPasswordError,
                      @Nullable Integer ageError,@Nullable Integer phoneNumberError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.confirmPasswordError= confirmPasswordError;
        this.ageError = ageError;
        this.phoneNumberError = phoneNumberError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getConfirmPasswordError() { return confirmPasswordError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
