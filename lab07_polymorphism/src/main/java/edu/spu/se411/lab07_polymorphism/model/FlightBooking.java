package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.config.GlobalConfig;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public class FlightBooking extends Booking {

	private final double basePrice;    
	private Double luggageWeight;       

	public FlightBooking(String bookingId, String customerName, String travelDate,
			String destinationCity, double basePrice) {
		super(bookingId, customerName, travelDate, destinationCity);
		this.basePrice = basePrice;
	}

	public void setLuggageWeight(double luggageWeight) {
		this.luggageWeight = luggageWeight;
	}

	@Override
	public double computeTotalPrice() throws MissingInformationException, InvalidArgumentException {

		if (luggageWeight == null) {
			throw new MissingInformationException(
					"Luggage weight was not provided for booking " + getBookingId());
		}

		if (luggageWeight < GlobalConfig.MIN_LUGGAGE_WEIGHT || luggageWeight > GlobalConfig.MAX_LUGGAGE_WEIGHT) {
			throw new InvalidArgumentException(
					"Luggage weight must be between " + GlobalConfig.MIN_LUGGAGE_WEIGHT
					+ " and " + GlobalConfig.MAX_LUGGAGE_WEIGHT + " kg, got: " + luggageWeight);
		}

		return basePrice + (luggageWeight * GlobalConfig.EXTRA_LUGGAGE_RATE);
	}

}
