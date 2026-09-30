package edu.spu.se411.lab07_polymorphism.exceptions;

/**
 * Thrown when a value required to price a booking was never supplied
 * (e.g. luggage weight, distance, number of rental days).
 */
public class MissingInformationException extends Exception {

	public MissingInformationException(String message) {
		super(message);
	}

}
