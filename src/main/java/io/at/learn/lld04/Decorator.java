package io.at.learn.lld04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.atomic.AtomicInteger;

public class Decorator {
    public static void main(String[] args) {
        AtomicInteger orderId = new AtomicInteger(1);

        Chai adrakChai = new AdrakChai();
        serveChai(orderId.getAndIncrement(), "Person 1", adrakChai);

        Chai plainChai = new PlainChai();
        serveChai(orderId.getAndIncrement(), "Person 2", plainChai);

        Chai milkAdrakTulsiChai = new Tulsi(new Milk(new AdrakChai()));
        serveChai(orderId.getAndIncrement(), "Person 3", milkAdrakTulsiChai);

        Chai special = new Tulsi(new Masala(new Elaichi(new Milk(new AdrakChai()))));
        serveChai(orderId.getAndIncrement(), "Person 4", special);
    }

    private static void serveChai(Integer orderId, String customerName, Chai chai) {
        System.out.printf("""
                        ---------------------------------------------
                        Order Id: %d
                        Customer: %s
                        Chai: %s | Bill: Rs. %.2f
                        ---------------------------------------------
                        """, orderId, customerName, chai.description(), chai.getCost());
    }
}

interface Chai {
    double getCost();
    String description();
}

@AllArgsConstructor @Getter
sealed abstract class BaseChai implements Chai permits PlainChai, AdrakChai {
    private String name;
    private double basePrice;

    @Override public double getCost() { return basePrice; }
    @Override public String description() { return name; }

}

final class PlainChai extends BaseChai {
    PlainChai() { super("Plain Chai", 10.0); }
}

final class AdrakChai extends BaseChai {
    AdrakChai() { super("Adrak Chai", 15.0); }
}

@RequiredArgsConstructor
abstract class ChaiDecorator implements Chai {
    private final Chai chai;
    @Override public double getCost() { return chai.getCost(); }
    @Override public String description() { return chai.description(); }
}

sealed abstract class AddOn extends ChaiDecorator permits Milk, Masala, Elaichi, Sugar, Tulsi {
    private final String name;
    private final double price;
    @Override public double getCost() { return super.getCost() + price; }
    @Override public String description() { return super.description() + " + " + name; }

    public AddOn(Chai chai, String name, double price) {
        super(chai);
        this.name = name;
        this.price = price;
    }
}

final class Milk extends AddOn {
    public Milk(Chai chai) { super(chai, "Dudh", 8.5); }
}
final class Masala extends AddOn {
    public Masala(Chai chai) { super(chai, "Masala", 10); }
}
final class Elaichi extends AddOn {
    public Elaichi(Chai chai) { super(chai, "Elaichi", 15); }
}
final class Sugar extends AddOn {
    public Sugar(Chai chai) { super(chai, "Cheeni", 2); }
}
final class Tulsi extends AddOn {
    public Tulsi(Chai chai) { super(chai, "Tulsi", 5.5); }
}
