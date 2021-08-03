package com.cts.authorization.model;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value="Model for the jwt Response for authorization")
public class JwtResponse implements Serializable {
	@ApiModelProperty(notes = "Serial version ID")
	private static final long serialVersionUID = -8091879091924046844L;
	@ApiModelProperty(notes = "JWT token")
	private final String jwttoken;
	
	public JwtResponse(String jwttoken)
	{
		this.jwttoken = jwttoken;
	}
	@ApiOperation(notes="Returns the jwttoken to the request",value="Returns the jwttoken")
	public String getToken()
	{
		return this.jwttoken;
	}
}