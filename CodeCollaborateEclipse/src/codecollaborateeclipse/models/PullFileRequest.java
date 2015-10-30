package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PullFileRequest extends Request {
	
	public PullFileRequest() {
		action = "Pull";
		resource = "File";
	}
	
	public PullFileRequest(int t) {
		this();
		tag = t;
	}
}
