package com.cognizant.pas.policy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.pas.policy.feign.AuthorisingClient;
import com.cognizant.pas.policy.feign.ConsumerClient;
import com.cognizant.pas.policy.models.ConsumerPolicy;
import com.cognizant.pas.policy.models.PolicyMaster;
import com.cognizant.pas.policy.payload.request.CreatePolicyRequest;
import com.cognizant.pas.policy.payload.request.IssuePolicyRequest;
import com.cognizant.pas.policy.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.policy.payload.response.MessageResponse;
import com.cognizant.pas.policy.payload.response.PolicyDetailsResponse;
import com.cognizant.pas.policy.payload.response.QuotesDetailsResponse;
import com.cognizant.pas.policy.repository.ConsumerPolicyRepository;
import com.cognizant.pas.policy.repository.PolicyMasterRepository;
import com.cognizant.pas.policy.service.PolicyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PolicyController.class)
class PolicyControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorisingClient authorisingClient;

	@MockBean
	private ConsumerClient consumerClient;

	@MockBean
	private PolicyService policyService;

	@MockBean
	private ConsumerPolicyRepository consumerPolicyRepository;

	@MockBean
	PolicyMasterRepository policyMasterRepository;

	ConsumerBusinessDetails mockConsumerBusinessDetails;
	CreatePolicyRequest createPolicyRequest;
	ObjectMapper objectMapper;
	MessageResponse messageResponse;
	IssuePolicyRequest issuePolicyRequest;
	ConsumerPolicy consumerPolicy;
	PolicyMaster policyMaster;
	PolicyDetailsResponse policyDetailsResponse;

	@BeforeEach
	public void setup() {
		mockConsumerBusinessDetails = new ConsumerBusinessDetails("fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1, (long) 1, (long) 1, "bcat", "type", (long) 12,
				(long) 13, (long) 4, (long) 15, (long) 11);
		createPolicyRequest = new CreatePolicyRequest((long) 1, "quotes");
		objectMapper = new ObjectMapper();
		issuePolicyRequest = new IssuePolicyRequest("P09", (long)1, (long)1, "Success", "Accepted");
		consumerPolicy = new ConsumerPolicy((long) 1, (long) 1, "P09", (long) 1, "Success", "Accepted", "Issued", "2021/08/02 21:09:38", "3,00,000", "11 week", "54000 INR");
		policyMaster = new PolicyMaster((long) 1, "P09", "Building", "Rented", "3,00,000", "11 week", (long) 4, (long) 7, "Chennai", "Burglary");
		policyDetailsResponse = new PolicyDetailsResponse((long) 1, "P09", "Building", "Rented", "3,00,000", "11 week", (long) 4, (long) 7, "Chennai", "Burglary", (long) 1, "Success", "Accepted", "Issued", "2021/08/02 21:09:38", "3,00,000", "11 week", "54000 INR");

	}

	@Test
	@DisplayName("Test Authorising client")
	void testClientNotNull() {
		assertThat(authorisingClient).isNotNull();
	}

	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test PolicyServiceImpl client")
	void testServiceNotNull() {
		assertThat(policyService).isNotNull();
	}

	@Test
	void testCreatePolicy_valid() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		when(consumerClient.viewConsumerBusinessbypolicy((long) 1, "@uthoriz@tionToken123"))
				.thenReturn(mockConsumerBusinessDetails);

		messageResponse = new MessageResponse(
				"Policy Has been Created with Policyconsumer Id : " + 1 + " .Thank You Very Much!!");

		when(policyService.createPolicy(createPolicyRequest, "@uthoriz@tionToken123")).thenReturn(messageResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createPolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createPolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	void testCreatePolicy_invalid() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);

		when(consumerClient.viewConsumerBusinessbypolicy((long) 1, "@uthoriz@tionToken123"))
				.thenReturn(mockConsumerBusinessDetails);

		messageResponse = new MessageResponse(
				"Policy Has been Created with Policyconsumer Id : " + 1 + " .Thank You Very Much!!");

		when(policyService.createPolicy(createPolicyRequest, "@uthoriz@tionToken123")).thenReturn(messageResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createPolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createPolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		assertEquals(403, response.getStatus());
	}
	
	@Test
	void testIssuePolicy_policy_not_found() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		when(consumerPolicyRepository.existsByConsumerid(issuePolicyRequest.getConsumerid())).thenReturn(true);
		
		//messageResponse = new MessageResponse("Sorry!!, No Policy Found!!");
		
		when(policyMasterRepository.existsByPid(issuePolicyRequest.getPolicyid())).thenReturn(false);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
		assertEquals("{\"message\":\"Sorry!!, No Policy Found!!\"}", response.getContentAsString());
		
	}
	
	@Test
	void testIssuePolicy_consumer_not_found() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		when(consumerPolicyRepository.existsByConsumerid(issuePolicyRequest.getConsumerid())).thenReturn(false);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
		assertEquals("{\"message\":\"Sorry!!, No Consumer Found!!\"}", response.getContentAsString());
		
	}
	
	@Test
	void testIssuePolicy_payment_failed1() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		//messageResponse = new MessageResponse("Sorry!!, Payment Failed!! Try Again");
		issuePolicyRequest.setPaymentdetails("f");
		
		when(consumerPolicyRepository.existsByConsumerid(issuePolicyRequest.getConsumerid())).thenReturn(true);
		
		when(policyMasterRepository.existsByPid(issuePolicyRequest.getPolicyid())).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
		assertEquals("{\"message\":\"Sorry!!, Payment Failed!! Try Again\"}", response.getContentAsString());
	}
	
	@Test
	void testIssuePolicy_valid_acceptance_failed() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		//messageResponse = new MessageResponse("Sorry!!, Payment Failed!! Try Again");
		issuePolicyRequest.setAcceptancestatus("f");
		
		when(consumerPolicyRepository.existsByConsumerid(issuePolicyRequest.getConsumerid())).thenReturn(true);
		
		when(policyMasterRepository.existsByPid(issuePolicyRequest.getPolicyid())).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
		assertEquals("{\"message\":\"Sorry!!, Accepted Failed !! Try Again\"}", response.getContentAsString());
	}
	
	@Test
	void testIssuePolicy_ok() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		messageResponse = new MessageResponse("Policy has Issued to PolicyConsumer Id : " + 1
		+ " .Thank You Very Much!!");
		
		when(consumerPolicyRepository.existsByConsumerid(issuePolicyRequest.getConsumerid())).thenReturn(true);
		
		when(policyMasterRepository.existsByPid(issuePolicyRequest.getPolicyid())).thenReturn(true);
		
		when(consumerPolicyRepository
		.findByConsumeridAndBusinessid(issuePolicyRequest.getConsumerid(), issuePolicyRequest.getBusinessid())).thenReturn(consumerPolicy);
		
		when(policyMasterRepository.findByPid(issuePolicyRequest.getPolicyid())).thenReturn(policyMaster);
		
		when(policyService.issuePolicy(issuePolicyRequest)).thenReturn(messageResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
	}
	
	@Test
	void testIssuePolicy_invalid() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuePolicy")
				.header("Authorization", "@uthoriz@tionToken123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(issuePolicyRequest));
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(403, response.getStatus());
	}
	
	@Test
	void testViewQuotesValid() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		QuotesDetailsResponse quotesDetailsResponse = new QuotesDetailsResponse("54000 INR");
		
		when(policyService.getQuotes("@uthoriz@tionToken123", (long) 1, (long) 1,
					"Building")).thenReturn(quotesDetailsResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getQuotes/1/1/Building")
				.header("Authorization", "@uthoriz@tionToken123");
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());
		
	}

}
