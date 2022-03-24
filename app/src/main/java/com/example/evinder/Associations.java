package com.example.evinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"user_id_assoc","event_id_assoc"})
public class Associations {

    @ColumnInfo(name = "user_id_assoc")
    public int user_id_assoc;

    @ColumnInfo(name = "event_id_assoc")
    public int event_id_assoc;

    public Associations(int user_id_assoc, int event_id_assoc){
        this.user_id_assoc = user_id_assoc;
        this.event_id_assoc = event_id_assoc;
    }

    public int getUser_id_assoc(){
        return this.user_id_assoc;
    }

    public void setUser_id_assoc(int user_id_assoc){
        this.user_id_assoc = user_id_assoc;
    }

    public int getEvent_id_assoc(){
        return this.event_id_assoc;
    }

    public void setEvent_id_assoc(int event_id_assoc){
        this.event_id_assoc = event_id_assoc;
    }
}
