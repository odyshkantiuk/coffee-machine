package com.study;

public class Customer {
    private String name;
    private double money;

    public Customer(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public void selectDrink(CoffeeMachine coffeeMachine, String drinkName) {
        coffeeMachine.selectDrink(drinkName);
    }

    public void insertPayment(CoffeeMachine coffeeMachine, Payment payment) {
        if (payment.getAmountInserted() > money) {
            throw new IllegalArgumentException("Not enough money");
        }
        coffeeMachine.insertPayment(this, payment);
        money -= payment.getAmountInserted();
    }

    public void returnPayment(double amount) {
        this.money += amount;
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public double getMoney() {
        return money;
    }
}
