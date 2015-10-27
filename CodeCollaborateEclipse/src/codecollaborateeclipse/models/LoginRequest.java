package codecollaborateeclipse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginRequest extends Request{
	
	@JsonProperty("Email")
	protected String email;
	
	@JsonProperty("Password")
	protected String password;
	
	public LoginRequest() {
		resource = "User";
		action = "Login";
	}
	
	public LoginRequest(int tag) {
		this();
		this.tag = tag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
