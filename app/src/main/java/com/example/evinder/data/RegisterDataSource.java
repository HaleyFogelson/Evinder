package com.example.evinder.data;

import com.example.evinder.data.model.LoggedInUser;

import java.io.IOException;

public class RegisterDataSource {

    public Result<LoggedInUser> register(String username, String name, String age,
                                         String phoneNumber, String password, String confirmPassword) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

}
