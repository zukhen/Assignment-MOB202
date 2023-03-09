package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Revenue")
public class Revenue {
    @PrimaryKey(autoGenerate = true)
    private int idRevenue;
    private String nameOfRevenue;
    private String dateRevenue;
    private float moneyOfRevenue;
    private String descriptionRevenue;
    private String fullName;
    private int idTypeOfRevenue;


    public Revenue(String nameOfRevenue, String dateRevenue, float moneyOfRevenue, String descriptionRevenue, String fullName, int idTypeOfRevenue) {
        this.nameOfRevenue = nameOfRevenue;
        this.dateRevenue = dateRevenue;
        this.moneyOfRevenue = moneyOfRevenue;
        this.descriptionRevenue = descriptionRevenue;
        this.fullName = fullName;
        this.idTypeOfRevenue = idTypeOfRevenue;
    }


    public int getIdRevenue() {
        return idRevenue;
    }

    public void setIdRevenue(int idRevenue) {
        this.idRevenue = idRevenue;
    }

    public String getNameOfRevenue() {
        return nameOfRevenue;
    }

    public void setNameOfRevenue(String nameOfRevenue) {
        this.nameOfRevenue = nameOfRevenue;
    }
    public int getIdTypeOfRevenue() {
        return idTypeOfRevenue;
    }

    public void setIdTypeOfRevenue(int idTypeOfRevenue) {
        this.idTypeOfRevenue = idTypeOfRevenue;
    }
    public String getDateRevenue() {
        return dateRevenue;
    }

    public void setDateRevenue(String dateRevenue) {
        this.dateRevenue = dateRevenue;
    }

    public float getMoneyOfRevenue() {
        return moneyOfRevenue;
    }

    public void setMoneyOfRevenue(float moneyOfRevenue) {
        this.moneyOfRevenue = moneyOfRevenue;
    }

    public String getDescriptionRevenue() {
        return descriptionRevenue;
    }

    public void setDescriptionRevenue(String descriptionRevenue) {
        this.descriptionRevenue = descriptionRevenue;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void reve(Revenue revenue) {
    }
}
