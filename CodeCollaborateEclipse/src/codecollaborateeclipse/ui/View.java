package codecollaborateeclipse.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.part.ViewPart;

import codecollaborateeclipse.Core;
import codecollaborateeclipse.document.DocumentManager;

public class View extends ViewPart {
	
	Label label;
	Button start;
	
    public View() {
    	
    }
    
    public void createPartControl(Composite parent) {
       label = new Label(parent, SWT.WRAP);
       label.setText("CodeCollaborate");
       start = new Button(parent, SWT.PUSH);
       start.setText("Start");
       Core core = new Core();
       start.addSelectionListener(new SelectionListener() {

    	   @Override
    	   public void widgetDefaultSelected(SelectionEvent arg0) {
    		   core.begin();
    		   //System.out.println("It worked! (Default)");
    	   }

    	   @Override
    	   public void widgetSelected(SelectionEvent arg0) {
    		   core.begin();
    		   //System.out.println("It worked!");
    	   }
       });
    }
    
    public void setFocus() {
       // set focus to my widget.  For a label, this doesn't
       // make much sense, but for more complex sets of widgets
       // you would decide which one gets the focus.
    }
    
}
