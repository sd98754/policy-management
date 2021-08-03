package com.cognizant.pas.consumer.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class ExceptionDetailsTest {

	private ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(),HttpStatus.ACCEPTED, "message");
	
	@Test
	void testMessageSetter() {
		details.setMessage("new message");
		assertThat(details.getMessage().equals("new message"));
		
	}
	
	@Test
	void testTimeStampSetter() {
		
		LocalDateTime date = LocalDateTime.now();
		details.setTimeStamp(date);
		assertThat(details.getTimeStamp().equals(date));
		
	}
	
	@Test
	void testStatus() {
		
		HttpStatus statusCode = HttpStatus.ACCEPTED;
		details.setStatus(statusCode);
		assertThat(details.getStatus().equals(statusCode));
		
	}
	
	
}
