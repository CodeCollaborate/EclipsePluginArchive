package codecollaborateeclipse;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.databinding.observable.list.WritableList;

import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.document.DocumentManager;
import codecollaborateeclipse.events.DocumentChangedListener;
import codecollaborateeclipse.events.ResponseNotificationListener;
import codecollaborateeclipse.models.FetchFilesRequest;
import codecollaborateeclipse.models.FetchProjectsRequest;
import codecollaborateeclipse.models.LoginRequest;
import codecollaborateeclipse.models.Notification;
import codecollaborateeclipse.models.PullFileRequest;
import codecollaborateeclipse.models.Request;
import codecollaborateeclipse.models.Response;
import codecollaborateeclipse.models.Response.File;
import codecollaborateeclipse.models.Response.Project;
import codecollaborateeclipse.models.ServerMessage;
import codecollaborateeclipse.models.SubscribeRequest;
import codecollaborateeclipse.preferences.PreferenceConstants;
import codecollaborateeclipse.resources.ResourceManager;
import codecollaborateeclipse.ui.UIManager;

public class Core implements ResponseNotificationListener, DocumentChangedListener {
	
	private DocumentManager documentManager;
	private CCWebSocketConnector connector;
	private static Core core = new Core();
	
	public Core() {
		
//		listener = new DocumentManager(connector);
//		connector.setEditorListener(listener);
	}
	
	public void begin() {
//		System.out.println(ResourceManager.getWorkingDirectory());
		// Set up the core to listen to the CCWSC and the Document Manager
		if (connector != null || documentManager != null)
			end();
		connector = new CCWebSocketConnector();
		documentManager = new DocumentManager();
		
		ResourceManager.getFileMetadata();
		connector.addResponseNotificationListener(this);
		documentManager.addDocumentChangedListener(this);
		
		// get data from preferences
		Storage.getInstance().setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME));//set("Username",Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME));
		Storage.getInstance().setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD));//set("Password",Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD));
		
		// TODO get data from metadata files
		//ResourceManager.getFileMetadata();
		
		connector.connect();
		sleep(1000);
        connector.login();
        sleep(1000);
        //documentManager.getActiveDocument();
        connector.fetchProjects();
        sleep(2000);
        // documentManager.generateDocumentsForProjects(); // generate document listeners
        connector.subscribe(documentManager.getActiveProjectId()); // change to current project
        documentManager.listen();
        sleep(1000);
        connector.pullDocument(documentManager.getActiveFileId());
