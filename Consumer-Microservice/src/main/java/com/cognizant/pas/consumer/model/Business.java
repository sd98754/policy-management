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

@ApiModel(value = "Model object that stores the consumers business details.")
@Entity
@Table(name = "Business")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Business {
	
	@ApiModelProperty(notes = "id of the package")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Long id;

	@ApiModelProperty(notes="Id of the consumer")
	@NotNull
	@Column(name = "Consumer_ID")
	private Long consumerId;

	@ApiModelProperty(notes="category of the consumer's business")
	@NotBlank
	@Size(max = 40)
	@Column(name = "Business_Category")
	private String businesscategory;

	@ApiModelProperty(notes="type of the consumer's business")
	@NotBlank
	@Size(max = 40)
	@Column(name = "Business_Type")
	private String businesstype;

	@ApiModelProperty(notes="business turnover")
	@NotNull
	@Column(name = "Business_Turnover")
	private Long businessturnover;

	@ApiModelProperty(notes="capital invested in the business")
	@NotNull
	@Column(name = "Capital_Invested ")
	private Long capitalinvested;

	@ApiModelProperty(notes="total employees in the business")
	@NotNull
	@Column(name = "Total_Employees")
	private Long totalemployees;

	@ApiModelProperty(notes="calculated business value")
	@NotNull
	@Column(name = "Business_Value")
	private Long businessvalue;

	@ApiModelProperty(notes="age of the business")
	@NotNull
	@Column(name = "Business_Age")
	private Long businessage;

	public Business(@NotNull Long consumerId, @NotBlank @Size(max = 40) String businesscategory,
			@NotBlank @Size(max = 40) String businesstype, @NotNull Long businessturnover,
			@NotNull Long capitalinvested, @NotNull Long totalemployees, @NotNull Long businessvalue,
			@NotNull Long businessage) {
		super();
		this.consumerId = consumerId;
		this.businesscategory = businesscategory;
		this.businesstype = businesstype;
		this.businessturnover = businessturnover;
		this.capitalinvested = capitalinvested;
		this.totalemployees = totalemployees;
		this.businessvalue = businessvalue;
		this.businessage = businessage;
	}



}
