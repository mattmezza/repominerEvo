package it.unisa.sesa.repominer.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import it.unisa.sesa.repominer.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.P_DBHOST, "localhost");
		store.setDefault(PreferenceConstants.P_DBNAME, "repominer");
		store.setDefault(PreferenceConstants.P_DBUSER, "root");
		store.setDefault(PreferenceConstants.P_DBPASS, "matt");
		store.setDefault(PreferenceConstants.P_DBPORT, 3306);
	}

}