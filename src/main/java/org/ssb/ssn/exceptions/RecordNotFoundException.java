package org.ssb.ssn.exceptions;

public class RecordNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public RecordNotFoundException(String errorMessage) {
		super(errorMessage);
		this.setErrorMessage(errorMessage);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
