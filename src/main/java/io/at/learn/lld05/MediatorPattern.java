package io.at.learn.lld05;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class MediatorPattern {
    public static void main(String[] args) {
        OrderProcessingMediator orderMediator = new OrderProcessingMediator();
        OrderService orderService = new OrderService(orderMediator);
        PaymentService paymentService = new PaymentService(orderMediator);
        InventoryService inventoryService = new InventoryService(orderMediator);
        NotificationService notificationService = new NotificationService(orderMediator);

        orderMediator.setOrderService(orderService);
        orderMediator.setPaymentService(paymentService);
        orderMediator.setInventoryService(inventoryService);
        orderMediator.setNotificationService(notificationService);

        orderService.createOrder();
    }
}

interface OrderMediator {
    void notify(Object sender, String event);
}

@RequiredArgsConstructor
abstract class ServiceComponent {
    protected final OrderMediator mediator;
}

class OrderService extends ServiceComponent {

    public OrderService(OrderMediator mediator) {
        super(mediator);
    }

    public void createOrder() {
        System.out.println("Order Created");
        mediator.notify(this, "ORDER_CREATED");
    }

}

class PaymentService extends ServiceComponent {

    public PaymentService(OrderMediator mediator) {
        super(mediator);
    }

    public void processPayment() {
        System.out.println("Payment Processed");
        mediator.notify(this, "PAYMENT_SUCCESS");
    }

}

class InventoryService extends ServiceComponent {

    public InventoryService(OrderMediator mediator) {
        super(mediator);
    }

    public void stockReserved() {
        System.out.println("Stock Reserved");
        mediator.notify(this, "STOCK_RESERVED");
    }

}

class NotificationService extends ServiceComponent {

    public NotificationService(OrderMediator mediator) {
        super(mediator);
    }

    public void sendNotification() {
        System.out.println("Sent Notification");
    }

}

@Setter
class OrderProcessingMediator implements OrderMediator {
    private OrderService orderService;
    private PaymentService paymentService;
    private InventoryService inventoryService;
    private NotificationService notificationService;

    @Override
    public void notify(Object sender, String event) {
        switch (event) {
            case "ORDER_CREATED" -> paymentService.processPayment();
            case "PAYMENT_SUCCESS" -> inventoryService.stockReserved();
            case "STOCK_RESERVED" -> notificationService.sendNotification();
        }
    }
}