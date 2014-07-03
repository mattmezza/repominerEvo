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
		store.setDefault(PreferenceConstants.P_DBPASS, "root");
		store.setDefault(PreferenceConstants.P_DBPORT, 3306);
		
		store.setDefault(PreferenceConstants.PERIOD_START, "2000/01/01");
		store.setDefault(PreferenceConstants.PERIOD_END, "2020/12/31");
		store.setDefault(PreferenceConstants.PERIOD_LENGTH, 3);
		store.setDefault(PreferenceConstants.PERIOD_TYPE, "MONTH");
		store.setDefault(PreferenceConstants.ECCM_MODALITY, "time");
		store.setDefault(PreferenceConstants.ECCM_MODIFICATION_LIMIT, 100);
		
		store.setDefault(PreferenceConstants.BURST_EPS, 5);
		store.setDefault(PreferenceConstants.BURST_MINPOINTS, 5);
	}

}
