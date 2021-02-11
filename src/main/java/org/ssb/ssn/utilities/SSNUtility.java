/*
 * The birthDateChecker() - https://www.baeldung.com/java-string-to-date
 * Date formatter : https://mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
 * The validateSSN() - https://www.baeldung.com/java-check-string-number
 */

package org.ssb.ssn.utilities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssb.ssn.data.DataSource;
import org.ssb.ssn.exceptions.InvalidInputException;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Candidate;
import org.ssb.ssn.models.Record;
import org.ssb.ssn.services.SSNServices;

public class SSNUtility {

	private static String firstName;
	private static String lastName;
	private static String middleName;
	private static String birthDate;
	
	@Autowired
	private static DataSource data;
	
	public static void validateApplication(Candidate app) throws InvalidInputException, ParseException {
		firstName = app.getFirstName();
		lastName = app.getLastName();
		middleName = app.getMiddleName();
		birthDate = app.getBirthDate();

		if (!nameChecker(firstName)) {
			throw new InvalidInputException("Invalid first name has been entered. ");
		} else if (!nameChecker(middleName)) {
			throw new InvalidInputException("Invalid middle name has been entered. ");
		} else if (!nameChecker(lastName)) {
			throw new InvalidInputException("Invalid last name has been entered. ");
		} else if (!birthDateChecker(birthDate)) {
			throw new InvalidInputException("Invalid birth date has been entered. ");
		}
	}

	public static void validateSSN(String ssn) throws InvalidInputException {
		if (ssn == null || ssn.isEmpty()) {
			throw new InvalidInputException("No SSN id has been entered.");
		}
		if (ssn.length() != 9) {
			throw new InvalidInputException("Incorrect SSN id has been entered.");
		}
		try {
			Integer.parseInt(ssn);
		} catch (NumberFormatException nfe) {
			throw new InvalidInputException("SSN id contains disallowed characters.");
		}
	}

	public static boolean validateARecord(Record toUpdate) throws InvalidInputException, ParseException, RecordNotFoundException {
		String id = toUpdate.getSsn();
		validateSSN(id);
		SSNServices ser = new SSNServices(data);
		if (ser.fetchARecord(id) != null) {
			validateApplication(toUpdate);
		}
		return false;
	}

	public static boolean validateSex(String sex) {
		if (sex.equalsIgnoreCase("male") || sex.equalsIgnoreCase("female")) {
			return true;
		}
		return false;
	}

	public static boolean nameChecker(String name) {
		return ((!name.equals("")) && (name != null) && (name.matches("^[a-zA-Z]*$")));
	}

	public static boolean birthDateChecker(String birthDate) throws ParseException {

		LocalDate bDate = LocalDate.parse(birthDate); // convert birthDate into a LocalDate type.
		LocalDate today = LocalDate.now();		
		
		return today.isAfter(bDate);
	}
}
