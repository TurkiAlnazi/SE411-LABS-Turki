package edu.spu.se411.lab07_polymorphism;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;
import edu.spu.se411.lab07_polymorphism.model.Booking;
import edu.spu.se411.lab07_polymorphism.model.CarRentalBooking;
import edu.spu.se411.lab07_polymorphism.model.FlightBooking;
import edu.spu.se411.lab07_polymorphism.model.SeatClass;
import edu.spu.se411.lab07_polymorphism.model.TrainBooking;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		logger.info("Application is starting...");

		List<Booking> bookings = new ArrayList<>();

		
		FlightBooking flight = new FlightBooking("F-001", "Sara Ahmed", "2026-10-10", "Dubai", 800.0);
		flight.setLuggageWeight(20);
		bookings.add(flight);

		TrainBooking train = new TrainBooking("T-001", "Omar Khalid", "2026-10-12", "Jeddah", SeatClass.FIRST_CLASS);
		train.setDistanceKm(450);
		bookings.add(train);

		CarRentalBooking car = new CarRentalBooking("C-001", "Lama Saad", "2026-10-15", "Riyadh", 120.0);
		car.setRentalDays(5);
		bookings.add(car);

		
		FlightBooking flightMissingLuggage = new FlightBooking("F-002", "Yousef Nasser", "2026-10-20", "Cairo", 650.0);
		bookings.add(flightMissingLuggage); 

		TrainBooking trainInvalidDistance = new TrainBooking("T-002", "Huda Ali", "2026-10-22", "Dammam", SeatClass.STANDARD);
		trainInvalidDistance.setDistanceKm(5000); 
		bookings.add(trainInvalidDistance);

		
		for (Booking booking : bookings) {
			computeAndPrint(booking);
		}

		logger.info("Application ends.");
	}

	private static void computeAndPrint(Booking booking) {
		try {
			double total = booking.computeTotalPrice();
			logger.info("Computed price for {}: {}", booking.getBookingId(), total);
			System.out.printf("%s -> Total price: %.2f%n", booking, total);

		} catch (MissingInformationException e) {
			logger.warn("Missing information for booking {}: {}", booking.getBookingId(), e.getMessage());
			System.out.println("Could not price " + booking.getBookingId() + ": " + e.getMessage());

		} catch (InvalidArgumentException e) {
			logger.error("Invalid argument for booking {}: {}", booking.getBookingId(), e.getMessage());
			System.out.println("Could not price " + booking.getBookingId() + ": " + e.getMessage());
		}
	}

}
