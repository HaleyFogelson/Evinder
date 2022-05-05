package com.example.evinder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Users.class, Events.class, Associations.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String PRELOADED_DATABASE_FILE = "data.db";
    private static final String DB_NAME = "data.db";
    private static volatile AppDatabase instance;

    public abstract UsersDao usersDao();
    public abstract EventsDao eventsDao();
    public abstract AssociationsDao associationsDao();

   // private RegisterDataSource dataSource;


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .createFromAsset(PRELOADED_DATABASE_FILE)
                .allowMainThreadQueries()
                .build();
    }
}