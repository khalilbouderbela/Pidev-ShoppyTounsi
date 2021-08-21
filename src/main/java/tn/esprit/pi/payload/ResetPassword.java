package tn.esprit.pi.payload;

import javax.validation.constraints.NotBlank;

public class ResetPassword {
	
	@NotBlank
	private String token;

	@NotBlank
	private String password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
