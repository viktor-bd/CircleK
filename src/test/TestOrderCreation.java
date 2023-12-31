package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.Order;
import model.OrderLine;
import model.Customer;
import model.Product;
import model.Employee;

import control.OrderController;
import control.PersonController;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.InvalidConcurrencyException;

public class TestOrderCreation {
	/*
	 * This test is testing a valid order and its confirmation process.
	 * A successful test result is achieved if the order successfully is set to confirmed
	 * and persisted in the database.
	 */
	@Test
	@DisplayName("S?_TC?: Given order will update order confirmation status")
	public void givenValidOrderWillSetConfirmedTrueShouldReturnOrder() throws DataAccessException, SQLException, InvalidConcurrencyException {
		// Arrange
		OrderController orderController = new OrderController();
		Order foundOrder = orderController.getOrderWithOrderId(4);
		Customer foundCustomer = orderController.getCustomerFromOrderId(foundOrder.getOrderId());
		foundOrder.setCustomer(foundCustomer);
		Employee foundEmployee = orderController.getEmployeeFromOrderId(foundOrder.getOrderId());
		foundOrder.setEmployee(foundEmployee);	
		
		// Act
		orderController.updateOrderToConfirmed(foundOrder);	
		Order updatedOrder = orderController.getOrderWithOrderId(4);
				
		// Assert		
		assertEquals(true, updatedOrder.isConfirmed());

	}
	/**
	 * This test verifies that given an order ID, the system retrieves the associated order
	 * from the database and returns the correct number of order lines. The order lines are
	 * part of the order's composition, and the test checks if the size of the order lines
	 * matches the expected value.
	 *
	 * Test Steps:
	 * 1. Arrange: Create an instance of OrderController.
	 * 2. Act: Retrieve the order with the specified order ID.
	 * 3. Assert: Verify that the number of order lines on the found order matches the expected value.
	 *
	 * Test Case ID: S?_TC?: Given order ID should return Order object with order lines
	 *
	 * Test Data:
	 * - Order ID: 6
	 *
	 * Expected Result:
	 * The test expects that the retrieved order has 2 order lines.
	 */
	@Test
	@DisplayName("S?_TC?: Given order id should return Order object with order lines")
	public void givenOrderIdShouldReturnOrderLinesFromOrder() throws DataAccessException {
		// Arrange
		OrderController orderController = new OrderController();
		
		// Act
		Order foundOrder = orderController.getOrderWithOrderId(4);
		int amountOfOrderLinesOnFoundOrder = (foundOrder.getSizeOfOrderLines());
		
		// Assert
		assertEquals(2, amountOfOrderLinesOnFoundOrder);
	
	}

	/*
	 * This test is testing the phone number "98765430" against the database to find
	 * a customer with the given phone number. A successful test result is achieved
	 * if the validPhoneNumber is equal to that of the found customer's phone
	 * number. In the database, phone number is unique.
	 */
	@Test
	@DisplayName("S04_TC_01: Given valid phone number of existing customer, return found customer")
	public void givenValidPhoneNumberWillReturnCustomer() throws DataAccessException {
		// Arrange
		PersonController personController = new PersonController();
		String validPhoneNumber = "98765430";

		// Act
		Customer foundCustomer = personController.lookUpCustomerInDB(validPhoneNumber);

		// Assert
		assertEquals(foundCustomer.getPhoneNumber(), validPhoneNumber);
	}

	@Test
	@DisplayName("S01_TC_02: Given invalid customer ID, order creation should return error")
	public void givenInvalidCustomIDWillReturnError() {

	}

	@Test
	@DisplayName("S01_TC_01: Given valid inputs in order creation will return success")
	public void givenValidInputInOrderWillReturnSuccess() throws DataAccessException, SQLException {
		// Arrange
		LocalDateTime testDatePickUpDate = LocalDateTime.now().plusHours(48);
		OrderController orderController = new OrderController();
		Product testProduct1 = new Product(1001, "Skinke/Ost Sandwich", 45, "Sandwich");
		Product testProduct2 = new Product(1004, "Smurt Rundstykke m/ ost", 22, "rundstykke");
		PersonController personController = new PersonController();
		String validPhoneNumber = "98765430";
		Employee testEmployee = new Employee(1);
		Order newOrder = orderController.createOrder(0, false, testDatePickUpDate, false, null, null);
		OrderLine newOrderLine = orderController.createOrderLine(testProduct1, 2);
		OrderLine newOrderLine2 = orderController.createOrderLine(testProduct2, 2);
		orderController.addOrderLineToOrder(newOrderLine, newOrder);
		orderController.addOrderLineToOrder(newOrderLine2, newOrder);
		Customer foundCustomer = personController.lookUpCustomerInDB(validPhoneNumber);
		orderController.addCustomerToOrder(foundCustomer, newOrder);
		orderController.addEmployeeToOrder(testEmployee, newOrder);

		// Act
		Order foundOrder = orderController.saveOrder(newOrder);

		// Assert
		assertTrue(foundOrder.getOrderId() > 0);
	}

	@Test
	@DisplayName("S02_TC_01: Given date too close to current date, order creation should return error")
	public void givenDateTooCloseToCurrentDateWillReturnError() {

	}

	@Test
	@DisplayName("S03_TC_01: Given invalid product quantity, order creation should return error")
	public void givenInvalidProductQuantityInputWillReturnError() {
		// TODO Implement test code here
	}

	@Test
	@DisplayName("S05_TC_01: Given customer ID that already exists, order creation should return error")
	public void givenCustomerIDThatAlreadyExistWillReturnError() {
		// TODO Implement test code here
	}

}
