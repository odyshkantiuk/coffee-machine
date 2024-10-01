package com.study;

public class Transaction {
    private Drink drink;
    private double amountPaid;
    private double changeReturned;

    public Transaction(Drink drink, double amountPaid, double changeReturned) {
        this.drink = drink;
        this.amountPaid = amountPaid;
        this.changeReturned = changeReturned;
    }

    public Drink getDrink() {
        return drink;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getChangeReturned() {
        return changeReturned;
    }
}
