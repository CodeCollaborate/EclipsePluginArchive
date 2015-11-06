package codecollaborateeclipse.models;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Created by fahslaj on 10/22/2015.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Response extends ServerMessage {
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
        private byte[] bytes;

        @JsonProperty("Changes")
        private PatchData[] changes;

        @JsonProperty("File")
        private String file;
        
        @JsonProperty("Files")
        private File[] files;

        @JsonProperty("ProjectId")
        private String projectId;

        @JsonProperty("Token")
        private String token;
        
        @JsonProperty("Projects")
        private Project[] projects;
        
        public File[] getFiles() {
			return files;
		}

		public void setFiles(File[] files) {
			this.files = files;
		}

		public Project[] getProjects() {
        	return projects;
        }
        
        public void setProjects(Project[] projects) {
        	this.projects = projects;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
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

        public void setFile(String file) {
            this.file = file;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
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
    	
    	@JsonProperty("FileId")
    	private String fileId;
    	
    	@JsonProperty("Username")
    	private String username;
    	
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

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String file) {
			this.fileId = file;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String user) {
			this.username = user;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Project {
    	
    	@JsonProperty("Id")
    	private String id;
    	
    	@JsonProperty("Name")
    	private String name;
    	
    	@JsonProperty("Permissions")
    	private Map<String, Integer> permissions;
    	
    	@JsonProperty("Files")
    	private File[] files;

		public File[] getFiles() {
			return files;
		}

		public void setFiles(File[] files) {
			this.files = files;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map<String, Integer> getPermissions() {
			return permissions;
		}

		public void setPermissions(Map<String, Integer> permissions) {
			this.permissions = permissions;
		}
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class File {
    	@JsonProperty("Id")
    	private String id;
    	
    	@JsonProperty("Name")
    	private String name;
    	
    	@JsonProperty("RelativePath")
    	private String path;
    	
    	@JsonProperty("Version")
    	private String version;
    	
    	@JsonProperty("Project")
    	private String projectId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
    }
}
