package com.example.myapplication.Database.Revenue;

import android.content.Context;

import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Revenue;

@Database(entities = {Revenue.class}, version = 1,exportSchema = false)
public abstract class RevenueDtb extends RoomDatabase {
    private static final String DATABASE_REVENUE = "Revenue.db";
    private static RevenueDtb instance;

    public static synchronized RevenueDtb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RevenueDtb.class, DATABASE_REVENUE)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
  public abstract RevenueDAO revenueDAO();


}
