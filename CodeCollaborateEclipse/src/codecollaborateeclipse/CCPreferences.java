package codecollaborateeclipse;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CCPreferences extends PreferencePage implements IWorkbenchPreferencePage {
	private Text text;
	private Text text_1;

	/**
	 * Create the preference page.
	 */
	public CCPreferences() {
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		
		Label lblEmailAddress = new Label(container, SWT.NONE);
		lblEmailAddress.setBounds(0, 10, 94, 19);
		lblEmailAddress.setText("E-mail Address:");
		
		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setBounds(33, 38, 61, 19);
		lblPassword.setText("Password:");
		
		text = new Text(container, SWT.BORDER);
		text.setBounds(100, 7, 251, 25);
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setBounds(100, 35, 251, 25);

		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}
}
