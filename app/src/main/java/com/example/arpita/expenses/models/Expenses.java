package com.example.arpita.expenses.models;

import java.util.ArrayList;

/**
 * Created by arpita on 13/07/16.
 */
public class Expenses {

    String name;
    Double amount;
    String date;
    int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }


    public Expenses(String name, Double amount, String date,int id) {

        this.name = name;
        this.amount = amount;
        this.date = date;
        this.id=id;


    }



}
