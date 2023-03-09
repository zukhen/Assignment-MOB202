package com.example.myapplication.Database.TypeOfRevenue;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.TypeOfRevenue;

import java.util.List;

@Dao
public interface TypeOfRevenueDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTypeOfRevenue(TypeOfRevenue typeOfRevenue);

    @Delete
    void deleteTypeOfRevenue(TypeOfRevenue typeOfRevenue);

    @Update
    void updateTypeOfRevenue(TypeOfRevenue typeOfRevenue);

    @Query("SELECT * FROM Type_Of_Revenue")
    List<TypeOfRevenue> getAllTypeOfRevenue();

    @Query("SELECT nameOfRevenue FROM Type_Of_Revenue")
    List<String> getAllNameTypeOfRevenue();
}
