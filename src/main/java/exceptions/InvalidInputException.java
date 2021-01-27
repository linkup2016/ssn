package exceptions;

public class InvalidInputException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	public InvalidInputException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
