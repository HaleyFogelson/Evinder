package com.example.evinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UsersDao {
    @Query("SELECT * FROM Users")
    List<Users> getAllUsers();

    @Query("SELECT * FROM Users WHERE user_id=:user_id")
    Users getUserById(int user_id);

    @Query("SELECT * FROM Users WHERE name=:name")
    List<Users> getUsersByName(String name);

    @Query("SELECT * FROM Users WHERE email=:email AND password=:password")
    Users getUserByCredentials(String email, String password);

    @Insert
    long insert(Users user);

    @Update
    public void update(Users user);

    @Delete
    void delete(Users user);
}