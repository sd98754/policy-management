package com.cognizant.pas.policy.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.pas.policy.feign.AuthorisingClient;

@SpringBootTest
class AuthorizationExceptionTest {
	
	@MockBean
	AuthorisingClient authorisingClient;
	
	private AuthorizationException e = new AuthorizationException("message");
	@Test
	void testMessageSetter() {
		assertThat(e).isNotNull();
	}	
}
