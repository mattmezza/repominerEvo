package it.unisa.sesa.repominer.preferences.exceptions;

/**
 * This exception is thrown when a preference is not an {@code int} value
 * @author RepominerEvo Team
 *
 */
public class IntegerPreferenceException extends Exception {

	/**
	 * The default serial Version UID
	 * 
	 */
	private static final long serialVersionUID = -954158830552747955L;
	private String preferenceName;
	private int preferenceValue;

	
	/**
	 * This constructor creates an IntegerPreferenceException initializing some instance variables.
	 * @param pPreferenceName The name of the preference that caused this exception to be thrown.
	 * @param pPreferenceValue The value of the preference that caused this exception to be thrown.
	 */
	public IntegerPreferenceException(String pPreferenceName,
			int pPreferenceValue) {
		super("Invalid value '"+pPreferenceValue+"' for preference '"+pPreferenceName+"'");
		this.preferenceName = pPreferenceName;
		this.preferenceValue = pPreferenceValue;
	}

	/**
	 * This method returns the name of the preference that caused this exception to be thrown.
	 * @return A {@code String} object representing the name of the preference that caused this exception to be thrown.
	 */
	public String getPreferenceName() {
		return preferenceName;
	}

	/**
	 * This method returns the value of the preference that caused this exception to be thrown.
	 * @return An {@code int} value representing the value of the preference that caused this exception to be thrown.
	 */
	public int getPreferenceValue() {
		return preferenceValue;
	}

}
