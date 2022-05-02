package com.example.evinder.data;

import com.example.evinder.AppDatabase;
import com.example.evinder.Users;
import com.example.evinder.data.model.LoggedInUser;

import java.io.IOException;

public class RegisterDataSource {
    private AppDatabase db;

    public RegisterDataSource(AppDatabase db){this.db=db;}

    public Result<Users> register(String username, String name, String age,
                                  String phoneNumber, String password, String confirmPassword) {

        try {
            int age_num = Integer.parseInt(age);
            Users user = new Users(username, name, phoneNumber,age_num,password);
            db.usersDao().insert(user);
            System.out.println("Inserting user");
            Users inserted_user = db.usersDao().getUserByEmail(username,password);
            if(inserted_user != null) {
                System.out.println("Created user successfully");
                return new Result.Success<>(user);
            }
            return new Result.Error(new IOException("User not created"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

}
