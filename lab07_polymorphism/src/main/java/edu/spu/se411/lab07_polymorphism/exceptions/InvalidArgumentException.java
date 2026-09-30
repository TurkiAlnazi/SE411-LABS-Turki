package edu.spu.se411.lab07_polymorphism.exceptions;

/**
 * Thrown when a supplied value is outside the allowed business range
 * (e.g. luggage weight > 40kg, distance > 2000km, rental days > 30).
 */
public class InvalidArgumentException extends Exception {

	public InvalidArgumentException(String message) {
		super(message);
	}

}
