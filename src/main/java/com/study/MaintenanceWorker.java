package com.study;

import java.util.List;

public class MaintenanceWorker {
    private String workerID;
    private AccessLevel accessLevel;
    private double money;

    public MaintenanceWorker(String workerID, AccessLevel accessLevel) {
        this.workerID = workerID;
        this.accessLevel = accessLevel;
    }

    public void refillMachine(CoffeeMachine machine, List<Drink> drinks) {
        machine.refillStock(this, drinks);
    }

    public void refillMachine(CoffeeMachine machine, double cash) {
        machine.refillStock(this, cash);
    }

    public void performMaintenance(CoffeeMachine machine) {
        if (machine.checkStatus(this) == Status.OUT_OF_SERVICE) {
            machine.setStatus(this, Status.WORKING);
        } else {
            System.out.println("Machine is already working");
        }
    }

    public void getMoneyFromMachine(CoffeeMachine machine) {
        this.money += machine.getMoney(this);
        System.out.println("Maintenance worker " + workerID + " has " + money + " money");
    }


    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
