package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class SubscribeRequest extends Request{
	
	@JsonProperty("Projects")
	protected String[] projects;
	
	public SubscribeRequest() {
		resource = "User";
		action = "Subscribe";
	}
	
	public SubscribeRequest(int tag) {
		this();
		this.tag = tag;
	}

	public String[] getProjects() {
		return projects;
	}

	public void setProjects(String[] projects) {
		this.projects = projects;
	}
}
