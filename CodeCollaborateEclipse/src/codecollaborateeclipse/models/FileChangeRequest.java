package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class FileChangeRequest extends Request{

	public FileChangeRequest() {
		action = "Change";
		resource = "File";
	}
	
	public FileChangeRequest(int tag) {
		this();
		this.tag = tag;
	}
}
