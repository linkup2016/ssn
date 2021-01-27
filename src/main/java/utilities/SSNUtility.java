package utilities;

import exceptions.InvalidInputException;
import model.Record;
import model.SSNApplication;

public class SSNUtility {

	private static String firstName;
	private static String lastName;
	private static String middleName;

	public static void validateApplication(SSNApplication app) throws InvalidInputException {
		firstName = app.getFirstName();
		lastName = app.getLastName();
		middleName = app.getMiddleName();

		if (!nameChecker(firstName)) {
			throw new InvalidInputException("Invalid first name has been entered. ");
		} else if (!nameChecker(middleName)) {
			throw new InvalidInputException("Invalid middle name has been entered. ");
		} else if (!nameChecker(lastName)) {
			throw new InvalidInputException("Invalid last name has been entered. ");
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

}
