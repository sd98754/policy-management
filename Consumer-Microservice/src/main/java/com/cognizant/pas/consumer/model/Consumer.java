package com.cognizant.pas.consumer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Consumer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model object that stores the consumer information.")


public class Consumer {
	
	@ApiModelProperty(notes = "id of the package")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Long id;
	
	@ApiModelProperty(notes="first name of the consumer")
	@NotBlank
	@Size(max = 50)
	@Column(name = "First_Name")
	private String firstname;
	
	@ApiModelProperty(notes="last name of the consumer")
	@NotBlank
	@Size(max = 50)
	@Column(name = "Last_Name")
	private String lastname;
	
	@ApiModelProperty(notes="date of birth")
	@NotBlank
	@Size(max = 20)
	@Column(name = "DOB")
	private String dob;
	
	@ApiModelProperty(notes="name of the business")
	@NotBlank
	@Size(max = 50)
	@Column(name = "Business_Name")
	private String businessname;
	
	@ApiModelProperty(notes="pan card number")
	@NotBlank
	@Size(max = 12)
	@Column(name = "PAN_Details")
	private String pandetails;
	
	@ApiModelProperty(notes="email id of consumer")
	@NotBlank
	@Size(max = 50)
	@Column(name = "Email")
	private String email;
	
	@ApiModelProperty(notes="phone number")
	@NotBlank
	@Size(max = 10)
	@Column(name = "Phone")
	private String phone;
	
	@ApiModelProperty(notes="business website")
	@NotBlank
	@Size(max = 40)
	@Column(name = "Website")
	private String website;
	
	@ApiModelProperty(notes="business details")
	@NotBlank
	@Size(max = 150)
	@Column(name = "Business_Overview")
	private String businessoverview;
	
	@ApiModelProperty(notes="consumer validity")
	@NotBlank
	@Size(max = 30)
	@Column(name = "Validity_of_Consumer")
	private String validity;
	
	@ApiModelProperty(notes="name of the agent")
	@NotBlank
	@Size(max = 50)
	@Column(name = "Agent_Name")
	private String agentname;
	
	@ApiModelProperty(notes="id of the agent")
	@NotNull
	@Column(name = "Agent_ID")
	private Long agentid;

	public Consumer(@NotBlank @Size(max = 50) String firstname, @NotBlank @Size(max = 50) String lastname,
			@NotBlank @Size(max = 20) String dob, @NotBlank @Size(max = 50) String businessname,
			@NotBlank @Size(max = 12) String pandetails, @NotBlank @Size(max = 50) String email,
			@NotBlank @Size(max = 10) String phone, @NotBlank @Size(max = 40) String website,
			@NotBlank @Size(max = 150) String businessoverview, @NotBlank @Size(max = 30) String validity,
			@NotBlank @Size(max = 50) String agentname, @NotNull Long agentid) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.businessname = businessname;
		this.pandetails = pandetails;
		this.email = email;
		this.phone = phone;
		this.website = website;
		this.businessoverview = businessoverview;
		this.validity = validity;
		this.agentname = agentname;
		this.agentid = agentid;
	}
	
}
