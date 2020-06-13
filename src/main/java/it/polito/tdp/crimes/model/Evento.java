package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType {
		CRIMINE,
		SOLUZIONE,
	}
	
	private LocalDateTime time;
	private EventType type;
	private Agent agent;
	private Event crimine;

	public Evento(LocalDateTime time, EventType type, Agent agent, Event crimine) {
		this.time = time;
		this.type = type;
		this.agent = agent;
		this.crimine = crimine;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Event getCrimine() {
		return crimine;
	}

	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}

	
	
	@Override
	public String toString() {
		return "Evento [time=" + time + ", type=" + type + ", agent=" + agent + ", crimine=" + crimine + "]";
	}

	@Override
	public int compareTo(Evento o) {
		return this.time.compareTo(o.time);
	}
	
}
