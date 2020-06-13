package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	private int N = 10;
	private int anno;
	private int mese;
	private int giorno;
	
	private Graph<Vertex, DefaultWeightedEdge> grafo;
	private List<Agent> agenti;
	private PriorityQueue<Evento> queue;
	private int policeStation;
	
	private int malgestiti;
	
	public void init(int N, int anno, int mese, int giorno, Graph<Vertex, DefaultWeightedEdge> grafo, int policeStation, List<Event> crimini) {
		this.malgestiti = 0;
		this.N = N;
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.policeStation = policeStation;
		this.grafo = grafo;
		agenti = new ArrayList<>();
		for(Vertex v : grafo.vertexSet()) {
			if(v.getId()==policeStation) {
				for(int i = 1; i <= N; i++) {
					Agent a = new Agent(i, v, true);
					agenti.add(a);
				}
			}
		}
		
		this.queue = new PriorityQueue<>();
		for (Event f : crimini) {
					Evento e = new Evento(f.getReported_date(), EventType.CRIMINE, null, f);
					queue.add(e);
		}
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Evento e) {

		switch (e.getType()) {
		case CRIMINE:
			double distanza = 100.0;
			Agent assegnato = null;
			for(Agent a : agenti) {
				double dist = LatLngTool.distance(new LatLng(a.getPosition().getLat(), a.getPosition().getLon()), new LatLng(e.getCrimine().getGeo_lat(), e.getCrimine().getGeo_lon()), LengthUnit.KILOMETER);
				if(dist<distanza && a.isFree()) {
					distanza = dist;
					assegnato = a;
				}
			}
			if(assegnato == null) {
				malgestiti++;
			}
			else {
				assegnato.setFree(false);
				assegnato.setPosition(idVertex(e.getCrimine().getDistrict_id()));
				long seconds = 0;
				if(e.getCrimine().getOffense_category_id().equals("all-other-crimes")) {
					double random = Math.random();
					if(random >= 0.5) {
						seconds =(long)(7200+distanza*60);
					}
					else {
						seconds =(long)(3600+distanza*60);
					}
					Evento ev = new Evento(e.getTime().plusSeconds(seconds), EventType.SOLUZIONE, assegnato, e.getCrimine());
					queue.add(ev);
				}
				else {
					seconds =(long)(7200+distanza*60);
					Evento ev = new Evento(e.getTime().plusSeconds(seconds), EventType.SOLUZIONE, assegnato, e.getCrimine());
					queue.add(ev);
				}
				if(distanza > 15) {
					malgestiti++;
				}
			}
			break;
		
		case SOLUZIONE:
			e.getAgent().setFree(true);
			break;
		}
	
	}
	
	public Vertex idVertex(int id) {
		for(Vertex v : this.grafo.vertexSet()) {
			if(v.getId()==id) {
				return v;
			}
		}
		return null;
	}

	public int getPoliceStation() {
		return policeStation;
	}

	public int getMalgestiti() {
		return malgestiti;
	}
	
}
