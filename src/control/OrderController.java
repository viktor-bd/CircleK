package control;

import dataaccesslayer.OrderDB;
import model.*;

import java.time.LocalDateTime;

public class OrderController {


    private OrderDB orderDB;


    public OrderController() throws DataAccessException {
        this.orderDB = new OrderDB();
    }

    public Order createOrder(int orderID, boolean pickUpStatus, LocalDateTime pickupDate, boolean isPaid, Customer customer, Employee employee) {
        Order newOrder = new Order(orderID, pickUpStatus, pickupDate, isPaid, customer, employee);
        return newOrder;
    }

    public Order saveOrder(Order order) {
        orderDB.saveOrder(order); // Call orderDAL to save the order
        return order;
    }

    public Order addOrderLineToOrder(OrderLine orderLine, Order order) {
        order.addOrderLine(orderLine);
        return order;
    }

    public OrderLine createOrderLine(Product product, int quantity) {
        OrderLine orderLine = new OrderLine(quantity, product);
        return orderLine;
    }

    //TODO orderID skal hentes fra DB via GUI kald til CTRL som går til DAL
    public void confirmOrder(Order order) {
        // Logic for confirming an order
        order.setPickUpStatus(true);
        // Add logic to update the order directly in the database or perform necessary operations
    }

	public void addCustomerToOrder(Customer customer, Order order) {
		// TODO mangler lige logic skal nok skrive det - Viktor
		order.setCustomer(customer);
	}

	}

}
