package com.cognizant.pas.quotes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cognizant.pas.quotes.feign.AuthorisingClient;
import com.cognizant.pas.quotes.models.QuotesMaster;
import com.cognizant.pas.quotes.repository.QuotesMasterRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = QuotesController.class)
class QuotesMicroserviceControllerTest {

	@MockBean
	AuthorisingClient authorisingClient;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private QuotesMaster quotes;

	@MockBean
	private QuotesMasterRepository quotesMasterRepository;
	
	@InjectMocks
	private QuotesController quotesController;
	
	@BeforeEach
	public void setup() {
		quotes = new QuotesMaster((long) 11, (long) 1, (long) 1, "type", "quotes");
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
	@DisplayName("Test QuotesMasterRepository client")
	void testServiceNotNull() {
		assertThat(quotesMasterRepository).isNotNull();
	}
	
	@Test
	 void getQuotesForPolicy() throws Exception {
		
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		Mockito.when(quotesMasterRepository.findByBusinessValueAndPropertyValueAndPropertyType(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())).thenReturn(quotes);

		this.mockMvc
		.perform(get("/getQuotesForPolicy/1/1/type")
				.header("Authorization", "@uthoriz@tionToken123"))
		.andExpect(status().isOk()).andExpect(content().string("quotes"));
	}

	@Test
	 void getQuotesForPolicyError() throws Exception {
		
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(quotesMasterRepository.findByBusinessValueAndPropertyValueAndPropertyType(Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(new NullPointerException("No Quotes, Contact Insurance Provider"));

		this.mockMvc.perform(get("/getQuotesForPolicy/1/1/type")
				.header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		
	}

}
