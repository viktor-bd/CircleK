package control;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.OrderDB;
import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderController {


    private OrderDB orderDB;


    public OrderController() throws DataAccessException {
        this.orderDB = new OrderDB();
    }

    public Order createOrder(int orderID, boolean pickUpStatus, LocalDateTime pickupDate, boolean isPaid, Customer customer, Employee employee) {
        Order newOrder = new Order(orderID, pickUpStatus, pickupDate, isPaid, customer, employee);
        return newOrder;
    }

    public Order saveOrder(Order order) throws SQLException {
    	System.out.println("OC SaveOrder 27 " +order.getSizeOfOrderLines());
        orderDB.saveOrder(order); // Call orderDB to save the order
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

	public void confirmOrder(Order order) {
		order.setPickUpStatus(true);
	}

	public void addCustomerToOrder(Customer customer, Order order) {
		// TODO mangler lige logic skal nok skrive det - Viktor
		order.setCustomer(customer);
	}

	public void testaddEmployeeToOrder(Employee testEmployee , Order newOrder) {
		// TODO Auto-generated method stub
		newOrder.setEmployee(testEmployee);
	}

}
