package com.cognizant.pas.consumer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.pas.consumer.model.Business;
import com.cognizant.pas.consumer.model.Consumer;
import com.cognizant.pas.consumer.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.consumer.repository.BusinessRepository;
import com.cognizant.pas.consumer.repository.ConsumerRepository;
import com.cognizant.pas.consumer.repository.PropertyRepository;


@ExtendWith(MockitoExtension.class)
class ConsumerServiceTests {
	
	@InjectMocks
	private ConsumerServiceImpl consumerService;
	
	@MockBean
	private BusinessRepository businessRepository;

	@MockBean
	private ConsumerRepository consumerRepository;

	@MockBean
	private PropertyRepository propertyRepository;
	
	private ConsumerBusinessDetails mockConsumerBusinessDetails;
	private Consumer consumer;
	private Business business ;
	
	@BeforeEach
	public void setup() {
		mockConsumerBusinessDetails = new ConsumerBusinessDetails("fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1, (long) 1, (long) 1, "bcat", "type", (long) 12,
				(long) 13, (long) 4, (long) 15, (long) 11);
		consumer = new Consumer((long) 1,"fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1);
		business = new Business((long) 1, (long) 1, "bcat", "type", (long) 12,
				(long) 13, (long) 4, (long) 15, (long) 11);
	}
	
	@Test
    void testCalculateBusinessValue() throws Exception {
		long res=consumerService.calBusinessValue((long)1,(long)2);
		assertEquals((long)20, res);
    }
	
	@Test
    void testCalculateBusinessValueException() throws NullPointerException {
		assertThrows(NullPointerException.class, () -> {
			consumerService.calBusinessValue((long)1,(long)1);
		});
    }
	
	@Test
    void testCalculatePropertyValue() throws Exception {
		long res=consumerService.calPropertyValue((long)200,(long)20,(long)13);
		assertEquals((long)9, res);
    }
	
	@Test
    void testCalculatePropertyValueException() throws NullPointerException {
		assertThrows(NullPointerException.class, () -> {
			consumerService.calPropertyValue((long)200,(long)0,(long)13);
		});
    }
	
	/*@Test
	public void testViewConsumerBusiness() {
		
		when(consumerRepository.findById(any())).thenReturn(Optional.empty());
		
		when(businessRepository.findByConsumerId(any())).thenReturn(null);
	
		assertEquals(mockConsumerBusinessDetails, consumerService.viewConsumerBusiness((long)1));
	}*/
	

}
