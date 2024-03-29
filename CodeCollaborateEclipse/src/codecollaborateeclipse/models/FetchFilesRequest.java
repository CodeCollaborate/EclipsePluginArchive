package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class FetchFilesRequest extends Request {

	public FetchFilesRequest() {
		resource = "Project";
		action = "GetFiles";
	}
	
	public FetchFilesRequest(int tag) {
		this();
		this.tag = tag;
	}
}
