package com.cognizant.pas.policy.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class GlobalExceptionHandlerTest {

	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Mock
	ExceptionDetails customErrorResponse;
	
	@BeforeEach
	void setUp() {
		customErrorResponse = new ExceptionDetails(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, "test message");
	}
	
	@Test
	void handleAuthorizationExceptionTest() {
		AuthorizationException e = new AuthorizationException("message");
		globalExceptionHandler.handleAuthorizationException(e);
		ResponseEntity<?> entity = new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
		assertEquals(401, entity.getStatusCodeValue());
	}
	
	@Test
	void handleConsumerBusinessNotFoundExceptionTest() {
		ConsumerBusinessNotFoundException e = new ConsumerBusinessNotFoundException("message");
		globalExceptionHandler.handleConsumerBusinessNotFoundException(e);
		ResponseEntity<?> entity = new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
		assertEquals(401, entity.getStatusCodeValue());
	}
	
	@Test
	void handlePolicyNotFoundExceptionTest() {
		PolicyNotFoundException e = new PolicyNotFoundException("message");
		globalExceptionHandler.handlePolicyNotFoundException(e);
		ResponseEntity<?> entity = new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
		assertEquals(401, entity.getStatusCodeValue());
	}
	
	@Test
	void handleConsumerPolicyNotFoundExceptionTest() {
		ConsumerPolicyNotFoundException e = new ConsumerPolicyNotFoundException("message");
		globalExceptionHandler.handleConsumerPolicyNotFoundException(e);
		ResponseEntity<?> entity = new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
		assertEquals(401, entity.getStatusCodeValue());
	}
	
	@Test
	void handlesExceptionTest() {
		Exception e = new Exception("message");
		globalExceptionHandler.handleGlobalException(e, null);
		ResponseEntity<?> entity = new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
		assertEquals(401, entity.getStatusCodeValue());
	}
}