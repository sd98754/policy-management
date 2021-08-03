package com.cognizant.pas.policy.service;

import static com.cognizant.pas.policy.util.PolicyConstant.CONSUMER_POLICY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.pas.policy.exception.AuthorizationException;
import com.cognizant.pas.policy.exception.ConsumerBusinessNotFoundException;
import com.cognizant.pas.policy.exception.ConsumerPolicyNotFoundException;
import com.cognizant.pas.policy.exception.PolicyNotFoundException;
import com.cognizant.pas.policy.feign.*;
import com.cognizant.pas.policy.models.ConsumerPolicy;
import com.cognizant.pas.policy.models.PolicyMaster;
import com.cognizant.pas.policy.payload.request.CreatePolicyRequest;
import com.cognizant.pas.policy.payload.request.IssuePolicyRequest;
import com.cognizant.pas.policy.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.policy.payload.response.MessageResponse;
import com.cognizant.pas.policy.payload.response.PolicyDetailsResponse;
import com.cognizant.pas.policy.payload.response.QuotesDetailsResponse;
import com.cognizant.pas.policy.repository.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	ConsumerPolicyRepository consumerPolicyRepository;

	@Autowired
	PolicyMasterRepository policyMasterRepository;

	@Autowired
	ConsumerClient consumerClient;

	@Autowired
	QuotesClient quotesclient;
	
	@Override
	public QuotesDetailsResponse getQuotes(String requestTokenHeader,Long businessValue, Long propertyValue, String propertyType) {
		log.info("Start getQuotesService");
		String quote;
		try {
			quote = quotesclient.getQuotesForPolicy(requestTokenHeader, businessValue, propertyValue, propertyType);
			log.debug("quote : {}", quote);
			log.info("End getQuotesService");
			return (new QuotesDetailsResponse(quote));
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PolicyDetailsResponse viewPolicy(Long consumerid, String policyid) throws PolicyNotFoundException,ConsumerPolicyNotFoundException {
		log.info("Start viewPolicyService");
		PolicyMaster policyMaster = policyMasterRepository.findByPid(policyid);
		log.debug("policyMaster : {}", policyMaster);
		ConsumerPolicy consumerPolicy = consumerPolicyRepository.findByConsumerid(consumerid);
		log.debug(CONSUMER_POLICY, consumerPolicy);
		PolicyDetailsResponse policyDetailsResponse = new PolicyDetailsResponse(consumerid, policyMaster.getPid(),
				policyMaster.getProperty_type(), policyMaster.getConsumer_type(), policyMaster.getAssured_sum(),
				policyMaster.getTenure(), policyMaster.getBusiness_value(), policyMaster.getProperty_value(),
				policyMaster.getBase_location(), policyMaster.getType(), consumerPolicy.getBusinessid(),
				consumerPolicy.getPaymentdetails(), consumerPolicy.getAcceptancestatus(),
				consumerPolicy.getPolicystatus(), consumerPolicy.getEffectivedate(), consumerPolicy.getCovered_sum(),
				consumerPolicy.getDuration(), consumerPolicy.getAcceptedquote());
		log.debug("policyDetailsResponse : {}", policyDetailsResponse);
		log.info("End viewPolicyService");
		return policyDetailsResponse;
	}

	@Override
	public MessageResponse createPolicy(CreatePolicyRequest createPolicyRequest, String requestTokenHeader) throws ConsumerBusinessNotFoundException {
		log.info("Start createPolicyService");
		ConsumerBusinessDetails consumerBusinessDetails = getConsumerBusiness(requestTokenHeader, createPolicyRequest.getConsumerid());
		if(consumerBusinessDetails==null)
		{
			return new MessageResponse("No Consumer Business Found !!");
		}
		log.debug("consumerBusinessDetails : {}", consumerBusinessDetails);
		ConsumerPolicy consumerPolicy = new ConsumerPolicy(consumerBusinessDetails.getConsumerId(),
				consumerBusinessDetails.getBusinessid(), "Initiated", createPolicyRequest.getAcceptedquotes());
		log.debug(CONSUMER_POLICY, consumerPolicy);
		ConsumerPolicy consumerPolicysave = consumerPolicyRepository.save(consumerPolicy);
		log.debug("consumerPolicysave : {}", consumerPolicysave);
		log.info("End createPolicyService");
		return new MessageResponse("Policy Has been Created with Policyconsumer Id : " + consumerPolicysave.getId()
				+ " .Thank You Very Much!!");
	}

	@Override
	public MessageResponse issuePolicy(IssuePolicyRequest issuePolicyRequest)throws ConsumerPolicyNotFoundException,PolicyNotFoundException  {
		log.info("Start issuePolicyService");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ConsumerPolicy consumerPolicy = consumerPolicyRepository
				.findByConsumeridAndBusinessid(issuePolicyRequest.getConsumerid(), issuePolicyRequest.getBusinessid());
		log.debug(CONSUMER_POLICY, consumerPolicy);
		PolicyMaster policyMaster = policyMasterRepository.findByPid(issuePolicyRequest.getPolicyid());
		log.debug("policyMaster : {}", policyMaster);
		consumerPolicy.setPolicyid(issuePolicyRequest.getPolicyid());
		consumerPolicy.setPaymentdetails(issuePolicyRequest.getPaymentdetails());
		consumerPolicy.setAcceptancestatus(issuePolicyRequest.getAcceptancestatus());
		consumerPolicy.setPolicystatus("Issued");
		consumerPolicy.setEffectivedate(dtf.format(now));
		consumerPolicy.setDuration(policyMaster.getTenure());
		consumerPolicy.setCovered_sum(policyMaster.getAssured_sum());
		ConsumerPolicy consumerPolicySave = consumerPolicyRepository.save(consumerPolicy);
		log.debug("consumerPolicySave : {}", consumerPolicySave);
		log.info("End issuePolicyService");
		return new MessageResponse("Policy has Issued to PolicyConsumer Id : " + consumerPolicySave.getId()
				+ " .Thank You Very Much!!");
	}

	@Override
	public ConsumerBusinessDetails getConsumerBusiness(String requestTokenHeader, Long consumerid) {
		log.info("Start getConsumerBusiness");
		ConsumerBusinessDetails consumerBusinessDetails;
		try {
			consumerBusinessDetails = consumerClient.viewConsumerBusinessbypolicy(consumerid, requestTokenHeader);
			log.debug("consumerBusinessDetails : {}", consumerBusinessDetails);
			log.info("End getConsumerBusiness");
			return consumerBusinessDetails;
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
