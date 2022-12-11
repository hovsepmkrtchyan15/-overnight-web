package com.example.overnightweb.exception;

public class UserNotFoundException extends IllegalArgumentException {

	private static final int serialVersionUID = 1;

	public UserNotFoundException(int id) {
		super(String.format("User with Id %d not found", id));
	}
}