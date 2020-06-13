package it.polito.tdp.crimes.model;

public class Agent {
	
	private int id;
	private Vertex position;
	private boolean free;
	
	public Agent(int id, Vertex position, boolean free) {
		this.id = id;
		this.position = position;
		this.free = free;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vertex getPosition() {
		return position;
	}

	public void setPosition(Vertex position) {
		this.position = position;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", position=" + position + ", free=" + free + "]";
	}

	
}
