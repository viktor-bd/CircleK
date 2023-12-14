package dataaccesslayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { // Constants used to get access to the database
							// SQL Server
	private static final String driver = "jdbc:sqlserver://localhost:1433";       
	private static final String databaseName = ";databaseName=CircleKStock6"; //TODO Insert correct name
	private static String userName = "; user=sa";								  //TODO Insert correct name
	private static String password = ";password=Password1234";					  //TODO Insert correct PW
	private static String encryption = ";encrypt=false";

	private DatabaseMetaData dma;
	private static Connection con;
	// an instance of the class is generated
	private static DBConnection instance = null;

	// the constructor is private to ensure that only one object of this class is
	// created
	private DBConnection() {
		String url = driver + databaseName + userName + password + encryption;

		try {
			// Loads the driver
			// SQL Server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Load af class ok");

		} catch (Exception e) {
			System.out.println("Can not find the driver");
			System.out.println(e.getMessage());
		} // end catch
		try {
			// connection to the database

			con = DriverManager.getConnection(url);
			// set autocommit
			con.setAutoCommit(true);
			dma = con.getMetaData(); // get meta data
			System.out.println("Connection to " + dma.getURL());
			System.out.println("Driver " + dma.getDriverName());
			System.out.println("Database product name " + dma.getDatabaseProductName());
		} // end try
		catch (Exception e) {

			System.out.println("Problems with the connection to the database");
			System.out.println(e.getMessage());
			System.out.println(url);
		} // end catch
	}// end constructor

	// closeDb: closes the connection to the database

	public static void closeConnection() {
		try {
			con.close();
			instance = null;
			System.out.println("The connection is closed");
		} catch (Exception e) {
			System.out.println("Error trying to close the database " + e.getMessage());
		}
	}// end closeDB
	
	
	// getDBcon: Get-method, returns the connection to the database
	public Connection getDBcon() {
		return con;
	}

	// this method is used to get the instance of the connection
	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	/*
	 * Transaction handling
	 */
	public static void startTransaction() {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Start transaction failure");
			System.out.println(e.getMessage());
		}
	}
	/*
	 * Commits the transaction
	 */
	public static void commitTransaction() throws SQLException {
		try {
			con.commit();
		} catch (SQLException e) {
			System.out.println("Commit transaction failure");
			System.out.println(e.getMessage());
		} finally {
			con.setAutoCommit(true);
		}
	}
	/*
	 * Rolls back the committed code
	 */
	public static void rollbackTransaction() throws SQLException {
		try {
			con.rollback();
		} catch (Exception e) {
			System.out.println("Rollback transaction failure");
			System.out.println(e.getMessage());
		} finally {
			con.setAutoCommit(true);
		}
	}
	/**
	 * check whether or not the instance is null
	 * @return
	 */
	public static boolean instanceIsNull() {
		return (instance == null);
	}
	/**
	 * Returns whether or not connection is close
	 * @return
	 */
	 public static boolean getOpenStatus() {
    	boolean isOpen = false;
    	try {
    		isOpen = (!con.isClosed());
    	} catch (Exception sclExc) {
    		isOpen = false;
    	}
    	return isOpen;
    }
}
