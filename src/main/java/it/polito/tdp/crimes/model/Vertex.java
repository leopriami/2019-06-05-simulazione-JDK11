package it.polito.tdp.crimes.model;

public class Vertex {
	
	private int id;
	private double lon;
	private double lat;
	
	public Vertex(int id, double lon, double lat) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", lon=" + lon + ", lat=" + lat + "]";
	}
	
	

}
