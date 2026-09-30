package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

/**
 * Common contract + shared data for every booking type.
 * computeTotalPrice() is abstract so each subclass supplies its own
 * pricing rule -- this is what makes App.computeAndPrint(Booking)
 * work polymorphically for Flight/Train/CarRental alike.
 */
public abstract class Booking {

	private final String bookingId;
	private final String customerName;
	private final String travelDate;
	private final String destinationCity;

	protected Booking(String bookingId, String customerName, String travelDate, String destinationCity) {
		this.bookingId = bookingId;
		this.customerName = customerName;
		this.travelDate = travelDate;
		this.destinationCity = destinationCity;
	}

	public abstract double computeTotalPrice() throws MissingInformationException, InvalidArgumentException;

	public String getBookingId() {
		return bookingId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	@Override
	public String toString() {
		return String.format("[%s] #%s - %s - %s -> %s",
				getClass().getSimpleName(), bookingId, customerName, travelDate, destinationCity);
	}

}
