package codecollaborateeclipse;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.part.ViewPart;

import codecollaborateeclipse.listener.EditorListener;

public class CCView extends ViewPart {
	
	Label label;
	Button start;
	
    public CCView() {
    	
    }
    
    public void createPartControl(Composite parent) {
       label = new Label(parent, SWT.WRAP);
       label.setText("CodeCollaborate");
       start = new Button(parent, SWT.PUSH);
       start.setText("Start");
       EditorListener el = new EditorListener();
       start.addSelectionListener(new SelectionListener() {

    	   @Override
    	   public void widgetDefaultSelected(SelectionEvent arg0) {
    		   el.listen();
    		   //System.out.println("It worked! (Default)");
    	   }

    	   @Override
    	   public void widgetSelected(SelectionEvent arg0) {
    		   el.listen();
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
