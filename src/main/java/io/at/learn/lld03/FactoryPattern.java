package io.at.learn.lld03;

import java.util.Objects;
import java.util.Scanner;

public class FactoryPattern {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Processor to use : ");
        String processorName = sc.nextLine();

        try {
            ProcessorType processorType = ProcessorType.getProcessorType(processorName);
            PaymentProcessor paymentProcessor = PaymentProcessorFactory.getPaymentProcessor(processorType);
            System.out.println("SELECTED PROCESSOR TYPE : " + paymentProcessor.getProcessorName());

            System.out.print("Enter the amount to process : ");
            double amount = sc.nextDouble();
            paymentProcessor.processPayment(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

interface PaymentProcessor {
    void processPayment(double amount);
    String getProcessorName();
}

enum ProcessorType {
    CREDIT_CARD_PROCESSOR("CREDIT_CARD_PROCESSOR"),
    DEBIT_CARD_PROCESSOR("DEBIT_CARD_PROCESSOR"),
    PAYTM_PROCESSOR("PAYTM_PROCESSOR");

    private final String name;
    ProcessorType(String name) {
        this.name = name;
    }

    static ProcessorType getProcessorType(String processorName) {
        for(var processorType: ProcessorType.values()) {
            if(processorType.name.equalsIgnoreCase(processorName)) {
                return processorType;
            }
        }
        return null;
    }

}

class PaymentProcessorFactory {
    public static PaymentProcessor getPaymentProcessor(ProcessorType processorType) {
        if(Objects.isNull(processorType)) {
            throw new IllegalArgumentException("Payment type is invalid");
        }
        return switch (processorType) {
            case CREDIT_CARD_PROCESSOR -> new CreditCardProcessor();
            case DEBIT_CARD_PROCESSOR -> new DebitCardProcessor();
            case PAYTM_PROCESSOR -> new PaytmProcessor();
            default -> throw new IllegalArgumentException("Invalid payment type : " + processorType);
        };

    }
}

class CreditCardProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing Credit Card payment of $%.2f%n", amount);
    }

    @Override
    public String getProcessorName() {
        return "Credit Card";
    }

}

class DebitCardProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing Debit Card payment of $%.2f%n", amount);
    }

    @Override
    public String getProcessorName() {
        return "Debit Card";
    }

}

class PaytmProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing PayPal payment of $%.2f%n", amount);
    }

    @Override
    public String getProcessorName() {
        return "Paytm";
    }

}