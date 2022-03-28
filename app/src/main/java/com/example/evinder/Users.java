package com.example.evinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Users {

    @PrimaryKey(autoGenerate = true)
    public int user_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "whatsapp")
    public String whatsapp;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "about_me")
    public String about_me;

    @ColumnInfo(name = "password")
    public String password;

    public Users(int user_id, String name, String whatsapp, String email, int age, String about_me, String password) {
        this.user_id = user_id;
        this.name = name;
        this.whatsapp = whatsapp;
        this.email = email;
        this.age = age;
        this.about_me = about_me;
        this.password = password;
    }

    public int getUser_id(){
        return this.user_id;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getWhatsapp(){
        return this.whatsapp;
    }

    public void setWhatsapp(String whatsapp){
        this.whatsapp = whatsapp;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getAbout_me(){
        return this.about_me;
    }

    public void setAbout_me(String about_me){
        this.about_me = about_me;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
