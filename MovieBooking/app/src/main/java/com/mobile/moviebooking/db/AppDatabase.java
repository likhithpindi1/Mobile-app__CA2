package com.mobile.moviebooking.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieBook.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
