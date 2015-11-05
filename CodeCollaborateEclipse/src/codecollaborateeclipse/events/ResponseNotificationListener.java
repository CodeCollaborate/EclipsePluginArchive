package codecollaborateeclipse.events;

import codecollaborateeclipse.models.Request;
import codecollaborateeclipse.models.Response;
import codecollaborateeclipse.models.ServerMessage;

public interface ResponseNotificationListener {
	public void respond(ServerMessage msg, Request req);
}
