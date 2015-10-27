package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Request {
	@JsonProperty("Tag")
	protected int tag;
	
	@JsonProperty("Action")
	protected String action;
	
	@JsonProperty("Resource")
	protected String resource;
	
	@JsonProperty("ResId")
	protected String resId;
	
	@JsonProperty("FileVersion")
	protected long fileVersion;
	
	@JsonProperty("Changes")
	protected String changes;
	
	@JsonProperty("UserId")
	protected String userId;
	
	@JsonProperty("Token")
	protected String token;
	
	public Request() {
		
	}
	
	public Request(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public long getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(long fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
