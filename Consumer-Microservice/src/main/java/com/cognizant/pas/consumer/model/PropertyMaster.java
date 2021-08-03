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
@Table(name = "Property_Master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model object that stores property details.")


public class PropertyMaster {
	
	@ApiModelProperty(notes = "id of the package")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Long id;

	@ApiModelProperty(notes="type of insurance")
	@NotBlank
	@Size(max = 30)
	@Column(name = "Insurance_Type")
	private String insurancetype;
	
	@ApiModelProperty(notes="type of property")
	@NotBlank
	@Size(max = 30)
	@Column(name = "Property_Type")
	private String propertytype;

	@ApiModelProperty(notes="type of building")
	@NotBlank
	@Size(max = 7)
	@Column(name = "Building_Type")
	private String buildingtype;

	@ApiModelProperty(notes="age of building")
	@NotNull
	@Column(name = "Building_Age")
	private Long buildingage;

}
