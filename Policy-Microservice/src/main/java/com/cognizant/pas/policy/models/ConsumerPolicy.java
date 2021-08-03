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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Consumer_Policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Model object that stores the consumer's policy details.")

public class ConsumerPolicy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	@ApiModelProperty(notes="Id of the Consumer policy")
	private Long id;
	
	@NotNull
	@Column(name = "Consumer_ID")
	@ApiModelProperty(notes="Id of the Consumer")
	private Long consumerid;
	
	@Size(max = 10)
	@Column(name = "Policy_ID")
	@ApiModelProperty(notes="Id of the policy")
	private String policyid;
	
	@NotNull
	@Column(name = "Business_ID")
	@ApiModelProperty(notes="Id of the Consumer Buisness")
	private Long businessid;
	
	@Size(max = 10)
	@Column(name = "Payment_Details")
	@ApiModelProperty(notes="Payment Details of the Consumer")
	private String paymentdetails;
	
	@Size(max = 10)
	@Column(name = "Acceptance_Status")
	@ApiModelProperty(notes="Acceptance status of the policy")
	private String acceptancestatus;
	
	@NotBlank
	@Size(max = 10)
	@Column(name = "Policy_Status")
	@ApiModelProperty(notes="Status of the policy")
	private String policystatus;
	
	@Size(max = 20)
	@Column(name = "Effective_Date")
	@ApiModelProperty(notes="Effective of the date")
	private String effectivedate;
	
	@Size(max = 15)
	@Column(name = "Covered_Sum")
	@ApiModelProperty(notes="Covered sum of the policy for the consumer buisness")
	private String covered_sum;
	
	
	@Size(max = 15)
	@Column(name = "Duration")
	@ApiModelProperty(notes="Duration of the consumer buisness")
	private String duration;
	
	@NotBlank
	@Size(max = 15)
	@Column(name = "Accepted_Quotes")
	@ApiModelProperty(notes="Accepted quotes for the consumer buisness")
	private String acceptedquote;
	
	/***
	 * 
	 * @param consumerid
	 * @param businessid
	 * @param policystatus
	 * @param acceptedquote
	 */
	public ConsumerPolicy(@NotNull Long consumerid, @NotNull Long businessid,
			@NotBlank @Size(max = 10) String policystatus, @NotBlank @Size(max = 15) String acceptedquote) {
		super();
		this.consumerid = consumerid;
		this.businessid = businessid;
		this.policystatus = policystatus;
		this.acceptedquote = acceptedquote;
	}



	


	
	
}
