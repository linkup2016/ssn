/*
 * Test written using Junit 5 also called Jupiter.
 * Junit 5 is written to support java 8 and above. 
 * Use @Disabled instead of @ignore to skip a test case.
 * Learn more at "https://www.baeldung.com/junit-5"
 */

package org.ssb.ssn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.Disabled;
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
		app.setBirthDate("2001-05-01");

		SSNUtility.validateApplication(app);
	}

	// @Disabled
	@Test()
	void testValidateApplication_InvalidNames() throws InvalidInputException {
		app.setFirstName("fi67tName");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		app.setBirthDate("2021-05-01");

		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateApplication(app);
		});

		String expectedMessage = "Invalid first name has been entered. ";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test()
	void testValidateApplication_InvalidBirthDate() throws InvalidInputException {
		app.setFirstName("firstName");
		app.setMiddleName("middleName");
		app.setLastName("lastName");
		app.setBirthDate("2021-05-01");

		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateApplication(app);
		});

		String expectedMessage = "Invalid birth date has been entered. ";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateSSN_HappyPath() throws InvalidInputException {
		String id = "123456789";
		SSNUtility.validateSSN(id);
	}
	
	@Test
	void testValidateSSN_InvalidSSNid() throws InvalidInputException {
		String id = "123re@678";
		
		
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateSSN(id);
		});

		String expectedMessage = "SSN id contains disallowed characters.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateSSN_IncorrectNumberOfDigits() throws InvalidInputException {
		String id = "0123456789";
		
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateSSN(id);
		});

		String expectedMessage = "Incorrect SSN id has been entered.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
