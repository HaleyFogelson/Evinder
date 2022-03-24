package com.example.evinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Events {

    @PrimaryKey(autoGenerate = true)
    public int event_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "creator")
    public int creator;

    public Events(int event_id, String name, String description, long date, int creator){
        this.event_id = event_id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.creator = creator;
    }

    public int getEvent_id(){
        return this.event_id;
    }

    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public long getDate(){
        return this.date;
    }

    public void setDate(long date){
        this.date = date;
    }

    public int getCreator(){
        return this.creator;
    }

    public void setCreator(int creator){
        this.creator = creator;
    }
}
