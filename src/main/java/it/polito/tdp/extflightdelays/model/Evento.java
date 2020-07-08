package it.polito.tdp.extflightdelays.model;

public class Evento implements Comparable<Evento> {
	
	public enum EventType{
		ARRIVO, PARTENZA
	}

	private Integer giorno;
	private String stato;
	private EventType tipo;
	
	public Evento(int giorno, String stato, EventType tipo) {
		super();
		this.giorno = giorno;
		this.stato = stato;
		this.tipo = tipo;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compareTo(Evento o) {
		return this.giorno.compareTo(o.getGiorno());
	}
	
}
