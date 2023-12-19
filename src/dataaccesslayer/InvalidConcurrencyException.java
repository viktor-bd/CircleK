 package dataaccesslayer;

public class InvalidConcurrencyException extends Exception {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidConcurrencyException(Exception e, String explanation) {
		super(explanation, e);
	}
}