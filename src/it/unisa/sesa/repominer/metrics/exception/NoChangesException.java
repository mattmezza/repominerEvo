package it.unisa.sesa.repominer.metrics.exception;

public class NoChangesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4672757507767777371L;

	public NoChangesException() {
		super("No changes found into db");
	}
	
}
