package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import model.Customer;
import control.PersonController;
import dataaccesslayer.DataAccessException;
import dataaccesslayer.PersonDB;

public class TestOrderCreation {

	@Test
	@DisplayName("S01_TC_01: Given valid input in order, order creation should be successful")
	public void givenValidInputInOrderWillReturnSuccess() {
		// TODO implement test logic
		//Arrange
		int validProductQuantity = 20;
		LocalDate validDate = LocalDate.parse("20-12-2023");
		//TODO detter er en integration test, lav den efter unit tests kører...
		
		//Act
		//Assert
	}
	
	@Test
	@DisplayName("--: Given valid existing customer, return found customer")
	public void givenValidPhoneNumberWillReturnCustomer() throws DataAccessException {
		//Arrange
		PersonDB personDB = new PersonDB();
		PersonController personController = new PersonController(personDB);
		String validPhoneNumber = "88888888";
		// Act
		//TODO implement lookUpCustomerInDB in OrderController
		Customer foundCustomer = personController.lookUpCustomerInDB(validPhoneNumber);
		//Assert
		assertEquals(foundCustomer.getPhoneNumber(), validPhoneNumber);
		
	}

	@Test
	@DisplayName("S01_TC_02: Given invalid customer ID, order creation should return error")
	public void givenInvalidCustomIDWillReturnError() {

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
