package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Customer;
import model.Employee;

public class PersonDB implements PersonDBIF {
	private Connection connection;

	private static final String findCustomerByPhoneNumberQuery = "SELECT person_id, first_name, last_name, phonenumber, zipcode, email, address, isBusiness FROM Person LEFT JOIN Customer ON Customer.customer_id = Person.person_id WHERE phonenumber = ?";
	private static final String findCustomerByOrderIdQuery = "SELECT o.order_id, p.*, c.isBusiness, c.customer_id FROM [Order] AS o INNER JOIN Customer AS c ON o.customer_id = c.customer_id INNER JOIN Person AS p ON c.customer_id = p.person_id WHERE o.order_id = ?";
	private static final String findEmployeeByOrderIdQuery = " SELECT o.order_id, p.*, e.isManager, e.employee_id FROM [Order] AS o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Person AS p ON e.employee_id= p.person_id WHERE o.order_id = ?";
	private PreparedStatement findCustomerByPhoneNumber;
	private PreparedStatement findEmployeeByOrderId;
	private PreparedStatement findCustomerByOrderId;

	public PersonDB() throws DataAccessException {		
		try {
			connection = DBConnection.getInstance().getDBcon();
			findCustomerByPhoneNumber = connection.prepareStatement(findCustomerByPhoneNumberQuery);
			findCustomerByOrderId = connection.prepareStatement(findCustomerByOrderIdQuery);
			findEmployeeByOrderId = connection.prepareStatement(findEmployeeByOrderIdQuery);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}

	public Customer lookUpCustomerInDBWithPhoneNumber(String validPhoneNumber) throws DataAccessException {

		try {
			findCustomerByPhoneNumber.setString(1, validPhoneNumber);
			ResultSet resultSet = findCustomerByPhoneNumber.executeQuery();
			Customer customer = null;
			if (resultSet.next()) {
				customer = buildCustomerObject(resultSet);
				customer.setPhoneNumber(validPhoneNumber);
			}
			return customer;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find customer by phone number = " + validPhoneNumber);
		}

	}

	private Customer buildCustomerObject(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer(resultSet.getInt("person_id"), resultSet.getString("first_name"),
				resultSet.getString("last_name"), resultSet.getString("phonenumber"), resultSet.getString("zipcode"),
				resultSet.getString("email"), resultSet.getString("address"));
		int isBusinessInteger = resultSet.getInt("isBusiness");
		boolean isBusiness = false;
		if (isBusinessInteger == 1) {
			isBusiness = true;
		}
		customer.setIsBusiness(isBusiness);
		return customer;
	}
	
	private Employee buildEmployeeObject(ResultSet resultSet) throws SQLException {
		int isManagerInteger = resultSet.getInt("isManager");
		boolean isManager = false;
		if (isManagerInteger == 1) {
			isManager = true;
		}
		Employee employee = new Employee(resultSet.getInt("person_id"), resultSet.getString("first_name"),
				resultSet.getString("last_name"), resultSet.getString("phonenumber"), resultSet.getString("zipcode"),
				resultSet.getString("email"), resultSet.getString("address"), isManager);
		return employee;
	}
	
	/**
	 * @param customerId
	 * @return
	 */
	public Customer findCustomerWithCustomerId(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getCustomerWithOrderId(int orderId) throws SQLException {			
		return lookUpCustomerInDBWithOrderId(orderId);
	}

	public Employee getEmployeeWithOrderId(int orderId) throws SQLException {
		return lookUpEmployeeInDBWithOrderId(orderId);
	}
	
	private Employee lookUpEmployeeInDBWithOrderId(int orderId) throws SQLException {
		findEmployeeByOrderId.setInt(1, orderId);
		ResultSet resultSet = findEmployeeByOrderId.executeQuery();
		Employee employee = null;
		if (resultSet.next()) {
			employee = buildEmployeeObject(resultSet);
		}
		return employee;
	}

	private Customer lookUpCustomerInDBWithOrderId(int orderId) throws SQLException {	
		findCustomerByOrderId.setInt(1, orderId);
		ResultSet resultSet = findCustomerByOrderId.executeQuery();
		Customer customer = null;
		if (resultSet.next()) {
			customer = buildCustomerObject(resultSet);
			}
		return customer;		
}
}
