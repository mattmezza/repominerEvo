package it.unisa.sesa.repominer.preferences;

import java.text.ParseException;
import java.util.Date;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.preferences.exceptions.IntegerPreferenceException;
import it.unisa.sesa.repominer.preferences.exceptions.PeriodLengthTooLong;
import it.unisa.sesa.repominer.util.Utils;

import org.eclipse.jface.preference.IPreferenceStore;

public class Preferences {

	private static IPreferenceStore STORE = Activator.getDefault()
			.getPreferenceStore();

	/**
	 * This method fetches the value of database host from the Eclipse
	 * preference store.
	 * 
	 * @return A {@code String} object representing the database host to which
	 *         the application connects.
	 */
	public static String getDatabaseHost() {
		return STORE.getString(PreferenceConstants.P_DBHOST);
	}

	/**
	 * This method fetches the value of database name from the Eclipse
	 * preference store.
	 * 
	 * @return A {@code String} object representing the database name to which
	 *         the application connects.
	 */
	public static String getDatabaseName() {
		return STORE.getString(PreferenceConstants.P_DBNAME);
	}

	/**
	 * This method fetches the value of database user's password from the
	 * Eclipse preference store.
	 * 
	 * @return A {@code String} object representing the database users's
	 *         password with which the application logs into DBMS.
	 */
	public static String getDatabasePassword() {
		return STORE.getString(PreferenceConstants.P_DBPASS);
	}

	/**
	 * This method fetches the value of database username from the Eclipse
	 * preference store.
	 * 
	 * @return A {@code String} object representing the database username with
	 *         which the application logs into DBMS.
	 */
	public static String getDatabaseUser() {
		return STORE.getString(PreferenceConstants.P_DBUSER);
	}

	/**
	 * This method fetches the value of database port from the Eclipse
	 * preference store.
	 * 
	 * @return A {@code int} value representing the port on which the DBMS is
	 *         listening to.
	 */
	public static int getDatabasePort() {
		return Math.abs(STORE.getInt(PreferenceConstants.P_DBPORT));
	}

	/**
	 * This method fetches the value of the Extended Code Change Model metric
	 * modality from the Eclipse preference store.
	 * 
	 * @return A {@code String} object representing the Extended Code Change
	 *         Model metric modality.
	 */
	public static String getECCMModality() {
		return STORE.getString(PreferenceConstants.ECCM_MODALITY);
	}

	/**
	 * This method fetches the value of the modification limit value for the
	 * Extended Code Change Model metric. Value is fetched from the Eclipse
	 * preference store.
	 * 
	 * @return An {@code int} value representing value for the modification
	 *         limit.
	 * @throws IntegerPreferenceException
	 *             This exception is thrown when the value is not a positive int
	 *             number.
	 */
	public static int getECCMModificationLimit()
			throws IntegerPreferenceException {
		int limit = STORE.getInt(PreferenceConstants.ECCM_MODIFICATION_LIMIT);
		if (limit > 0) {
			return limit;
		} else {
			throw new IntegerPreferenceException(
					PreferenceConstants.ECCM_MODIFICATION_LIMIT, limit);
		}
	}

	/**
	 * This method fetches the value for the period starting date for the Basic
	 * Code Change Model metric. Value is fetched from the Eclipse preference
	 * store.
	 * 
	 * @return A {@code Date} object representing the starting date for the
	 *         interval in which to calculate Basic Code Change Model metric.
	 * @throws ParseException
	 *             This exception is thrown when the string fetched from Eclipse
	 *             preference store is not parsable as a valid Date.
	 */
	public static Date getPeriodStartingDate() throws ParseException {
		return Utils.stringToDate(STORE
				.getString(PreferenceConstants.PERIOD_START));
	}

	/**
	 * This method fetches the value for the period ending date for the Basic
	 * Code Change Model metric. Value is fetched from the Eclipse preference
	 * store.
	 * 
	 * @return A {@code Date} object representing the ending date for the
	 *         interval in which to calculate Basic Code Change Model metric.
	 * @throws ParseException
	 *             This exception is thrown when the string fetched from Eclipse
	 *             preference store is not parsable as a valid Date.
	 */
	public static Date getPeriodEndingDate() throws ParseException {
		return Utils.stringToDate(STORE
				.getString(PreferenceConstants.PERIOD_END));
	}

	/**
	 * This method fetches the value of the period length from the Eclipse
	 * preference store.
	 * 
	 * @return An {@code int} value representing the value for the period
	 *         length.
	 * @throws IntegerPreferenceException
	 *             Thrown when the value fetched is not positive number.
	 * @throws PeriodLengthTooLong
	 *             Thrown when the value exceeds a limit (4000 when considering
	 *             weeks, 1000 when months, 80 years).
	 */
	public static int getPeriodLength() throws IntegerPreferenceException,
			PeriodLengthTooLong {
		int length = STORE.getInt(PreferenceConstants.PERIOD_LENGTH);
		if (length <= 0) {
			throw new IntegerPreferenceException(
					PreferenceConstants.PERIOD_LENGTH, length);
		} else {
			String periodType = Preferences.getPeriodType();
			if (periodType.equals(PreferenceConstants.PERIOD_TYPE_WEEK)) {
				if (length > 4000) {
					throw new PeriodLengthTooLong(4000, length);
				} else {
					return length;
				}
			} else if (periodType.equals(PreferenceConstants.PERIOD_TYPE_MONTH)) {
				if (length > 1000) {
					throw new PeriodLengthTooLong(1000, length);
				} else {
					return length;
				}
			} else if (periodType.equals(PreferenceConstants.PERIOD_TYPE_YEAR)) {
				if (length > 80) {
					throw new PeriodLengthTooLong(80, length);
				} else {
					return length;
				}
			}
			return length;
		}
	}

	/**
	 * This method fetches the value of radius parameter for DBSCAN algorithm
	 * from Eclipse preference store.
	 * 
	 * @return A {@code int} value representing the radius of the DBSCAN
	 *         algorithm.
	 * @throws IntegerPreferenceException
	 *             Thrown when the value fetched is not a positive number.
	 */
	public static int getEpsParameter() throws IntegerPreferenceException {
		int eps = STORE.getInt(PreferenceConstants.BURST_EPS);
		if (eps <= 0) {
			throw new IntegerPreferenceException(PreferenceConstants.BURST_EPS,
					eps);
		} else {
			return eps;
		}
	}

	/**
	 * This method fetches the value of the minimum size per cluster relative to
	 * the DBSCAN algorithm from Eclipse preference store.
	 * 
	 * @return A {@code int} value representing the minimum size per cluster
	 *         relative to the DBSCAN algorithm.
	 * @throws IntegerPreferenceException
	 *             Thrown when the value fetched is not a positive number.
	 */
	public static int getMinPointsParameter() throws IntegerPreferenceException {
		int minPoints = STORE.getInt(PreferenceConstants.BURST_MINPOINTS);
		if (minPoints <= 0) {
			throw new IntegerPreferenceException(
					PreferenceConstants.BURST_MINPOINTS, minPoints);
		} else {
			return minPoints;
		}
	}

	/**
	 * This method fetches the value of the period type from Eclipse preference
	 * store.
	 * 
	 * @return A {@code String} object representing the period type relative to
	 *         the Extended Code Change Model metric.
	 */
	public static String getPeriodType() {
		return STORE.getString(PreferenceConstants.PERIOD_TYPE);
	}

}
