package edu.spu.se411.lab07_polymorphism.model;

import edu.spu.se411.lab07_polymorphism.config.GlobalConfig;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public class TrainBooking extends Booking {

	private final SeatClass seatClass;   
	private Double distanceKm;           

	public TrainBooking(String bookingId, String customerName, String travelDate,
			String destinationCity, SeatClass seatClass) {
		super(bookingId, customerName, travelDate, destinationCity);
		this.seatClass = seatClass;
	}

	public void setDistanceKm(double distanceKm) {
		this.distanceKm = distanceKm;
	}

	@Override
	public double computeTotalPrice() throws MissingInformationException, InvalidArgumentException {

		if (distanceKm == null) {
			throw new MissingInformationException(
					"Distance was not provided for booking " + getBookingId());
		}

		if (distanceKm < GlobalConfig.MIN_TRAIN_DISTANCE || distanceKm > GlobalConfig.MAX_TRAIN_DISTANCE) {
			throw new InvalidArgumentException(
					"Distance must be between " + GlobalConfig.MIN_TRAIN_DISTANCE
					+ " and " + GlobalConfig.MAX_TRAIN_DISTANCE + " km, got: " + distanceKm);
		}

		double rate = (seatClass == SeatClass.FIRST_CLASS)
				? GlobalConfig.TRAIN_FIRST_CLASS_RATE
				: GlobalConfig.TRAIN_STANDARD_RATE;

		return distanceKm * rate;
	}

}
