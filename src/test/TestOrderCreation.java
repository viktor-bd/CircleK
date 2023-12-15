package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Order;
import model.OrderLine;
import model.Customer;
import model.Product;
import model.Ingredient;
import model.Employee;
import control.OrderController;
import control.PersonController;
import dataaccesslayer.DataAccessException;
import dataaccesslayer.PersonDB;
import dataaccesslayer.OrderDB;

public class TestOrderCreation {

	/*@Test
	@DisplayName("S01_TC_01: Given valid input in order, order creation should be successful")
	public void givenValidInputInOrderWillReturnSuccess() {
		// TODO implement test logic
		// Arrange
		int validProductQuantity = 20;
		LocalDate validDate = LocalDate.parse("20-12-2023");
		// TODO detter er en integration test, lav den efter unit tests kører...

		// Act
		// Assert
	}*/

	/*
	 * This test is testing the phone number "88888888" against the database to find
	 * a customer with the given phone number. A successful test result is achieved
	 * if the validPhoneNumber is equal to that of the found customer's phone
	 * number. In the database, phone number is unique.
	 */
	@Test
	@DisplayName("S04_TC_01: Given valid phone number of existing customer, return found customer")
	public void givenValidPhoneNumberWillReturnCustomer() throws DataAccessException {
		// Arrange
		PersonDB personDB = new PersonDB();
		PersonController personController = new PersonController(personDB);
		String validPhoneNumber = "98765430";
		// Act
		Customer foundCustomer = personController.lookUpCustomerInDB(validPhoneNumber);
		System.out.println(foundCustomer.getIsBusiness());
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
		//Arrange
		LocalDateTime testDateNow = LocalDateTime.now();
		LocalDateTime testDatePickUpDate = LocalDateTime.now().plusHours(48);
		System.out.println(testDateNow);
		System.out.println(testDatePickUpDate);
		
		OrderDB orderDB = new OrderDB();
		OrderController orderController = new OrderController();
		
		
		Product testProduct1 = new Product(1001, "Skinke/Ost Sandwich", 45, "Sandwich");

		Ingredient testIngredient1 = new Ingredient(2, "Salat", 150, null);
		Ingredient testIngredient2 = new Ingredient(3, "Ost", 200, null);
		Ingredient testIngredient3 = new Ingredient(5, "Sandwichbræd", 200, null);
		Ingredient testIngredient4 = new Ingredient(8, "Skinke", 200, null);
		Ingredient testIngredient5 = new Ingredient(10, "Mayo", 200, null);
		
		PersonDB personDB = new PersonDB();
		PersonController personController = new PersonController(personDB);
		String validPhoneNumber = "98765430";
		Employee testEmployee = new Employee(1);

		//Act
		// Step 1. OC creates an Order with desired date
		Order newOrder = orderController.createOrder(0, false, testDatePickUpDate, false, null, null); 
		// Step 2. Enter product quantity mimmick UI with hardcoded products
		// Step 3. UI -> OrderController creates OrderLine for each desired product and adds quantity
		OrderLine newOrderLine = orderController.createOrderLine(testProduct1, 2);
		// Step 4. OrderController adds OrderLine(s) to the order
		orderController.addOrderLineToOrder(newOrderLine, newOrder);
		// Step 5. Look up customer 
		Customer foundCustomer = personController.lookUpCustomerInDB(validPhoneNumber);
		//System.out.println(foundCustomer.getIsBusiness());
		// Step 6. Add customer to order
		orderController.addCustomerToOrder(foundCustomer, newOrder);
		orderController.testaddEmployeeToOrder(testEmployee,newOrder);
		// Step 7. Finish order creation (save in db) call count on orders in db ++ on id and set id to result
		Order foundOrder = orderController.saveOrder(newOrder);
		// orderController.saveOrderLines(order.getOrderLines)
		//Assert
	//	assertEquals(foundOrder.getOrderId(), 4);
	//	assertEquals(foundOrder.getCustomer(), foundCustomer);
		
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
