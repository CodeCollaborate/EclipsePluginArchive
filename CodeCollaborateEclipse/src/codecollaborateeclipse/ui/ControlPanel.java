package codecollaborateeclipse.ui;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.internal.dnd.SwtUtil;
import org.eclipse.ui.part.ViewPart;

import codecollaborateeclipse.Core;
import codecollaborateeclipse.Storage;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ListViewer;

public class ControlPanel extends ViewPart {
	private DataBindingContext m_bindingContext;
	ListViewer projectsListViewer;
	ListViewer usersListViewer;
	private Label lblInsert;

	public ControlPanel() {
		
	}

	@PreDestroy
	public void dispose() {
	}

	public void setFocus() {
		// TODO	Set the focus to control
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(0, 0, 974, 288);
		
		Label lblProjects = new Label(composite, SWT.NONE);
		lblProjects.setBounds(10, 20, 42, 19);
		lblProjects.setText("Projects");
		
		Label lblUsers = new Label(composite, SWT.NONE);
		lblUsers.setBounds(488, 20, 55, 19);
		lblUsers.setText("Users");
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(10, 273, 954, 25);
		
		lblInsert = new Label(composite_1, SWT.NONE);
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
		
		projectsListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List projectList = projectsListViewer.getList();
		projectList.setBounds(10, 45, 472, 222);
		
		usersListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List userList = usersListViewer.getList();
		userList.setBounds(488, 45, 476, 222);
		m_bindingContext = initDataBindings();
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextLblInsertObserveWidget = WidgetProperties.text().observe(lblInsert);
		IObservableValue observeConnectionStatus = BeanProperties.value(Storage.class, "connectionStatus").observe(Storage.getInstance());
		bindingContext.bindValue(observeTextLblInsertObserveWidget, observeConnectionStatus, null, null);
		//
//		ObservableListContentProvider usersContentProvider = new ObservableListContentProvider();
//		usersListViewer.setContentProvider(usersContentProvider);
//		IObservableSet knownUsers = usersContentProvider.getKnownElements();
//		final IObservableMap usersMap = BeanProperties.value(Storage.class, "users").observeDetail(knownUsers);
//		ILabelProvider usersLabelProvider = new ObservableMapLabelProvider(usersMap);
//		usersListViewer.setLabelProvider(usersLabelProvider);
		return bindingContext;
	}
}
