package com.example.kursw.model;

import android.graphics.drawable.Drawable;

public class Kurs {
    private String name;
    private String symbol;
    private double valueSell;
    private double valueBuy;
    private Drawable flag;

    public Kurs(String name, String symbol, double valueSell, double valueBuy, Drawable flag) {
        this.name = name;
        this.symbol = symbol;
        this.valueSell = valueSell;
        this.valueBuy = valueBuy;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getValueSell() {
        return valueSell;
    }

    public double getValueBuy() {
        return valueBuy;
    }

    public Drawable getFlag() {
        return flag;
    }
}
