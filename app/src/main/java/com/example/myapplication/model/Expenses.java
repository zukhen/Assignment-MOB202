package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Expenses")
public class Expenses {
    @PrimaryKey(autoGenerate = true)
    private int idExpenses;
    private String nameOfExpenses;
    private String dateExpenses;
    private float moneyOfExpenses;
    private String descriptionExpenses;
    private String fullName;
    private int idTypeOfExpenses;

    public Expenses(String nameOfExpenses, String dateExpenses, float moneyOfExpenses, String descriptionExpenses, String fullName, int idTypeOfExpenses) {
        this.nameOfExpenses = nameOfExpenses;
        this.dateExpenses = dateExpenses;
        this.moneyOfExpenses = moneyOfExpenses;
        this.descriptionExpenses = descriptionExpenses;
        this.fullName = fullName;
        this.idTypeOfExpenses = idTypeOfExpenses;
    }

    public int getIdTypeOfExpenses() {
        return idTypeOfExpenses;
    }

    public void setIdTypeOfExpenses(int idTypeOfExpenses) {
        this.idTypeOfExpenses = idTypeOfExpenses;
    }

    public int getIdExpenses() {
        return idExpenses;
    }

    public void setIdExpenses(int idExpenses) {
        this.idExpenses = idExpenses;
    }

    public String getNameOfExpenses() {
        return nameOfExpenses;
    }

    public void setNameOfExpenses(String nameOfExpenses) {
        this.nameOfExpenses = nameOfExpenses;
    }

    public String getDateExpenses() {
        return dateExpenses;
    }

    public void setDateExpenses(String dateExpenses) {
        this.dateExpenses = dateExpenses;
    }

    public float getMoneyOfExpenses() {
        return moneyOfExpenses;
    }

    public void setMoneyOfExpenses(float moneyOfExpenses) {
        this.moneyOfExpenses = moneyOfExpenses;
    }

    public String getDescriptionExpenses() {
        return descriptionExpenses;
    }

    public void setDescriptionExpenses(String descriptionExpenses) {
        this.descriptionExpenses = descriptionExpenses;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
