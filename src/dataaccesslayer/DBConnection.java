package dataaccesslayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String driver = "jdbc:sqlserver://localhost:1433";
	private static final String databaseName = ";databaseName=CircleKStock6";
	private static String userName = "; user=sa";
	private static String password = ";password=Password1234";
	private static String encryption = ";encrypt=false";

	private DatabaseMetaData dma;
	private static Connection con;

	private static DBConnection instance = null;

	private DBConnection() {
		String url = driver + databaseName + userName + password + encryption;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("SQLServerDriver loaded succesfully");

		} catch (Exception e) {
			System.err.println("Can not find the driver");
			System.err.println(e.getMessage());
		}
		try {

			con = DriverManager.getConnection(url);

			con.setAutoCommit(true);
			dma = con.getMetaData();
			System.out.println("Connection to " + dma.getURL());
			System.out.println("Driver " + dma.getDriverName());
			System.out.println("Database product name " + dma.getDatabaseProductName());
		} catch (Exception e) {

			System.err.println("Problems with the connection to the database");
			System.err.println(e.getMessage());
			System.err.println(url);
		}
	}

	public static void closeConnection() {
		try {
			con.close();
			instance = null;
			System.err.println("The connection is closed");
		} catch (Exception e) {
			System.err.println("Error trying to close the database " + e.getMessage());
		}
	}

	public Connection getDBcon() {
		return con;
	}

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
			System.err.println("Start transaction failure");
			System.err.println(e.getMessage());
		}
	}

	/*
	 * Commits the transaction
	 */
	public static void commitTransaction() throws SQLException {
		try {
			con.commit();
		} catch (SQLException e) {
			System.err.println("Commit transaction failure");
			System.err.println(e.getMessage());
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
			System.err.println("Rollback transaction failure");
			System.err.println(e.getMessage());
		} finally {
			con.setAutoCommit(true);
		}
	}

	/**
	 * check whether or not the instance is null
	 * 
	 * @return
	 */
	public static boolean instanceIsNull() {
		return (instance == null);
	}

	/**
	 * Returns whether or not connection is close
	 * 
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
