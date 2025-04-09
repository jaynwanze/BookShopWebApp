package com.example.bookshop.service;

import com.example.bookshop.entity.CartItem;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.factory.item.OrderItemFactory;
import com.example.bookshop.entity.OrderItem;
import com.example.bookshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DiscountService discountService;

    public Order placeOrder(Long customerId, String discountCode) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        // Get the customer's shopping cart
        ShoppingCart cart = shoppingCartService.getShoppingCart(customerId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty or not found for customer ID: " + customerId);
        }

        // Get the cart details
        List<CartItem> cartItems = shoppingCartService.getCartItems(cart);
        // Calculate the total price of the cart
        Double total = shoppingCartService.calculateCartTotal(cartItems);

        // Create the order object
        Order order = new Order.Builder().customerId(customerId)
                .total(total)
                .shippingAddress(customer.getShippingAddress())
                .paymentMethod(customer.getPaymentMethod())
                .orderDate(new java.sql.Date(System.currentTimeMillis()))
                .build();

        // Convert cart items to order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = (OrderItem) new OrderItemFactory(order, cartItem.getBook().getPrice())
                            .createItem(cartItem.getBook(), cartItem.getQuantity());
                    // Set the order in the order item:
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .toList();
        // Set the order items in the order
        order.setItems(orderItems);

        // Apply discount if applicable
        double discount = discountService.validateDiscountCode(discountCode);
        if (discount == 0.0) {
            total = total * (1 - discount);
        }

        // Process payment
        boolean paymentSuccess = paymentService.processPayment(order);
        if (paymentSuccess) {
            // Update stock levels for each order item
            stockService.updateStockLevels(orderItems);
            // Persist the order
            orderRepository.save(order);
            // Clear the shopping cart after successful order placement
            shoppingCartService.clearShoppingCart(customerId);

            return order;
        } else {
            // Handle payment failure (e.g., throw an exception or return an error state)
            throw new RuntimeException("Payment processing failed. Order not placed.");
        }
    }

    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findOrdersByCustomerId(customerId);
    }
}
