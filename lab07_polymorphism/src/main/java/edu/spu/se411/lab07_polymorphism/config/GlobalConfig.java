package edu.spu.se411.lab07_polymorphism.config;

/**
 * Central place for every business constant used by the pricing rules.
 * Keeping them here means a rate/limit change never touches the booking
 * classes themselves.
 */
public final class GlobalConfig {

	private GlobalConfig() {
	}

	// Flight
	public static final double EXTRA_LUGGAGE_RATE = 5.0;   // currency / kg
	public static final double MIN_LUGGAGE_WEIGHT = 0.0;   // kg
	public static final double MAX_LUGGAGE_WEIGHT = 40.0;  // kg

	// Train
	public static final double TRAIN_STANDARD_RATE = 0.15;     // currency/ km
	public static final double TRAIN_FIRST_CLASS_RATE = 0.35;  // currency per km
	public static final double MIN_TRAIN_DISTANCE = 1.0;       //km
	public static final double MAX_TRAIN_DISTANCE = 2000.0;    // Km

	// Car rental
	public static final int MIN_RENTAL_DAYS = 1;
	public static final int MAX_RENTAL_DAYS = 30;

}
