package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Type_Of_Expenses")
public class TypeOfExpenses {
    @PrimaryKey(autoGenerate = true)
    private int idTypeOfExpenses;
    private String nameOfExpenses;





    public TypeOfExpenses(String nameOfExpenses) {
        this.nameOfExpenses = nameOfExpenses;
    }

    public int getIdTypeOfExpenses() {
        return idTypeOfExpenses;
    }

    public void setIdTypeOfExpenses(int idTypeOfExpenses) {
        this.idTypeOfExpenses = idTypeOfExpenses;
    }

    public String getNameOfExpenses() {
        return nameOfExpenses;
    }

    public void setNameOfExpenses(String nameOfExpenses) {
        this.nameOfExpenses = nameOfExpenses;
    }
}
