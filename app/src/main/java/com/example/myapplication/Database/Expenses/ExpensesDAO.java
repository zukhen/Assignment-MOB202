package com.example.myapplication.Database.Expenses;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Expenses;


import java.util.List;

@Dao
public interface ExpensesDAO {
    @Insert
    void insertExpenses(Expenses expenses);
    @Delete
    void deleteExpenses(Expenses expenses);

    @Update
    void updateExpenses(Expenses expenses);

    @Query("SELECT * FROM Expenses")
    List<Expenses> getAllExpenses();

    @Query("SELECT SUM(moneyOfExpenses) FROM Expenses ")
    float AnalysisExpenses();
}
