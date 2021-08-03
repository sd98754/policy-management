package com.cognizant.pas.consumer.controller;

import static com.cognizant.pas.consumer.util.ConsumerConstant.NOT_ALLOWED;
import static com.cognizant.pas.consumer.util.ConsumerConstant.BUSINESS_NOT_FOUND;
import static com.cognizant.pas.consumer.util.ConsumerConstant.CONSUMER_NOT_FOUND;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pas.consumer.model.Property;
import com.cognizant.pas.consumer.payload.request.BusinessPropertyRequest;
import com.cognizant.pas.consumer.payload.request.ConsumerBusinessRequest;
import com.cognizant.pas.consumer.payload.response.BusinessPropertyDetails;
import com.cognizant.pas.consumer.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.consumer.payload.response.MessageResponse;
import com.cognizant.pas.consumer.repository.BusinessRepository;
import com.cognizant.pas.consumer.repository.ConsumerRepository;
import com.cognizant.pas.consumer.repository.PropertyRepository;
import com.cognizant.pas.consumer.service.ConsumerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.cognizant.pas.consumer.exception.AuthorizationException;
import com.cognizant.pas.consumer.feign.AuthorisingClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class ConsumerController {
	
	@Autowired
	private AuthorisingClient authorisingClient;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private BusinessRepository businessRepository;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private PropertyRepository propertyRepository;
	
	/**
	 * This method returns the consumer business is eligible for insurance or not.
	 * @param consumerBusinessRequest
	 * @param requestTokenHeader
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/createConsumerBusiness")
	@ApiOperation(notes="creates the consumer business if they are eligible for insurance", value="creates consumer business")
	public MessageResponse createConsumerBusiness(
			@ApiParam(name = "consumerBusinessRequest", value = "Details of the Consumer and Business") @Valid @RequestBody ConsumerBusinessRequest consumerBusinessRequest,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		log.info("Start createConsumerBusiness");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!consumerService.checkBusinessEligibility(consumerBusinessRequest)) {
				return (new MessageResponse("Sorry!!, You are Not Eligibile for Insurance"));
			}
			MessageResponse messageResponse = consumerService.createConsumerBusiness(consumerBusinessRequest);
			log.debug("ConsumerBusiness Status: {}", messageResponse);
			log.info("End createConsumerBusiness");
			return (messageResponse);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}
	
	/***
	 * This method is to update the consumer business details by using consumer id or business id.
	 * @param consumerBusinessDetails
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 
	 */
	@PostMapping("/updateConsumerBusiness")
	@ApiOperation(notes="updates consumer business details", value="updates consumer business")
	public MessageResponse updateConsumerBusiness(
			@ApiParam(name = "consumerBusinessDetails", value = "Details of the Consumer and Business") @Valid @RequestBody ConsumerBusinessDetails consumerBusinessDetails,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
		log.info("Start updateConsumerBusiness");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!consumerRepository.existsById(consumerBusinessDetails.getConsumerId())) {
				return (new MessageResponse(CONSUMER_NOT_FOUND));
			}
			if (!businessRepository.existsByConsumerId(consumerBusinessDetails.getConsumerId())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			if (!businessRepository.existsById(consumerBusinessDetails.getBusinessid())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			MessageResponse messageResponse = consumerService.updateConsumerBusiness(consumerBusinessDetails);
			log.debug("ConsumerBusiness Status: {}", messageResponse);
			log.info("End updateConsumerBusiness");
			return (messageResponse);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}

	/***
	 * This method is to view the consumer business details by using the consumer id.
	 * @param consumerid
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 */
	@GetMapping("/viewConsumerBusiness/{consumerid}")
	@ApiOperation(notes="returns the consumer business based on the given consumer" , value="displays the consumer business")
	public ResponseEntity<?> viewConsumerBusiness(
			@ApiParam(name = "consumerid", value = "Id of the Consumer") @Valid @PathVariable Long consumerid, 
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
		log.info("Start viewConsumerBusiness");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!consumerRepository.existsById(consumerid)) {
				return ResponseEntity.badRequest().body(new MessageResponse(CONSUMER_NOT_FOUND));
			}
			if (!businessRepository.existsByConsumerId(consumerid)) {
				return ResponseEntity.badRequest().body(new MessageResponse(BUSINESS_NOT_FOUND));
			}
			ConsumerBusinessDetails consumerBusinessDetails = consumerService.viewConsumerBusiness(consumerid);
			log.debug("ConsumerBusiness Details: {}", consumerBusinessDetails);
			log.info("End viewConsumerBusiness");
			return ResponseEntity.ok(consumerBusinessDetails);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}
	
	/***
	 * This method is to create a business property based on consumer and business details.
	 * @param businessPropertyRequest
	 * @param requestTokenHeader
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/createBusinessProperty")
	@ApiOperation(notes="creates business prperty based on consumer and business", value="creates business property")
	public MessageResponse createBusinessProperty(
			@ApiParam(name = "businessPropertyRequest", value = "Details of the Property and Business") @Valid @RequestBody BusinessPropertyRequest businessPropertyRequest,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		log.info("Start createBusinessProperty");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!consumerRepository.existsById(businessPropertyRequest.getConsumerId())) {
				return (new MessageResponse(CONSUMER_NOT_FOUND));
			}
			if (!businessRepository.existsByConsumerId(businessPropertyRequest.getConsumerId())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			if (!businessRepository.existsById(businessPropertyRequest.getBusinessId())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			if (!consumerService.checkPropertyEligibility(businessPropertyRequest.getPropertytype(),
					businessPropertyRequest.getInsurancetype(), businessPropertyRequest.getBuildingtype(),businessPropertyRequest.getBuildingage())) {
				return (new MessageResponse("Sorry!!, You are Not Eligibile for Insurance"));
			}
			MessageResponse messageResponse = consumerService.createBusinessProperty(businessPropertyRequest);
			log.debug("BusinessProperty Status: {}", messageResponse);
			log.info("End createBusinessProperty");
			return (messageResponse);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}
	
	/***
	 * This method is to update the business property details of the consumer.
	 * @param businessPropertyDetails
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 */
	@PostMapping("/updateBusinessProperty")
	@ApiOperation(notes="updates the business property details", value="updates business property")
	public MessageResponse updateBusinessProperty(
			@ApiParam(name = "businessPropertyDetails", value = "Details of the Property and Business") @Valid @RequestBody BusinessPropertyDetails businessPropertyDetails,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
		log.info("Start updateBusinessProperty");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!propertyRepository.existsById(businessPropertyDetails.getPropertyId())) {
				return (new MessageResponse("Sorry!!, No Property Found!!"));
			}
			if (!consumerRepository.existsById(businessPropertyDetails.getConsumerId())) {
				return (new MessageResponse(CONSUMER_NOT_FOUND));
			}
			if (!businessRepository.existsByConsumerId(businessPropertyDetails.getConsumerId())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			if (!businessRepository.existsById(businessPropertyDetails.getBusinessId())) {
				return (new MessageResponse(BUSINESS_NOT_FOUND));
			}
			MessageResponse messageResponse = consumerService.updateBusinessProperty(businessPropertyDetails);
			log.debug("BusinessProperty Status: {}", messageResponse);
			log.info("End updateBusinessProperty");
			return (messageResponse);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}
	
	/***
	 * This method is to view the consumer property based on consumer id and property id.
	 * @param consumerid
	 * @param propertyid
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 */
	@GetMapping("/viewConsumerProperty/{consumerid}/{propertyid}")
	@ApiOperation(notes="returns the consumer property based on the given consumer and property", value="displys the consumer property")
	public ResponseEntity<?> viewConsumerProperty(
			@ApiParam(name = "consumerid", value = "Id of the Consumer") @Valid @PathVariable Long consumerid,
			@ApiParam(name = "propertyid", value = "Id of the Property") @PathVariable Long propertyid,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
		log.info("Start viewConsumerProperty");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			if (!propertyRepository.existsById(propertyid)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Sorry!!, No Property Found!!"));
			}
			if (!consumerRepository.existsById(consumerid)) {
				return ResponseEntity.badRequest().body(new MessageResponse(CONSUMER_NOT_FOUND));
			}
			if (!businessRepository.existsByConsumerId(consumerid)) {
				return ResponseEntity.badRequest().body(new MessageResponse(BUSINESS_NOT_FOUND));
			}
			Property property = consumerService.viewConsumerProperty(consumerid, propertyid);
			log.debug("BusinessProperty Details: {}", property);
			log.info("End viewConsumerProperty");
			return ResponseEntity.ok(property);
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
			}
	
	/***
	 * This method is to view the consumer business policy by using the consumer id.
	 * @param consumerid
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException 
	 */
	@GetMapping("/viewConsumerBusinessByPolicy/{consumerid}")
	@ApiOperation(notes="returns the consumer business by policy", value="displays consumer business by policy")
	public ConsumerBusinessDetails viewConsumerBusinessbypolicy(
			@ApiParam(name = "consumerid", value = "Id of the Consumer") @Valid @PathVariable Long consumerid,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
		log.info("Start viewConsumerBusinessByPolicy");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ConsumerBusinessDetails consumerBusinessDetails = consumerService.viewConsumerBusiness(consumerid);
			log.debug("ConsumerBusiness Details: {}", consumerBusinessDetails);
			log.info("End viewConsumerBusinessByPolicy");
			return consumerBusinessDetails;
		} else {
			throw new AuthorizationException(NOT_ALLOWED);
		}
	}

}
