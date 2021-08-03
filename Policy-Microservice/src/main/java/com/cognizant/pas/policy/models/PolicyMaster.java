package com.cognizant.pas.policy.models;

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
import lombok.*;

@Entity
@Table(name="Policy_Master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model object that stores the policy details.")

public class PolicyMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	@ApiModelProperty(notes="Id of the Policy")
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "Policy_ID")
	@ApiModelProperty(notes="Id of the Consumer Policy")
	private String pid;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "Property_Type")
	@ApiModelProperty(notes="Property type of the consumer applies for policy")
	private String property_type;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "Consumer_Type")
	@ApiModelProperty(notes="Type of the consumer applies for policy")
	private String consumer_type;

	@NotBlank
	@Size(max = 40)
	@Column(name = "Assured_Sum")
	@ApiModelProperty(notes="Assured sum for the consumer buisness")
	private String assured_sum;

	@NotBlank
	@Size(max = 30)
	@Column(name = "Tenure")
	@ApiModelProperty(notes="Tenure given for the consumer buisness")
	private String tenure;

	@NotNull
	@Column(name = "Business_Value")
	@ApiModelProperty(notes="Buisness value of the consumer buisness")
	private Long business_value;
	
	@NotNull
	@Column(name = "Property_Value")
	@ApiModelProperty(notes="Property value of the consumer buisness")
	private Long property_value;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "Base_Location")
	@ApiModelProperty(notes="Base location of the consumer buisness")
	private String base_location;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "Type")
	@ApiModelProperty(notes="Type of the policy")
	private String type;

}
