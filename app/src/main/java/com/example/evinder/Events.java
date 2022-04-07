package com.example.evinder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Users.class,
        parentColumns = "user_id",
        childColumns = "creator",
        onDelete = ForeignKey.CASCADE)
})
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

    @ColumnInfo(name = "eventPic")
    public String eventPic;

    @ColumnInfo(name = "location")
    public String location;

    public Events(int event_id, String name, String description, long date, int creator, String eventPic, String location){
        this.event_id = event_id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.creator = creator;
        this.eventPic = eventPic;
        this.location = location;
    }

    @Ignore
    public Events(String name, String description, long date, int creator, String eventPic){
        this.name = name;
        this.description = description;
        this.date = date;
        this.creator = creator;
        this.eventPic = eventPic;
        this.location = "";
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

    public String getEventPic(){
        return this.eventPic;
    }

    public void setEventPic(String eventPic){
        this.eventPic = eventPic;
    }

    public String getLocation(){
      return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String toString() {
        String sb = "";

        sb = this.name;
        sb += ", created by "+this.creator;
        sb += ", with picture : "+this.eventPic;
        return sb;
    }
}
