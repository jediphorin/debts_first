package com.noname.springsecurity.demo.crm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.noname.springsecurity.demo.validation.FieldMatch;
import com.noname.springsecurity.demo.validation.ValidEmail;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class CrmUser {

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String username;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String matchingPassword;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;

	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;

	public CrmUser() {}

	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getMatchingPassword() {return matchingPassword;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}	
	public void setMatchingPassword(String matchingPassword) {this.matchingPassword = matchingPassword;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public void setEmail(String email) {this.email = email;}

}