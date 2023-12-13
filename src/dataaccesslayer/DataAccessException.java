package dataaccesslayer;

public class DataAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException(Exception e, String explanation) {
		super(explanation, e);
	}
}
