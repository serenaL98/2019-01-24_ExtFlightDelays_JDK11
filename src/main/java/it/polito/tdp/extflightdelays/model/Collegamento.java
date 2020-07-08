package it.polito.tdp.extflightdelays.model;

public class Collegamento implements Comparable<Collegamento> {
	
	private int origine;
	private String storigine;
	private int dest;
	private String stdest;
	private Integer peso;
	
	
	public Collegamento(int origine, String storigine, int dest, String stdest, int peso) {
		super();
		this.origine = origine;
		this.storigine = storigine;
		this.dest = dest;
		this.stdest = stdest;
		this.peso = peso;
	}
	
	
	public int getOrigine() {
		return origine;
	}
	public void setOrigine(int origine) {
		this.origine = origine;
	}
	public String getStorigine() {
		return storigine;
	}
	public void setStorigine(String storigine) {
		this.storigine = storigine;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public String getStdest() {
		return stdest;
	}
	public void setStdest(String stdest) {
		this.stdest = stdest;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Collegamento o) {
		return -(this.peso.compareTo(o.getPeso()));
	}
	
	

}
