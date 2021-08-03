package com.cognizant.pas.policy.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.pas.policy.exception.AuthorizationException;

//@Headers("Content-Type: application/json")
//@FeignClient(name = "emp-ws", url = "${feign.url}")
@FeignClient(name = "quotes-service", url = "http://localhost:8100/quotes-api")
public interface QuotesClient {
	
	@GetMapping("/getQuotesForPolicy/{businessValue}/{propertyValue}/{propertyType}")
	public String getQuotesForPolicy
	(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @Valid @RequestParam Long businessValue,
			@RequestParam Long propertyValue, @RequestParam String propertyType)
			throws AuthorizationException;

}
