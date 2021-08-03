package com.cognizant.pas.quotes.models;

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
@Table(name="Quotes_Master")
@Getter
@ApiModel(value = "Model object that stores the Quotes information.")
@AllArgsConstructor
@NoArgsConstructor
public class QuotesMaster  {
	
	@ApiModelProperty(notes="Id of the Quotes of policy")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Long id;
	
	@NotNull
	@Column(name = "Business_Value")
	@ApiModelProperty(notes="Buisness value of the given consumer policy")
	private Long businessValue;

	@NotNull
	@Column(name = "Property_Value")
	@ApiModelProperty(notes="Property value of the given consumer policy")
	private Long propertyValue;

	@NotBlank
	@Size(max = 50)
	@Column(name = "Property_Type")
	@ApiModelProperty(notes="Property Type of the given consumer policy")
	private String propertyType;
	
	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(notes="Quotes for the given consumer policy")
	@Column(name = "Quotes")
	private String quotes;
	
	

}
