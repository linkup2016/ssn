package org.ssb.ssn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import exceptions.InvalidInputException;
import model.SSNApplication;
import utilities.SSNUtility;

@SpringBootTest
@Runwith(Junit.class)
public class SSNUtilityTest {
	
	SSNApplication app = new SSNApplication();

	@Test
	void contextLoads() {
	}
	
	@Test
	void testValidateApplication_HappyPath() throws InvalidInputException {
		app.setFirstName("firstName");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		
		SSNUtility.validateApplication(app);
	}
	
	@Test(Expected = NullPointerException.class)
	void testValidateApplication_ErrorPath1() throws InvalidInputException {
		app.setFirstName("fir89Name");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		
		SSNUtility.validateApplication(app);
	}
}
