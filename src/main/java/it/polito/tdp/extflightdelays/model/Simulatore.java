package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.model.Evento.EventType;

public class Simulatore {
	
	//INPUT
	private int T;	//turisti
	private int G;	//giorni
	private Graph<String, DefaultWeightedEdge>grafo;
	
	//OUTPUT
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;
	
	//MODELLO DEL MONDO
	private Map<String, Integer> mappa;	//stato-turisti

	//STAMPA OUTPUT
	public String getMappa() {
		
		String stampa = "";
		for(String s: this.mappa.keySet()) {
			if(mappa.get(s)!=1)
				stampa += s+" --> "+mappa.get(s)+" turisti\n";
			else
				stampa += s+" --> "+mappa.get(s)+" turista\n";

		}
		
		return stampa;
	}
	
	//SIMULAZIONE
	public void init(int T, int G, Graph<String, DefaultWeightedEdge>grafo, String partenza) {
		this.T = T;
		this.G = G;
		this.grafo = grafo;
		this.mappa = new HashMap<>();
		this.coda = new PriorityQueue<>();

		//tutti i turisti nel punto di partenza
		this.mappa.put(partenza, T);
		
		//aggiungo primo evento
		this.coda.add(new Evento(1, partenza, EventType.ARRIVO));
		this.G--;
	}
	
	public void avvia() {
		while(!this.coda.isEmpty()) {
			Evento e = this.coda.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		
		//finchè ho ancora giorni
		if(this.G>0) {
			
			switch(e.getTipo()) {
			
			case ARRIVO:
				//destinazioni disponibili
				List<String> destinazioni = new ArrayList<>(this.statiUscenti(e.getStato()));
				
				//ogni turista sceglie la destinazione fra le possibili
				for(int i= 0; i<this.T; i++) {
					
					double prob = Math.random();
					
					for(String s: destinazioni) {
						int pesoAttuale = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(e.getStato(), s));
						double rapporto = pesoAttuale/this.sommaPesiUscenti(e.getStato());
						
						if(prob<rapporto) {
							//scelgo questo stato
							this.coda.add(new Evento(e.getGiorno()+1, s, EventType.ARRIVO));
							//aggiorno la mappa e i giorni passano
							this.mappa.put(s, this.mappa.get(s)+1);
							this.G--;
						}
					}
					
				}
				this.coda.add(new Evento(e.getGiorno()+1, e.getStato(), EventType.PARTENZA));
				break;
			
			}
			
		}
		
		
	}

	//METODI
	public List<String> statiUscenti(String partenza){
		
		List<String> stati = new ArrayList<>();
		
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(partenza)) {
			stati.add(this.grafo.getEdgeTarget(e));
		}
		
		return stati;
	}
	
	public int sommaPesiUscenti(String partenza) {
		
		int somma = 0;
		
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(partenza)) {
			somma += this.grafo.getEdgeWeight(e);
		}
		
		return somma;
	}
	
}
