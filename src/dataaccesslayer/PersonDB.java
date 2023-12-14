package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Customer;

public class PersonDB implements PersonDBIF {
	private Connection connection;

	private static final String findCustomerByPhoneNumberQuery = "SELECT person_id, first_name, last_name, phonenumber, zipcode, email, address, isBusiness FROM Person LEFT JOIN Customer ON Customer.customer_id = Person.person_id WHERE phonenumber = ?";

	private PreparedStatement findCustomerByPhoneNumber;
	
	public PersonDB() throws DataAccessException {
		 try {
				findCustomerByPhoneNumber = DBConnection.getInstance().getDBcon().prepareStatement(findCustomerByPhoneNumberQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DataAccessException(e, "Could not prepare statement");
			}
	}
	public Customer lookUpCustomerInDB(String validPhoneNumber) throws DataAccessException {
		
		try {
			findCustomerByPhoneNumber.setString(1, validPhoneNumber);
			ResultSet resultSet = findCustomerByPhoneNumber.executeQuery();
			Customer customer = null;
			if (resultSet.next()) {
				customer = buildObject(resultSet);
				customer.setPhoneNumber(validPhoneNumber);
			}
			return customer;
		}catch (SQLException e) {
			throw new DataAccessException(e, "Could not find customer by phone number = " + validPhoneNumber);
		}
		
	}
	private Customer buildObject(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer(
				resultSet.getInt("person_id"),
				resultSet.getString("firsName"), 
				resultSet.getString("lastName"),
				resultSet.getString("phoneNumber"),
				resultSet.getString("zipcode"),
				resultSet.getString("email"),
				resultSet.getString("address"),
				resultSet.getBoolean("isBusiness")
				);

		System.out.println("Object succesfully created " + customer.getFirstName());
		return customer;
			
}
    

}
