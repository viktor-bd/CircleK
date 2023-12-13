package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer;
public class PersonDB implements PersonDBIF {
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
