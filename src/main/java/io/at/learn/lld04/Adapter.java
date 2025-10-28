package io.at.learn.lld04;

import java.util.Random;

public class Adapter {

    public static void main(String[] args) {
        LegacyPaymentProcessorAdapter adapter = new LegacyPaymentProcessorAdapter(new LegacyPaymentSystem());
        CheckoutService checkoutService = new CheckoutService(adapter);
        checkoutService.checkout("Customer 1", 54);
    }

}

class CheckoutService {
    private final PaymentProcessor paymentProcessor;
    public CheckoutService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    public void checkout(String customerId, double total) {
        System.out.printf("Checkout initiated for %s | Total: $%s%n", customerId, total);
        paymentProcessor.processPayment(customerId, total);
        System.out.println("Payment successful!");
    }
}

interface PaymentProcessor {
    void processPayment(String customerId, double amountInDollars);
}

class LegacyPaymentSystem {
    public void makePayment(String transactionId, String accountNumber, double amountInCents) {
        System.out.printf("%s : %s -> %.2f cents \n", transactionId, accountNumber, amountInCents);
    }
}

class LegacyPaymentProcessorAdapter implements PaymentProcessor {

    private final LegacyPaymentSystem legacyPaymentSystem;

    public LegacyPaymentProcessorAdapter(LegacyPaymentSystem legacyPaymentSystem) {
        this.legacyPaymentSystem = legacyPaymentSystem;
    }

    private double convertDollarsToCents(double amountInDollars) {
        return amountInDollars * 100;
    }

    @Override
    public void processPayment(String customerId, double amountInDollars) {
        double amountInCents = convertDollarsToCents(amountInDollars);
        String transactionId = "TSX" + (new Random()).nextInt();
        legacyPaymentSystem.makePayment(transactionId, customerId, amountInCents);
    }

}