package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import exceptions.InvalidInputException;
import model.Record;
import model.SSNApplication;

public class SSNUtility {

	private static String firstName;
	private static String lastName;
	private static String middleName;
	private static String birthDate;

	public static void validateApplication(SSNApplication app) throws InvalidInputException, ParseException {
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
		}else if (!birthDateChecker(birthDate)) {
			throw new InvalidInputException("Invalid birth date has been entered. ");
		}
	}

	public static boolean validateSSN(String ssn) {
		return false;
	}

	public static boolean validateARecord(Record toUpdate) {
		return false;
	}

	public static boolean validateSex(String sex) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean nameChecker(String name) {
		return ((!name.equals("")) && (name != null) && (name.matches("^[a-zA-Z]*$")));
	}
	
	public static boolean birthDateChecker(String birthDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String today = sdf.format(java.time.LocalDateTime.now()); 
    
        return sdf.parse(birthDate).before(sdf.parse(today));		
	}
}
