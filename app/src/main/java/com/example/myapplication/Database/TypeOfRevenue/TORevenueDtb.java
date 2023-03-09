package com.example.myapplication.Database.TypeOfRevenue;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.TypeOfRevenue;

@Database(entities = {TypeOfRevenue.class},version = 1,exportSchema = false)
public abstract class TORevenueDtb extends RoomDatabase {
    private static final String DATABASE_TORevenue = "TORevenue.db";
    private static TORevenueDtb instance;

    public static synchronized TORevenueDtb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TORevenueDtb.class, DATABASE_TORevenue)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract TypeOfRevenueDAO typeOfRevenueDAO();
}
