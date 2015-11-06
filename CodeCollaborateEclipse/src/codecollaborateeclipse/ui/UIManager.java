package codecollaborateeclipse.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class UIManager {
	
	public static void showInfoDialog(String title, String information) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		    public void run() {
			    Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			    MessageDialog.openInformation(activeShell, title, information);
		    }
		});
	}
}
