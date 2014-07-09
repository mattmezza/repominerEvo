package it.unisa.sesa.repominer.preferences.exceptions;

public class PeriodLengthTooLong extends Exception {

	public PeriodLengthTooLong(int pLimit, int pLength) {
		super("Specified period length is too long! (limit="+pLimit+" length="+pLength+")");
	}
	
}
