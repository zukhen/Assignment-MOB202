package com.example.myapplication.Database.TypeOfExpenses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.TypeOfExpenses;

@Database(entities ={TypeOfExpenses.class},version = 1,exportSchema = false)
public abstract class TOExpensesDtb extends RoomDatabase {
    private static final String DATABASE_TOExpenses = "TOExpenses.db";
    private static  TOExpensesDtb instance;
    public static synchronized TOExpensesDtb getInstance(Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),TOExpensesDtb.class,DATABASE_TOExpenses)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract TOExpensesDAO toExpensesDAO();
}
