package com.study;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        MaintenanceWorker worker1 = new MaintenanceWorker("1", AccessLevel.HIGH);
        MaintenanceWorker worker2 = new MaintenanceWorker("2", AccessLevel.MEDIUM);
        MaintenanceWorker worker3 = new MaintenanceWorker("2", AccessLevel.LOW);
        Customer customer = new Customer("1", 20);
        List<Drink> drinks = new ArrayList<>();
        drinks.add(new Drink("Coffee", 10, 10));
        drinks.add(new Drink("Tea", 10, 10));
        drinks.add(new Drink("Juice", 10, 10));
        drinks.add(new Drink("Water", 10, 10));
        worker3.refillMachine(machine, drinks);
        worker3.refillMachine(machine, 1000);
        customer.selectDrink(machine, "Coffee");
        customer.insertPayment(machine, new Payment(10, PaymentType.CASH));
    }
}
