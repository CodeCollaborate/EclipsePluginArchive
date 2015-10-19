package codecollaborateeclipse;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.ui.part.ViewPart;

public class CCView extends ViewPart {
	
	Label label;
	
    public CCView() {
    	
    }
    
    public void createPartControl(Composite parent) {
       label = new Label(parent, SWT.WRAP);
       label.setText("CodeCollaborate");
    }
    
    public void setFocus() {
       // set focus to my widget.  For a label, this doesn't
       // make much sense, but for more complex sets of widgets
       // you would decide which one gets the focus.
    }
    
}
