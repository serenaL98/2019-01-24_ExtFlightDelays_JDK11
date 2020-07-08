package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	private List<String> stati;
	
	//grafo semplice, pesato, orientato
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<Collegamento> collegamenti;
	
	private Simulatore sim;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.stati = new ArrayList<>();
		this.collegamenti = new ArrayList<>();
		
		this.sim = new Simulatore();
	}

	public List<String> elencoStati(){
		this.stati = this.dao.loadAllStates();
		return this.stati;
	}
	
	public void creaGrafo() {
		
		this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.stati);
		
		//ARCHI
		this.collegamenti = this.dao.prendiCollegamenti();
		
		for(Collegamento c: this.collegamenti) {
			if(!this.grafo.containsEdge(c.getStorigine(), c.getStdest())) {
				//aggiungo
				Graphs.addEdge(this.grafo, c.getStorigine(), c.getStdest(), c.getPeso());
			}else {
				//aggiorno peso
				this.grafo.setEdgeWeight(c.getStorigine(), c.getStdest(), this.grafo.getEdgeWeight(this.grafo.getEdge(c.getStorigine(), c.getStdest()))+c.getPeso());
			}
		}
		
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String statiAssociati(String scelto) {
		
		List<Collegamento> ordinata = new ArrayList<>();
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(scelto)) {
			ordinata.add(new Collegamento(0, scelto,0, this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
		}
		Collections.sort(ordinata);
		
		String stampa = "";
		for(Collegamento c: ordinata) {
			stampa += c.getStdest()+" --> "+c.getPeso()+"\n";
		}
		return stampa;
	}
	
	//---PUNTO 2---
	public void simula(int T, int G, String partenza) {
		
		this.sim.init(T, G, this.grafo, partenza);
		this.sim.avvia();
		
	}
	
	public String risultatoSimulazione() {
		return this.sim.getMappa();
	}
	
}
