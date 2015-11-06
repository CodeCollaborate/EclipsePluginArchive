package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class FetchProjectsRequest extends Request {
	
	public FetchProjectsRequest() {
		resource = "User";
		action = "Projects";
	}
	
	public FetchProjectsRequest(int tag) {
		this();
		this.tag = tag;
	}
}
