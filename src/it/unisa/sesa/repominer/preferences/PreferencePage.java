package it.unisa.sesa.repominer.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import it.unisa.sesa.repominer.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("With this page you can provide some important information to make the plugin work correctly.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.P_DBHOST,
				"Where the dbms is running?", getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceConstants.P_DBPORT,
				"On which port the dbms is &running?", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_DBNAME,
				"What is the &name of the database?", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_DBUSER,
				"What is the &username for connecting to the dbms?",
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_DBPASS,
				"What is the &password of the dbms user?",
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.S_BCC,
				"Start date for BCC metric", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.E_BCC,
				"End date for BCC metric", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}