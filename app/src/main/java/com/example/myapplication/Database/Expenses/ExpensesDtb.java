package com.example.myapplication.Database.Expenses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Expenses;

@Database(entities = {Expenses.class}, version = 1,exportSchema = false)
public abstract class ExpensesDtb extends RoomDatabase {
    private static final String DATABASE_EXPENSES = "Expenses.db";
    private static ExpensesDtb instance;

    public static synchronized ExpensesDtb getInstance(Context context)
    {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ExpensesDtb.class, DATABASE_EXPENSES)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract ExpensesDAO expensesDAO();

}
