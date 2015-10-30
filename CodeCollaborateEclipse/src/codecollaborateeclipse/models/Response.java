package codecollaborateeclipse.models;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fahslaj on 10/22/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Response {
    @JsonProperty("Status")
    private int status;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Tag")
    private long tag;

    @JsonProperty("Data")
    private ResponseData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
	public static class ResponseData {
        @JsonProperty("FileBytes")
        private Byte[] bytes;

        @JsonProperty("Changes")
        private PatchData[] changes;

        @JsonProperty("File")
        private String file;

        @JsonProperty("ProjectId")
        private String projectId;

        @JsonProperty("User")
        private String user;

        @JsonProperty("Token")
        private String token;
        
        @JsonProperty("UserId")
        private String userId;

        public Byte[] getBytes() {
            return bytes;
        }

        public void setBytes(Byte[] bytes) {
            this.bytes = bytes;
        }

        public PatchData[] getChanges() {
            return changes;
        }

        public void setChanges(PatchData[] changes) {
            this.changes = changes;
        }

        public String getFile() {
            return file;
        }
        
        public String getUserId() {
        	return userId;
        }
        
        public void setUserId(String userId) {
        	this.userId = userId;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class PatchData {
    	@JsonProperty("Id")
    	private String id;
    	
    	@JsonProperty("Changes")
    	private String changes;
    	
    	@JsonProperty("Version")
    	private long version;
    	
    	@JsonProperty("File")
    	private String file;
    	
    	@JsonProperty("User")
    	private String user;
    	
    	@JsonProperty("Date")
    	private String date;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getChanges() {
			return changes;
		}

		public void setChanges(String changes) {
			this.changes = changes;
		}

		public long getVersion() {
			return version;
		}

		public void setVersion(long version) {
			this.version = version;
		}

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
    }
}
