package com.example.hatansaad.popularmoviesapp;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = Movie.class, version = 1, exportSchema = false)

public abstract class Database extends RoomDatabase {

    public abstract  MovieDAO moviesDAO ();

    private static Database database;

    public static Database getDatabase (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context, Database.class, "Favorite_Movie.db").fallbackToDestructiveMigration().build();
        }

        return database;
    }
}
