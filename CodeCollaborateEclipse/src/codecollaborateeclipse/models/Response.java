package codecollaborateeclipse.models;

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
    static class ResponseData {
        @JsonProperty("FileBytes")
        private Byte[] bytes;

        @JsonProperty("Changes")
        private String[] changes;

        @JsonProperty("FileId")
        private String fileId;

        @JsonProperty("ProjectId")
        private String projectId;

        @JsonProperty("UserId")
        private String userId;

        @JsonProperty("Token")
        private String token;

        public Byte[] getBytes() {
            return bytes;
        }

        public void setBytes(Byte[] bytes) {
            this.bytes = bytes;
        }

        public String[] getChanges() {
            return changes;
        }

        public void setChanges(String[] changes) {
            this.changes = changes;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
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
}
