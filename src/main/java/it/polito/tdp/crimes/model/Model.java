package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private SimpleWeightedGraph<Vertex, DefaultWeightedEdge> grafo;
	private List<Vertex> nodi;
	private List<Integer> anni;
	private EventsDao dao;
	private Simulator sim;
	
	public Model() {
		this.dao = new EventsDao();
		this.sim = new Simulator();
		anni = new ArrayList<>(dao.listAllYears());
	}

	public void creaGrafo(int year) {
		nodi = new ArrayList<>(dao.listAllDistricts(year));
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(Vertex v : nodi) grafo.addVertex(v);
		for(int i = 0; i < nodi.size(); i++) {
			for(int j = i + 1; j < nodi.size(); j++) {
				double weight = LatLngTool.distance(new LatLng(nodi.get(i).getLat(), nodi.get(i).getLon()), new LatLng(nodi.get(j).getLat(), nodi.get(j).getLon()), LengthUnit.KILOMETER);
				Graphs.addEdge(grafo, nodi.get(i), nodi.get(j), weight);
			}
		}
	}

	public List<Integer> getAnni() {
		return anni;
	}

	public SimpleWeightedGraph<Vertex, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Vertex> getNodi() {
		return nodi;
	}
	
	public List<Integer> mesi(){
		List<Integer> mesi = new ArrayList<>();
		for(int i=1; i<=12; i++) {
			mesi.add(i);
		}
		return mesi;
	}
	
	public List<Integer> giorni(){
		List<Integer> giorni = new ArrayList<>();
		for(int i=1; i<=31; i++) {
			giorni.add(i);
		}
		return giorni;
	}

	public Simulator getSim() {
		return sim;
	}

	public EventsDao getDao() {
		return dao;
	}
	
	
}
