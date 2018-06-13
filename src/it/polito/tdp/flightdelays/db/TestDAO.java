package it.polito.tdp.flightdelays.db;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		FlightDelaysDAO dao = new FlightDelaysDAO();
		Airport airport2 = new Airport("EWR", "Newark Liberty International Airport", "Newark"," NJ", "USA", 40.6925, -74.16866);
		Airport airport1 = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "CA", "USA", 33.94254, -118.40807);
		Airline airline = new Airline("UA", "United Air Lines Inc.");
		
		//System.out.println(dao.loadAllAirlines());
		//System.out.println(dao.loadAllAirports());
		//System.out.println(dao.loadAllFlights());
		System.out.println(dao.AvgDelayOfFlightOfAirline(airline, airport2, airport1));
	}

}
