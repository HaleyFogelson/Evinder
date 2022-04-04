package com.example.evinder.ui.register;

import androidx.annotation.Nullable;


public class RegisterResult {
    @Nullable
    private Integer error;

    RegisterResult(@Nullable Integer error) {
        this.error = error;
    }


    @Nullable
    Integer getError() {
        return error;
    }

}
