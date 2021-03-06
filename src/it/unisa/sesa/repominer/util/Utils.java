package it.unisa.sesa.repominer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

	private static final Pattern PATTERN_BUG_FIXING = Pattern.compile(
			".*[Bb][Uu][Gg]([sS])?([^a-zA-Z0-9])*[Ff][iI][xX].*",
			Pattern.DOTALL);
	private static final Pattern PATTERN_REFACTORING = Pattern.compile(
			".*[rR][eE][fF][aA][cC][tT][oO][rR].*", Pattern.DOTALL);

	
	/**
	 * Checks whether or not a message is likely to be a refactoring one.
	 * @param msg The message to check.
	 * @return true if message is refactoring, false otherwise.
	 */
	public static boolean msgIsRefactoring(String msg) {
		return Utils.PATTERN_REFACTORING.matcher(msg).matches();
	}
	
	/**
	 * Checks whether or not a message is likely to be a bug-fixing one.
	 * @param msg The message to check.
	 * @return true if message is bug-fixing, false otherwise.
	 */
	public static boolean msgIsBugFixing(String msg) {
		return Utils.PATTERN_BUG_FIXING.matcher(msg).matches();
	}

	/**
	 * This method convert a String into a Date
	 * 
	 * @param pString
	 * @return A Date Object
	 */
	public static Date stringToDate(String pString) throws ParseException {
		Date convertedDate = null;
		DateFormat formatter = null;

		formatter = new SimpleDateFormat("yyyy/MM/dd");
		convertedDate = (Date) formatter.parse(pString);
		return convertedDate;
	}

	/**
	 * This method calculates difference in days between two dates
	 * 
	 * @param startDate
	 *            start date
	 * @param endDate
	 *            end date
	 * @return days from start date to end date
	 */
	public static int daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		int daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	/**
	 * This method convert an instance of Date into an instance of Calendar
	 * 
	 * @param pDate
	 * @return A Calendar object
	 */
	public static Calendar dateToCalendar(Date pDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pDate);
		return calendar;
	}

	/**
	 * This function calculate the base-2 logarithm of a float number
	 * 
	 * @param pValue
	 * @return Base-2 logarithm value
	 */
	public static double log2(double pValue) {
		return Math.log(pValue) / Math.log(2);
	}

}
