package codecollaborateeclipse;

import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.document.DocumentManager;
import codecollaborateeclipse.events.DocumentChangedListener;
import codecollaborateeclipse.events.ResponseNotificationListener;
import codecollaborateeclipse.models.LoginRequest;
import codecollaborateeclipse.models.Notification;
import codecollaborateeclipse.models.PullFileRequest;
import codecollaborateeclipse.models.Request;
import codecollaborateeclipse.models.Response;
import codecollaborateeclipse.models.ServerMessage;
import codecollaborateeclipse.preferences.PreferenceConstants;

public class Core implements ResponseNotificationListener, DocumentChangedListener {
	
	private DocumentManager documentManager;
	private CCWebSocketConnector connector;
	private static Core core = new Core();
	
	public Core() {
		connector = new CCWebSocketConnector();
		documentManager = new DocumentManager();
		
//		listener = new DocumentManager(connector);
//		connector.setEditorListener(listener);
	}
	
	public void begin() {
		// Set up the core to listen to the CCWSC and the Document Manager
		connector.addResponseNotificationListener(this);
		documentManager.addDocumentChangedListener(this);
		
		// get data from preferences
		Storage.setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME));
		Storage.setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD));
		
		// TODO get data from metadata files
		//ResourceManager.getFileMetadata();
		
		connector.connect();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.login();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.subscribe();
        documentManager.listen();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.pullDocument();
	}
	
	public void end() {
		connector.close();
		documentManager.close();
	}

	@Override
	public void onDocumentModified(String patch) {
		connector.sendPatch(patch);
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
    		Storage.setToken(response.getData().getToken());
    		System.out.println("Token: "+Storage.getToken());
    	} else if (request instanceof PullFileRequest && response.getData() != null) {
//    		documentManager.flushFile(response.getData().getChanges(), response.getData().getBytes());
//    		PatchData[] changes = response.getData().getChanges();
//    		if (changes != null) {
//	    		for (int i = 0; i < changes.length; i++) {
//	    			documentManager.recievePatch(changes[i].getChanges());
//	    		}
//    		}
    	}
    	switch (response.getStatus()) {
    		case 1: return; 
    		case -100: //no such user found error
    		case -101: //error creating user: internal error
    		case -102: //error creating user: duplicate username (reprompt for new username)
    		case -103: //error logging in: internal error
    		case -104: //error logging in: Invalid Username or Password
    		case -105: break;//listener.repromptLogin(); Error logging in: Invalid Token
    		
    		case -200: //no such project found
    		case -201: //error creating project: internal error
    		case -202: //error renaming project: internal error
    		case -203: //error granting permissions: internal error
    		case -204: //error revoking permissions: internal error
    		case -205: //error revoking permissions: must have an owner
    		case -206: break; //error subscribing to project
    		
    		case -300: //no such file found
    		case -301: //error creating file: internal error
    		case -302: //error renaming file: internal error
    		case -303: //error moving file: internal error
    		case -304: //error deleting file: internal error
    		case -305: //error creating file: duplicate file
    		case -306: //error renaming file: duplicate file
    		case -307: //error moving file: duplicate file
    		case -308: break; //error creating file: invalid file path
    			
    		case -400: //error inserting change: internal error
    		case -401: //error inserting change: duplicate version number
    		case -402: //error reading change: internal error
    		case -420: break;//error, too blazed
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
    				documentManager.recievePatch(n.getData().getChanges());
    			}
    			break;
    		case "Project": 
    			if (action.equals("Subscribe")) {
    				//update UI for current connected users
    			}
    			break;
    		default: break;
    	}
    }
	
	public static Core getInstance() {
		return core;
	}
}
