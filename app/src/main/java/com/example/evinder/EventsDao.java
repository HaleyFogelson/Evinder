package com.example.evinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface EventsDao {
    @Query("SELECT * FROM Events")
    List<Events> getAllEvents();

    @Query("SELECT * FROM Events WHERE event_id=:event_id")
    Events getEventById(int event_id);

    @Query("SELECT * FROM Events WHERE name=:name")
    List<Events> getEventsByName(String name);

    @Insert
    long insert(Events event);

    @Update
    public void update(Events event);

    @Delete
    void delete(Events event);
}