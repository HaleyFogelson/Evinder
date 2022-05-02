package com.example.evinder.data;

import com.example.evinder.AppDatabase;
import com.example.evinder.Users;
import com.example.evinder.data.model.LoggedInUser;

import java.io.IOException;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private AppDatabase db;

    public LoginDataSource(AppDatabase db){this.db=db;}

    public Result<Users> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            //does the user with the username exist in the database
            System.out.println("we are looking for user");
            List<Users> users = db.usersDao().getAllUsers();
            for( Users u:users ){
                System.out.println(u.name);
            }
            Users user = db.usersDao().getUserByEmail(username,password);
            System.out.println(user.getAbout_me());
            if (user != null){
                return new Result.Success<>(user);
            }
            return new Result.Error(new IOException("User not found"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}