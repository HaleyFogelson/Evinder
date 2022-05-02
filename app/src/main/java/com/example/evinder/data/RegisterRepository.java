package com.example.evinder.data;

import com.example.evinder.data.model.LoggedInUser;

public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;


    // private constructor : singleton access
    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public Result<LoggedInUser> register(String username, String name, String age,
             String phoneNumber, String password, String confirmPassword){
        // handle register
        Result<LoggedInUser> result = dataSource.register(username,name,age,phoneNumber, password, confirmPassword);
        return result;
    }

    public void register(String username, String name, String age, String phoneNumber, String password) {

    }
}
