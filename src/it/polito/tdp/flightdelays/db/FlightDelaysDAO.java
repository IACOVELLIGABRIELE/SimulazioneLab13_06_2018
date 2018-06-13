package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Flight;
import it.polito.tdp.flightdelays.model.LatLan;

public class FlightDelaysDAO {

	//Restituisce tutte le compagnie aeree 
	public List<Airline> loadAllAirlines() {
		String sql = "SELECT id, airline from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getString("ID"), rs.getString("airline")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	//Restituisce tutti gli aereoporti 
	public List<Airport> loadAllAirports() {
		String sql = "SELECT id, airport, city, state, country, latitude, longitude FROM airports";
		List<Airport> result = new ArrayList<Airport>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getString("id"), rs.getString("airport"), rs.getString("city"),
						rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"), rs.getDouble("longitude"));
				result.add(airport);
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	//Restituisce tutti i voli 
	public List<Flight> loadAllFlights() {
		String sql = "SELECT id, airline, flight_number, origin_airport_id, destination_airport_id, scheduled_dep_date, "
				+ "arrival_date, departure_delay, arrival_delay, air_time, distance FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("id"), rs.getString("airline"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_dep_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("air_time"), rs.getInt("distance"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	// Restituisce tutti i voli di una compagnia aerea
	public List<Flight> loadAllFlightsOfAirlines(Airline airline) {
		String sql = "SELECT * " + 
				"FROM flights " + 
				"WHERE flights.AIRLINE = ? ";
		
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, airline.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("id"), rs.getString("airline"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_dep_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("air_time"), rs.getInt("distance"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	// Data una linea e una tratta calcola la media dei ritardi
	public float AvgDelayOfFlightOfAirline(Airline airline, Airport airport1, Airport airport2) {
		String sql = "SELECT AVG(flights.ARRIVAL_DELAY) as MediaRitardi " + 
				"FROM flights " + 
				"WHERE flights.AIRLINE = ? " + 
				"AND flights.ORIGIN_AIRPORT_ID = ? " + 
				"AND flights.DESTINATION_AIRPORT_ID = ? ";
		
		float avg = 0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, airline.getId());
			st.setString(2,airport1.getId());
			st.setString(3,airport2.getId());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				avg = rs.getFloat("MediaRitardi");
			}

			conn.close();
			return avg;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	//Latitudine e longitudine di un aereoporto
	public LatLan LatLonAirport(Airport airport) {
		String sql = "SELECT airports.LATITUDE, airports.LONGITUDE " + 
				"FROM airports " + 
				"WHERE airports.ID = ? ";
		
		LatLan latLan = null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, airport.getId());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				latLan = new LatLan(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"));
			}

			conn.close();
			return latLan;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	//tutti gli aereoporti di una linea 
	public List<Airport> allAirportsOfAirline(Airline airline) {
		
		String sql = "SELECT DISTINCT flights.ORIGIN_AIRPORT_ID, flights.DESTINATION_AIRPORT_ID " + 
				"FROM flights " + 
				"WHERE flights.AIRLINE = ? ";
		
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, airline.getId());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
			}

			conn.close();
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
}







/*
Tutti  le tratte di una linea  

SELECT * 
FROM flights
WHERE flights.AIRLINE = 'UA'

Data una linea e una tratta della stessa calcola la media dei ritardi

SELECT AVG(flights.ARRIVAL_DELAY) as MediaRitardi
FROM flights
WHERE flights.AIRLINE = 'UA'
AND flights.ORIGIN_AIRPORT_ID = 'EWR'
AND flights.DESTINATION_AIRPORT_ID = 'LAX' 


Latitudine e longitudine di due aereoporti 

SELECT airports.LATITUDE, airports.LONGITUDE
FROM airports 
WHERE airports.ID = 'ABR'

*/