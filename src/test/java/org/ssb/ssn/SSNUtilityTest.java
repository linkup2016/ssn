/*
 * Test written using Junit 5 also called Jupiter.
 * Junit 5 is written to support java 8 and above. 
 * Use @Disabled instead of @ignore to skip a test case.
 * Learn more at "https://www.baeldung.com/junit-5"
 */

package org.ssb.ssn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import exceptions.InvalidInputException;
import model.SSNApplication;
import utilities.SSNUtility;

@SpringBootTest
public class SSNUtilityTest {
	
	SSNApplication app = new SSNApplication();
	@Test
	void testValidateApplication_HappyPath() throws InvalidInputException, ParseException {
		app.setFirstName("firstName");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		app.setBirthDate("01/01/2001");

		SSNUtility.validateApplication(app);
	}
	
	@Test()
	void testValidateApplication_ErrorPath1() throws InvalidInputException {
		app.setFirstName("fir89Name");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		
		Throwable exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateApplication(app);
		    });
		    assertEquals(exception.getMessage(), "Invalid first name has been entered. ");
	}
}
