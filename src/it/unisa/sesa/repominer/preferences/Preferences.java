package it.unisa.sesa.repominer.preferences;

import java.text.ParseException;
import java.util.Date;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.preferences.exceptions.IntegerPreferenceException;
import it.unisa.sesa.repominer.util.Utils;

import org.eclipse.jface.preference.IPreferenceStore;

public class Preferences {

	private static IPreferenceStore STORE = Activator.getDefault()
			.getPreferenceStore();

	public static String getDatabaseHost() {
		return STORE.getString(PreferenceConstants.P_DBHOST);
	}

	public static String getDatabaseName() {
		return STORE.getString(PreferenceConstants.P_DBNAME);
	}

	public static String getDatabasePassword() {
		return STORE.getString(PreferenceConstants.P_DBPASS);
	}

	public static String getDatabaseUser() {
		return STORE.getString(PreferenceConstants.P_DBUSER);
	}

	public static int getDatabasePort() {
		return Math.abs(STORE.getInt(PreferenceConstants.P_DBPORT));
	}

	public static String getECCMModality() {
		return STORE.getString(PreferenceConstants.ECCM_MODALITY);
	}

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

	public static Date getPeriodStartingDate() throws ParseException {
		return Utils.stringToDate(STORE
				.getString(PreferenceConstants.PERIOD_START));
	}

	public static Date getPeriodEndingDate() throws ParseException {
		return Utils.stringToDate(STORE
				.getString(PreferenceConstants.PERIOD_END));
	}

	public static int getPeriodLength() throws IntegerPreferenceException {
		int length = STORE.getInt(PreferenceConstants.PERIOD_LENGTH);
		if (length <= 0) {
			throw new IntegerPreferenceException(
					PreferenceConstants.PERIOD_LENGTH, length);
		} else {
			return length;
		}
	}

	public static int getEpsParameter() throws IntegerPreferenceException {
		int eps = STORE.getInt(PreferenceConstants.BURST_EPS);
		if (eps <= 0) {
			throw new IntegerPreferenceException(PreferenceConstants.BURST_EPS,
					eps);
		} else {
			return eps;
		}
	}
	
	public static int getMinPointsParameter() throws IntegerPreferenceException{
		int minPoints = STORE.getInt(PreferenceConstants.BURST_MINPOINTS);
		if(minPoints<=0){
			throw new IntegerPreferenceException(PreferenceConstants.BURST_MINPOINTS, minPoints);
		}else{
			return minPoints;
		}
	}

	public static String getPeriodType() {
		return STORE.getString(PreferenceConstants.PERIOD_TYPE);
	}

}
