package codecollaborateeclipse.ui;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.internal.databinding.provisional.viewers.ViewerLabelProvider;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.ProjectLocationMoveDialog;
import org.eclipse.ui.internal.dnd.SwtUtil;
import org.eclipse.ui.part.ViewPart;

import codecollaborateeclipse.Core;
import codecollaborateeclipse.Storage;
import codecollaborateeclipse.Storage.DisplayList;
import codecollaborateeclipse.events.DisplayListener;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;

public class ControlPanel extends ViewPart {
	private DataBindingContext m_bindingContext;
	private Label lblInsert;
	private ListViewer usersListViewer;
	private ListViewer projectsListViewer;

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
		lblProjects.setBounds(10, 20, 55, 19);
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
		
		Button btnTest = new Button(composite, SWT.NONE);
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				usersListViewer.refresh();
				projectsListViewer.refresh();
			}
		});
		btnTest.setBounds(92, 10, 85, 29);
		btnTest.setText("Refresh");
		
		projectsListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List projectsListView = projectsListViewer.getList();
		projectsListView.setBounds(10, 45, 472, 222);
		
		usersListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List usersListView = usersListViewer.getList();
		usersListView.setBounds(488, 45, 476, 222);
		m_bindingContext = initDataBindings();
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextLblInsertObserveWidget = WidgetProperties.text().observe(lblInsert);
		IObservableValue observeConnectionStatus = BeanProperties.value(Storage.class, "connectionStatus").observe(Storage.getInstance());
		bindingContext.bindValue(observeTextLblInsertObserveWidget, observeConnectionStatus, null, null);
		//
		usersListViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
				System.out.println("Input changed: old="+arg1+", new="+arg2);
			}
			
			@Override
			public void dispose() {
				System.out.println("Disposing...");
			}
			
			@Override
			public Object[] getElements(Object arg0) {
				ArrayList<String> names = (ArrayList<String>)arg0;
				return names.toArray();
			}
		});
		usersListViewer.setInput(Storage.getInstance().getUsers());
		usersListViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}
			public String getText(Object element) {
				return ((String)element);
			}
		});
		usersListViewer.refresh();
		projectsListViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
				System.out.println("Input changed: old="+arg1+", new="+arg2);
			}
			
			@Override
			public void dispose() {
				System.out.println("Disposing...");
			}
			
			@Override
			public Object[] getElements(Object arg0) {
				ArrayList<String> names = (ArrayList<String>)arg0;
				return names.toArray();
			}
		});
		projectsListViewer.setInput(Storage.getInstance().getProjectNames());
		projectsListViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}
			public String getText(Object element) {
				return ((String)element);
			}
		});
		projectsListViewer.refresh();
		Storage.getInstance().getProjectNames().addDisplayListener(new DisplayListener() {
			
			@Override
			public void onNotification(DisplayList list) {
				Display.getDefault().asyncExec(new Runnable() {
				    @Override
				    public void run() {
				        projectsListViewer.refresh();
				    }
				});
			}
		});
		Storage.getInstance().getUsers().addDisplayListener(new DisplayListener() {
			
			@Override
			public void onNotification(DisplayList list) {
				Display.getDefault().asyncExec(new Runnable() {
				    @Override
				    public void run() {
				        usersListViewer.refresh();
				    }
				});
			}
		});
//		//
//		IObservableList itemsProjectsListObserveWidget = WidgetProperties.items().observe(projectsList);
//		IObservableList storageProjects = BeanProperties.list(Storage.class, "projectNames").observe(Storage.getInstance());
//		bindingContext.bindList(itemsProjectsListObserveWidget, storageProjects , null, null);
//		//
//		//
//		IObservableList itemsUsersListObserveWidget = WidgetProperties.items().observe(usersList);
//		IObservableList storageUsers = BeanProperties.list(Storage.class, "users").observe(Storage.getInstance());
//		bindingContext.bindList(itemsUsersListObserveWidget, storageUsers , null, null);
//		//
		return bindingContext;
	}
}
