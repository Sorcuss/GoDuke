package com.goduke.exceptions;

public class DataDuplicatedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public DataDuplicatedException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
