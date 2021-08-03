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
@Table(name = "Business_Master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model object that stores the business details.")

public class BusinessMaster {
	
	@ApiModelProperty(notes = "id of the package")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Long id;

	@ApiModelProperty(notes="category of the business")
	@NotBlank
	@Size(max = 40)
	@Column(name = "Business_Category")
	private String businesscategory;
	
	@ApiModelProperty(notes="type of business")
	@NotBlank
	@Size(max = 40)
	@Column(name = "Business_Type")
	private String businesstype;
	
	@ApiModelProperty(notes="number of employees in the business")
	@NotNull
	@Column(name = "Total_Employees")
	private Long totalemployees;
	
	@ApiModelProperty(notes="age of the business")
	@NotNull
	@Column(name = "Business_Age")
	private Long businessage;

}