//        UIManager.showInfoDialog("Dank Title", "Insert Dank Memes Here");
//    	Storage.getInstance().getUsers().add("jello");
//    	Storage.getInstance().firePropertyChange("users", Storage.getInstance().getUsers(), Storage.getInstance().getUsers());
	}
	
	public void end() {
		connector.close();
		documentManager.close();
		connector = null;
		documentManager = null;
		Storage.getInstance().getProjectNames().removeAll(Storage.getInstance().getProjectNames());
		Storage.getInstance().getUsers().removeAll(Storage.getInstance().getUsers());
	}

	@Override
	public void onDocumentModified(String resId, String patch) {
		connector.sendPatch(resId, patch);
	}

	@Override
	public void respond(ServerMessage r, Request req) {
		if (r instanceof Notification && req == null) {
			interpretNotification((Notification)r);
		} else if (r instanceof Response && req != null) {
			interpretResponse((Response)r, req);
		} else {
			System.out.println("Cannot respond; invalid server message type.");
			return;
		}
	}
	
    private void interpretResponse(Response response, Request request) {
    	if (response == null) {
    		System.out.println("Failed to interpret response.");
    		return;
    	}
    	if (request instanceof LoginRequest && response.getData() != null) {
    		System.out.println("Retrieving user details...");
    		Storage.getInstance().setToken(response.getData().getToken());//set("Token", response.getData().getToken());
    		System.out.println("Token: "+Storage.getInstance().getToken());//get("Token"));
    	} else if (request instanceof PullFileRequest && response.getData() != null) {
    		documentManager.flushFile(response.getData().getChanges(), response.getData().getBytes());
    	} else if (request instanceof FetchProjectsRequest && response.getData() != null) {
    		populateProjectData(response.getData().getProjects());
    	} else if (request instanceof FetchFilesRequest && response.getData() != null) {
    		populateFileData(request.getResId(), response.getData().getFiles());
    	} else if (request instanceof SubscribeRequest && response.getStatus() == 1) {
    		Storage.getInstance().getUsers().add(Storage.getInstance().getUsername()+" (me)");
    	}
    	switch (response.getStatus()) {
    		case 1: return; 
    		case -100: UIManager.showInfoDialog("Error -100", "No such user found.");break; //no such user found error
    		case -101: UIManager.showInfoDialog("Error -101", "Error creating user: internal error.");break; //error creating user: internal error
    		case -102: UIManager.showInfoDialog("Error -102", "Error creating usre: duplicate username.");break; //error creating user: duplicate username (reprompt for new username)
    		case -103: UIManager.showInfoDialog("Error -103", "Error logging in: internal error.");break; //error logging in: internal error
    		case -104: UIManager.showInfoDialog("Error -104", "Error logging in: invalid username or password.");break; //error logging in: Invalid Username or Password
    		case -105: UIManager.showInfoDialog("Error -105", "Error logging in: invalid token.");break; //listener.repromptLogin(); Error logging in: Invalid Token
    		
    		case -200: UIManager.showInfoDialog("Error -200", "No such project found.");break; //no such project found
    		case -201: UIManager.showInfoDialog("Error -201", "Error creating project: internal error.");break; //error creating project: internal error
    		case -202: UIManager.showInfoDialog("Error -202", "Error renaming project: internal error.");break; //error renaming project: internal error
    		case -203: UIManager.showInfoDialog("Error -203", "Error granting permissions: internal error.");break; //error granting permissions: internal error
    		case -204: UIManager.showInfoDialog("Error -204", "Error revoking permissions: internal error.");break; //error revoking permissions: internal error
    		case -205: UIManager.showInfoDialog("Error -205", "Error revoking permissions: document must have an owner.");break; //error revoking permissions: must have an owner
    		case -206: UIManager.showInfoDialog("Error -206", "Error subscribing to project: internal error.");break; //error subscribing to project
    		
    		case -300: UIManager.showInfoDialog("Error -300", "No such file found.");break; //no such file found
    		case -301: UIManager.showInfoDialog("Error -301", "Error creating file: internal error.");break; //error creating file: internal error
    		case -302: UIManager.showInfoDialog("Error -302", "Error renaming file: internal error.");break; //error renaming file: internal error
    		case -303: UIManager.showInfoDialog("Error -303", "Error moving file: internal error.");break; //error moving file: internal error
    		case -304: UIManager.showInfoDialog("Error -304", "Error deleting file: internal error.");break; //error deleting file: internal error
    		case -305: UIManager.showInfoDialog("Error -305", "Error creating file: file already exists.");break; //error creating file: duplicate file
    		case -306: UIManager.showInfoDialog("Error -306", "Error renaming file: file of new name already exists.");break; //error renaming file: duplicate file
    		case -307: UIManager.showInfoDialog("Error -307", "Error moving file: file of the same name exists in the target directory.");break; //error moving file: duplicate file
    		case -308: UIManager.showInfoDialog("Error -308", "Error creating file: invalid file path.");break; //error creating file: invalid file path
    			
    		case -400: UIManager.showInfoDialog("Error -400", "Error inserting change: internal error.");break; //error inserting change: internal error
    		case -401: connector.sendPatch(request.getResId(), request.getChanges()); break;//UIManager.showInfoDialog("Error -401", "Error inserting change: duplicate version number.");break; //error inserting change: duplicate version number
    		case -402: UIManager.showInfoDialog("Error -402", "Error reading change: internal error.");break; //error reading change: internal error
    		case -420: UIManager.showInfoDialog("Error -420", "Error, too blazed.");break; //error, too blazed
    	}
    	
    	System.out.println("Successfully interpreted response of status: "+response.getStatus());
    }
    
    private void interpretNotification(Notification n) {
    	String action = n.getAction();
    	String resource = n.getResource();
    	switch(resource) {
    		case "User": break;
    		case "File": 
    			if (action.equals("Change")) {
    				documentManager.recievePatch(n.getResId(), n.getData().getChanges());
    			}
    			break;
    		case "Project": 
    			if (action.equals("Subscribe")) {
    				Storage.getInstance().getUsers().add(n.getData().getUsername());
    			}
    			break;
    		default: break;
    	}
    }
    
    private void populateProjectData(Project[] projects) {
    	HashMap<String, Project> projectsList = new HashMap<String, Project>();
    	ArrayList<String> projectNames = new ArrayList<String>();
    	for (Project p : projects) {
    		projectsList.put(p.getId(), p);
    		projectNames.add(p.getName());
    	}
    	Storage.getInstance().setProjects(projectsList);
    	Storage.getInstance().getProjectNames().addAll(projectNames);
    	for (Project p : projects) {
    		connector.fetchFiles(p.getId());
    	}
    }
    
    private void populateFileData(String projId, File[] files) {
    	if (files == null)
    		return;
    	Project p = Storage.getInstance().getProjects().get(projId);
    	if (p == null)
    		return;
    	p.setFiles(files);
    }
    
    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	public static Core getInstance() {
		return core;
	}
}
