package it.unisa.sesa.repominer.preferences.exceptions;

/**
 * 
 * @author Matt
 * 
 */
public class PeriodLengthTooLong extends Exception {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 2877983489803401774L;

	/**
	 * This constructor builds an object of type PeriodLengthTooLong by setting
	 * the message of the exception to a string representation indicating the
	 * limit exceeded and the value that exceeded that limit.
	 * 
	 * @param pLimit
	 * @param pLength
	 */
	public PeriodLengthTooLong(int pLimit, int pLength) {
		super("Specified period length is too long! (limit=" + pLimit
				+ " length=" + pLength + ")");
	}

}
