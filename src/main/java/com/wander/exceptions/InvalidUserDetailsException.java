package com.wander.exceptions;

public class InvalidUserDetailsException extends Exception{
	
	private String errorMsg;

	public InvalidUserDetailsException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
}
