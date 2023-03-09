package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Type_Of_Revenue")
public class TypeOfRevenue {
    @PrimaryKey(autoGenerate = true)
    private int idTypeOfRevenue;
    private String nameOfRevenue;
    public int getIdTypeOfRevenue() {
        return idTypeOfRevenue;
    }

    public void setIdTypeOfRevenue(int idTypeOfRevenue) {
        this.idTypeOfRevenue = idTypeOfRevenue;
    }

    public TypeOfRevenue( String nameOfRevenue) {
        this.nameOfRevenue = nameOfRevenue;
    }



    public String getNameOfRevenue() {
        return nameOfRevenue;
    }

    public void setNameOfRevenue(String nameOfRevenue) {
        this.nameOfRevenue = nameOfRevenue;
    }
}
