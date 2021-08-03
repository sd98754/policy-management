package com.cognizant.pas.quotes.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.cognizant.pas.quotes.feign.AuthorisingClient;

@SpringBootTest
//@ContextConfiguration("/applicationContext.xml")
class AuthorizationExceptionTest {
	
	@MockBean
	AuthorisingClient authorisingClient;
	
	private AuthorizationException e = new AuthorizationException("message");
	@Test
	void testMessageSetter() {
		assertThat(e).isNotNull();
	}	
}
