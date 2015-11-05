package codecollaborateeclipse.ui;

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
import org.eclipse.ui.part.ViewPart;

import codecollaborateeclipse.Core;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ControlPanel extends ViewPart {
	private DataBindingContext m_bindingContext;
	private List userList;
	private List projectList;

	public ControlPanel() {
		
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

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(0, 0, 974, 288);
		
		projectList = new List(composite, SWT.BORDER);
		projectList.setBounds(10, 40, 422, 224);
		
		userList = new List(composite, SWT.BORDER);
		userList.setBounds(438, 40, 526, 224);
		
		Label lblProjects = new Label(composite, SWT.NONE);
		lblProjects.setBounds(10, 20, 42, 19);
		lblProjects.setText("Projects");
		
		Label lblUsers = new Label(composite, SWT.NONE);
		lblUsers.setBounds(439, 20, 55, 19);
		lblUsers.setText("Users");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(10, 273, 954, 25);
		
		Label lblInsert = new Label(composite_1, SWT.NONE);
		lblInsert.setBounds(5, 5, 616, 20);
		lblInsert.setText(" Insert connection message here");
		
		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(830, 11, 134, 23);
		
		ToolItem tltmAddremove = new ToolItem(toolBar, SWT.NONE);
		tltmAddremove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmAddremove.setText("Add/Remove");
		
		ToolItem tltmSettings = new ToolItem(toolBar, SWT.NONE);
		tltmSettings.setText("Settings");
		
		Button btnConnect = new Button(composite, SWT.NONE);
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Core.getInstance().begin();
			}
		});
		btnConnect.setBounds(643, 5, 85, 29);
		btnConnect.setText("Connect");
		
		Button btnDisconnect = new Button(composite, SWT.NONE);
		btnDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Core.getInstance().end();
			}
		});
		btnDisconnect.setBounds(734, 5, 85, 29);
		btnDisconnect.setText("Disconnect");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(79, 11, 85, 19);
		lblNewLabel.setText("Disconnected");
		m_bindingContext = initDataBindings();
		
	}
}
