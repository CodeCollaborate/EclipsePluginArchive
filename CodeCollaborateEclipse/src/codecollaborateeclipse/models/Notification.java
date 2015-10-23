package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fahslaj on 10/22/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Notification {
    @JsonProperty("Action")
    private String action;

    @JsonProperty("Resource")
    private String resource;

    @JsonProperty("ResId")
    private String resId;

    @JsonProperty("Data")
    private NotificationData data;

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

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    static class NotificationData{
        
    }
}
