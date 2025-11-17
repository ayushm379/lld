package io.at.learn.lld04;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

public class Facade {

    public static void main(String[] args) {
        // Dependency Injection will be done by IOC
        FoodDeliveryFacade foodDeliveryFacade = new FoodDeliveryFacade(
                new RestaurantService(),
                new MenuService(),
                new PaymentService(),
                new OrderService(),
                new NotificationSystem(),
                new DeliverySystem()
        );

        // Client calls this directly, instead of all the steps
        try {
            foodDeliveryFacade.placeOrder("Restaurant 1", "Chai", "UPI")
                    .ifPresent(order -> System.out.println("ORDER 1: " + order));

            foodDeliveryFacade.placeOrder("Restaurant 2", "Momos", "UPI")
                    .ifPresent(order -> System.out.println("ORDER 2: " + order));
        } catch (RuntimeException e) {
            System.out.println("ERROR: " +  e.getLocalizedMessage());
        }
    }
}

@RequiredArgsConstructor
class FoodDeliveryFacade {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final NotificationSystem notificationSystem;
    private final DeliverySystem deliverySystem;

    public Optional<Order> placeOrder(String restaurantName, String itemName, String paymentMethod) throws RuntimeException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);
        RestaurantMenu menu = menuService.getMenu(restaurant.restaurantId());
        Optional<Item> itemOptional = menu.findByName(itemName);
        if(itemOptional.isEmpty()) {
            throw new RuntimeException("Item " + itemName + " not present!");
        }
        boolean paid = paymentService.processPayment(140.45, paymentMethod);
        if(paid) {
            Order order = orderService.placeOrder(restaurant, itemOptional.get());
            notificationSystem.sendNotification(order);
            deliverySystem.assignRider();
            return Optional.of(order);
        }
        return Optional.empty();
    }

}

class RestaurantService {
    Restaurant findByName(String name) {
        return new Restaurant(1, "Restaurant 001");
    }
}

class MenuService {
    RestaurantMenu getMenu(int restaurantId) {
        return new RestaurantMenu(List.of(
                new Item("Chai", 10),
                new Item("Coffee", 20)
        ));
    }
}

class PaymentService {
    boolean processPayment(double amount, String method) {
        System.out.printf("Processing Payment ->\n Amount: %.2f via %s \n", amount, method);
        return true;
    }
}

class OrderService {
    Order placeOrder(Restaurant restaurant, Item item) {
        return new Order(restaurant.restaurantId(), 1, item);
    }
}

class NotificationSystem {
    void sendNotification(Order order) {
        System.out.printf("Order ->\n OrderId : %s\n", order.OrderId());
    }
}

class DeliverySystem {
    void assignRider() {
        System.out.println("Assigning Rider");
    }
}

record Restaurant(int restaurantId, String name) {}
record RestaurantMenu(List<Item> itemList) {
    public Optional<Item> findByName(String name) {
        if(name == null || name.isBlank()) {
            return Optional.empty();
        }
        return itemList.stream()
                .filter(item -> item.name().equalsIgnoreCase(name))
                .findFirst();
    }
}
record Order(int restaurantId, int OrderId, Item item) {}
record Item(String name, double prices) {}