package it.polito.tdp.flightdelays.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {                                 //12:05  - 13:28
	
	private FlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	
	
	public void creaGrafo(Airline airline) {
		
		this.grafo = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.loadAllAirports());
		
		
	}
	

}
