package com.cts.authorization.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(value="Model for the jwt request for authorization")
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	@ApiModelProperty(notes = "Name of the User")
	private String userName;
	@ApiModelProperty(notes = "Password of the User")
	private String password;
	
	public JwtRequest()
	{
		
	}

	public JwtRequest(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}
}