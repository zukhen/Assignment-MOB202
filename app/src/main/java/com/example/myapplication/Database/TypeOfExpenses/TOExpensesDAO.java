package com.example.myapplication.Database.TypeOfExpenses;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.TypeOfExpenses;

import java.util.List;

@Dao
public interface TOExpensesDAO {
    @Insert
    void insertTOExpenses(TypeOfExpenses typeOfExpenses);

    @Delete
    void deleteTOExpenses(TypeOfExpenses typeOfExpenses);

    @Update
    void updateTOExpenses(TypeOfExpenses typeOfExpenses);

    @Query("SELECT * FROM Type_Of_Expenses")
    List<TypeOfExpenses> getAllTOExpenses();

    @Query("SELECT nameOfExpenses FROM Type_Of_Expenses")
    List<String> getAllNameTypeOfExpenses();
}
