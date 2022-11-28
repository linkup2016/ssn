/*
 * Test written using Junit 5 also called Jupiter.
 * Junit 5 is written to support java 8 and above. 
 * Use @Disabled instead of @ignore to skip a test case.
 * Learn more at "https://www.baeldung.com/junit-5"
 * testNameChecker() demonstrates group assertions. Refer the same link above for more. 
 */

package org.ssb.ssn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ssb.ssn.exceptions.InvalidInputException;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Candidate;
import org.ssb.ssn.models.Record;
import org.ssb.ssn.models.SSNApplication;
import org.ssb.ssn.services.SSNServices;
import org.ssb.ssn.utilities.SSNUtility;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SSNUtilityTest {
	@MockBean
    private SSNServices service;
	
	Candidate app = new SSNApplication();
	
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
	void testValidateSSN_NullSSNid() throws InvalidInputException {

		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateSSN(null);
		});

		String expectedMessage = "No SSN id has been entered.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateSSN_emptySSNid() throws InvalidInputException {

		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateSSN("");
		});

		String expectedMessage = "No SSN id has been entered.";
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

	@Test
	void testNameChecker() {
		String[] names = { "Solomon", "ABADI", "m", "daNieL" };
		assertAll("names", () -> assertTrue(SSNUtility.nameChecker(names[0])),
				() -> assertTrue(SSNUtility.nameChecker(names[1])), 
				() -> assertTrue(SSNUtility.nameChecker(names[2])),
				() -> assertTrue(SSNUtility.nameChecker(names[3])));
	}
	
	@Test
	void testBirthDateChecker() throws ParseException {
		String bDate = "2002-02-02";
		SSNUtility.birthDateChecker(bDate);
	}
	@Test()
	void testValidateARecord_HappyPath() throws InvalidInputException, ParseException, RecordNotFoundException {
		Record rec = new Record();
		rec.setSsn("234254520");
		rec.setFirstName("firstName");
		rec.setMiddleName("middleName");
		rec.setLastName("lastName");
		rec.setBirthDate("2001-05-01");
		
		// Faced unfinished stubbing error because of syntax error. 
		// I used when(service.fetchARecord(rec.getSsn())); instead of 
		// when(service).fetchARecord(rec.getSsn());
		
		Mockito.doReturn(new Record()).when(service).fetchARecord(rec.getSsn());
		
		SSNUtility.validateARecord(rec);
	}
	
	@Test()
	void testValidateARecord_BadSSN() throws InvalidInputException, RecordNotFoundException {
		
		Record rec = new Record();
		rec.setSsn("2342545hjko");
		rec.setFirstName("firstName");
		rec.setMiddleName("middleName");
		rec.setLastName("lastName");
		rec.setBirthDate("2001-05-01");

		Mockito.doReturn(new Record()).when(service).fetchARecord(rec.getSsn());
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateARecord(rec);
		});

		String expectedMessage = "Incorrect SSN id has been entered.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test()
	void testValidateARecord_NullSSN() throws InvalidInputException, RecordNotFoundException {
		
		Record rec = new Record();
		rec.setSsn(null);
		rec.setFirstName("firstName");
		rec.setMiddleName("middleName");
		rec.setLastName("lastName");
		rec.setBirthDate("2001-05-01");

		Mockito.doReturn(new Record()).when(service).fetchARecord(rec.getSsn());
		Exception exception = assertThrows(InvalidInputException.class, () -> {
			SSNUtility.validateARecord(rec);
		});

		String expectedMessage = "No SSN id has been entered.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
