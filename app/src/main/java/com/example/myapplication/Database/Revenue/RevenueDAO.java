package com.example.myapplication.Database.Revenue;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Revenue;

import java.util.List;

@Dao
public interface RevenueDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRevenue(Revenue revenue);
    @Delete
    void deleteRevenue(Revenue revenue);
    @Update
    void updateRevenue(Revenue revenue);
    @Query("SELECT * FROM Revenue")
    List<Revenue> getAllRevenue();
    @Query("SELECT SUM(moneyOfRevenue) FROM Revenue ")
    float AnalysisRevenue();

}
