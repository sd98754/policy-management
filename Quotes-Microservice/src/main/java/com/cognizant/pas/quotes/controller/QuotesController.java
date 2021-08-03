package com.cognizant.pas.quotes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pas.quotes.models.QuotesMaster;
import com.cognizant.pas.quotes.repository.QuotesMasterRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.cognizant.pas.quotes.exception.*;
import com.cognizant.pas.quotes.feign.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class QuotesController {
	
	@Autowired
	QuotesMasterRepository quotesMasterRepository;
	
	@Autowired
	private AuthorisingClient authorisingClient;
	
	/**
	 * This method returns the quotes for the given policy id using the parameters 
	 * business value, property value and property type of the consumer business.
	 * @param requestTokenHeader
	 * @param businessValue
	 * @param propertyValue
	 * @param propertyType
	 * @return
	 * @throws AuthorizationException
	 */	
	@GetMapping("/getQuotesForPolicy/{businessValue}/{propertyValue}/{propertyType}")
	@ApiOperation(notes="Gets the quote for the given policy ID",value="Gets quote for the policy")
	public String getQuotesForPolicy(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name = "businessValue", value = "Value of the Business") @Valid @PathVariable Long businessValue,
			@ApiParam(name = "propertyValue", value = "Value of the Property") @PathVariable Long propertyValue,
			@ApiParam(name = "propertyType", value = "Type of the Property") @PathVariable String propertyType)
			throws AuthorizationException{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			log.info("Start");
			String quotes;
			try
	        {
			QuotesMaster quotesMaster=quotesMasterRepository
					.findByBusinessValueAndPropertyValueAndPropertyType(businessValue, propertyValue, propertyType);
			log.debug("QuotesMaster: {}", quotesMaster);
			quotes=quotesMaster.getQuotes();
	        }catch(NullPointerException e) 
	        { 
	        	quotes= "No Quotes, Contact Insurance Provider";
	        	return quotes;  
	        } 
			log.info("End");
			return quotes; 
			
		} else {
			throw new AuthorizationException("Not allowed");
		}
		
	}	

}