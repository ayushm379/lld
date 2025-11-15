package io.at.learn.lld04;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

public class Composite {
    public static void main(String[] args) {
        Menu appetizers = Menu.builder()
                .name("Appetizers")
                .item(new MenuItem("French Fries", 70))
                .item(new MenuItem("Garlic Bread", 80))
                .build();

        MenuComponent coke = new MenuItem("Coke", 45);
        Menu beverages = Menu.builder()
                .name("Beverages")
                .item(coke)
                .item(new MenuItem("Mineral Water", 20))
                .build();

        MenuComponent burger = new MenuItem("Burger", 99);
        Menu mainMenu = Menu.builder()
                .name("Zomato Cafe Menu")
                .item(burger)
                .item(beverages)
                .item(appetizers)
                .build();

        mainMenu.print();
        System.out.println("Total Bill: ₹" + mainMenu.getPrice());

        /*
        Structure
            MainMenu
                - Burger
                - Beverages
                    - Coke
                - Appetizers
                    - French Fries
                    - Garlic Bread
         */
    }
}

interface MenuComponent {
    void print();
    double getPrice();
}

@AllArgsConstructor @Getter
class MenuItem implements MenuComponent {
    private String name;
    private double price;

    @Override
    public void print() {
        System.out.printf("Name: %s, price: %f\n", name, price);
        System.out.println("******************");
    }
}

@AllArgsConstructor @Builder
class Menu implements MenuComponent {
    private String name;
    // Every MenuComponent has multiple nested MenuComponents
    @Singular private List<MenuComponent> items;

    @Override
    public void print() {
        System.out.printf("""
            Name: %s,
            items:
        """, name);
        items.forEach(MenuComponent::print);
        System.out.println("******************");
    }

    @Override
    public double getPrice() {
        return items.stream().mapToDouble(MenuComponent::getPrice).sum();
    }
}