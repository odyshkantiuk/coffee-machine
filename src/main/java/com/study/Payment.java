package com.study;

public class Payment {
    private double amountInserted;
    private PaymentType paymentType;

    public Payment(double amountInserted, PaymentType paymentType) {
        this.amountInserted = amountInserted;
        this.paymentType = paymentType;
    }

    public double calculateChange(double price) {
        return amountInserted - price;
    }

    public boolean processPayment(double price) {
        return amountInserted >= price;
    }

    public double getAmountInserted() {
        return amountInserted;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}
