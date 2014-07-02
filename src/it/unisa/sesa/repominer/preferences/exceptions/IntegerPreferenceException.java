package it.unisa.sesa.repominer.preferences.exceptions;

public class IntegerPreferenceException extends Exception {

	/**
	 * The default serial Version UID
	 * 
	 */
	private static final long serialVersionUID = -954158830552747955L;
	private String preferenceName;
	private int preferenceValue;

	public IntegerPreferenceException(String pPreferenceName,
			int pPreferenceValue) {
		super("Invalid value '"+pPreferenceValue+"' for preference '"+pPreferenceName+"'");
		this.preferenceName = pPreferenceName;
		this.preferenceValue = pPreferenceValue;
	}

	public String getPreferenceName() {
		return preferenceName;
	}

	public int getPreferenceValue() {
		return preferenceValue;
	}

}
