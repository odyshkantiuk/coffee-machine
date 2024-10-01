package com.study;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {
    private Status status;
    private List<Drink> availableDrinks;
    private double availableCash;
    private List<Transaction> transactions;
    private Drink order;

    public CoffeeMachine() {
        this.status = Status.WORKING;
        this.availableDrinks = new ArrayList<>();
        this.availableCash = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void insertPayment(Customer customer, Payment payment) {
        if (status != Status.WORKING) {
            System.out.println("Machine is out of service. Please try later");
            return;
        }
        availableCash += payment.getAmountInserted();
        System.out.println("Payment of " + payment.getAmountInserted() + " received");
        dispenseDrink(customer, order, payment);
    }

    private void dispenseDrink(Customer customer, Drink drink, Payment payment) {
        if (drink.isAvailable()) {
            if (payment.processPayment(drink.getPrice())) {
                System.out.println("Preparing: " + drink.getName());
                drink.dispense();
                double change = payment.calculateChange(drink.getPrice());
                returnChange(customer, change);
                Transaction transaction = new Transaction(drink, payment.getAmountInserted(), change);
                logTransaction(transaction);
                System.out.println("Ready: " + drink.getName());
            } else {
                System.out.println("Insufficient payment! Drink costs: " + drink.getPrice());
                customer.returnPayment(payment.getAmountInserted());
                availableCash -= payment.getAmountInserted();
            }
        } else {
            System.out.println("Selected drink is out of stock");
        }
    }

    public void setStatus(Object obj, Status status) {
        if (obj instanceof MaintenanceWorker maintenanceWorker) {
            AccessLevel accessLevel = maintenanceWorker.getAccessLevel();
            if (accessLevel == AccessLevel.MEDIUM || accessLevel == AccessLevel.HIGH) {
                this.status = status;
                System.out.println("Machine status changed to: " + status);
            } else {
                System.out.println("This can do only by Maintenance Worker with MEDIUM or HIGH access");
            }
        } else {
            System.out.println("This can do only by Maintenance Worker");
        }
    }

    public Status checkStatus(Object obj) {
        if (obj instanceof MaintenanceWorker maintenanceWorker) {
            AccessLevel accessLevel = maintenanceWorker.getAccessLevel();
            for (Drink drink : availableDrinks) {
                if (drink.getQuantity() > 0) {
                    status = Status.WORKING;
                    return status;
                }
            }
            status = Status.OUT_OF_SERVICE;
            return status;
        } else {
            System.out.println("This can do only by Maintenance Worker");
            return null;
        }
    }

    public void selectDrink(String drinkName) {
        if (status != Status.WORKING) {
            System.out.println("Machine is out of service. Please try later");
            return;
        }
        for (Drink drink : availableDrinks) {
            if (drink.getName().equalsIgnoreCase(drinkName)) {
                order = drink;
                return;
            }
        }
        System.out.println("Drink not found: " + drinkName);
    }

    public void returnChange(Customer customer, double change) {
        if (change > 0) {
            System.out.println("Returning change: " + change);
            availableCash -= change;
            customer.returnPayment(change);
        }
    }

    public void refillStock(Object obj, List<Drink> drinks) {
        if (obj instanceof MaintenanceWorker) {
            for (Drink newDrink : drinks) {
                boolean found = false;
                for (Drink existingDrink : availableDrinks) {
                    if (existingDrink.getName().equals(newDrink.getName()) && existingDrink.getPrice() == newDrink.getPrice()) {
                        existingDrink.setQuantity(existingDrink.getQuantity() + newDrink.getQuantity());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    availableDrinks.add(newDrink);
                }
            }
            System.out.println("Refilled stock for: " + drinks);
        } else {
            System.out.println("This can only be done by a Maintenance Worker");
        }
    }

    public void refillStock(Object obj, double cash) {
        if (obj instanceof MaintenanceWorker) {
            availableCash += cash;
            System.out.println("Refilled stock for: " + cash);
        } else {
            System.out.println("This can only be done by a Maintenance Worker");
        }
    }

    public double getMoney(Object obj) {
        if (obj instanceof MaintenanceWorker maintenanceWorker) {
            AccessLevel accessLevel = maintenanceWorker.getAccessLevel();
            if (accessLevel == AccessLevel.HIGH) {
                System.out.println("Total cash available: " + availableCash);
                double cash = availableCash;
                availableCash = 0.0;
                return cash;
            } else {
                System.out.println("This can do only by Maintenance Worker with HIGH access");
                return 0.0;
            }
        } else {
            System.out.println("This can do only by Maintenance Worker");
            return 0.0;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void logTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction logged: " + transaction.getDrink().getName() + ", Amount Paid: " + transaction.getAmountPaid() + ", Change Returned: " + transaction.getChangeReturned());
    }

    public List<Transaction> getTransactions(Object obj) {
        if (obj instanceof MaintenanceWorker) {
            return transactions;
        } else {
            System.out.println("This can only be done by a Maintenance Worker");
            return null;
        }
    }
}
