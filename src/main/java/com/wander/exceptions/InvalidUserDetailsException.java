package com.wander.exceptions;

/**
 * The Class InvalidUserDetailsException.
 */
public class InvalidUserDetailsException extends Exception{
	
	/** The error msg. */
	private String errorMsg;

	/**
	 * Instantiates a new invalid user details exception.
	 *
	 * @param errorMsg the error msg
	 */
	public InvalidUserDetailsException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
	
	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
}
