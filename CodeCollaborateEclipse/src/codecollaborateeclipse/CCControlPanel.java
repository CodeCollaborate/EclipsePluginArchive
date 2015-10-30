package codecollaborateeclipse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

public class CCControlPanel {
	private DataBindingContext m_bindingContext;
	private List userList;
	private List projectList;

	public CCControlPanel() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(0, 0, 974, 288);
		
		projectList = new List(composite, SWT.BORDER);
		projectList.setBounds(10, 38, 422, 216);
		
		userList = new List(composite, SWT.BORDER);
		userList.setBounds(438, 38, 526, 216);
		
		Label lblProjects = new Label(composite, SWT.NONE);
		lblProjects.setBounds(10, 17, 42, 15);
		lblProjects.setText("Projects");
		
		Label lblUsers = new Label(composite, SWT.NONE);
		lblUsers.setBounds(438, 17, 55, 15);
		lblUsers.setText("Users");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(10, 263, 954, 25);
		
		Label lblInsert = new Label(composite_1, SWT.NONE);
		lblInsert.setBounds(5, 5, 616, 15);
		lblInsert.setText(" Insert connection message here");
		
		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(830, 10, 134, 23);
		
		ToolItem tltmAddremove = new ToolItem(toolBar, SWT.NONE);
		tltmAddremove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmAddremove.setText("Add/Remove");
		
		ToolItem tltmSettings = new ToolItem(toolBar, SWT.NONE);
		tltmSettings.setText("Settings");
		m_bindingContext = initDataBindings();
	}

	@PreDestroy
	public void dispose() {
	}

	public void setFocus() {
		// TODO	Set the focus to control
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsUserListObserveWidget = WidgetProperties.items().observe(userList);
		IObservableList itemsProjectListObserveWidget = WidgetProperties.items().observe(projectList);
		bindingContext.bindList(itemsUserListObserveWidget, itemsProjectListObserveWidget, null, null);
		//
		return bindingContext;
	}
}
