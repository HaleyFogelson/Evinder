package com.example.evinder.data;

import com.example.evinder.data.model.LoggedInUser;

public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private RegisterRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }
}
