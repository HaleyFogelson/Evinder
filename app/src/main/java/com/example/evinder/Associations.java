package com.example.evinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"user_id_assoc","event_id_assoc"},
        foreignKeys = {
                @ForeignKey(entity = Users.class,
                            parentColumns = "user_id",
                            childColumns = "user_id_assoc",
                            onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Events.class,
                        parentColumns = "event_id",
                        childColumns = "event_id_assoc",
                        onDelete = ForeignKey.CASCADE)
        }
)
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
