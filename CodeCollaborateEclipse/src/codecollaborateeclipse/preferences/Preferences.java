package codecollaborateeclipse.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import codecollaborateeclipse.Activator;
import codecollaborateeclipse.Core;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class Preferences
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public Preferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getProperty().equals(PreferenceConstants.P_USERNAME)) {
					Core.setUsername(arg0.getNewValue().toString());
				} else if (arg0.getProperty().equals(PreferenceConstants.P_PASSWORD)) {
					Core.setPassword(arg0.getNewValue().toString());
				}
			}
		});
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.P_USERNAME, "Username:", getFieldEditorParent()));
		StringFieldEditor pword = new StringFieldEditor(PreferenceConstants.P_PASSWORD, "Password:", getFieldEditorParent()) {
			@Override
			    protected void doFillIntoGrid(Composite parent, int numColumns) {
			        super.doFillIntoGrid(parent, numColumns);

			        getTextControl().setEchoChar('*');
			    }

			};
		addField(pword);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}