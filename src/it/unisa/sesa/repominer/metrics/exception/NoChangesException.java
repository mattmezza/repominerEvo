package it.unisa.sesa.repominer.metrics.exception;

/**
 * This class represents an exception to be thrown when there are not changes
 * stored into the database.
 * 
 * @author RepominerEvo Team
 * 
 */
public class NoChangesException extends Exception {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 4672757507767777371L;

	/**
	 * This method constructs an object of type NoChangesException by setting
	 * the message to a default string value "No changes found into db".
	 */
	public NoChangesException() {
		super("No changes found into db");
	}

}
