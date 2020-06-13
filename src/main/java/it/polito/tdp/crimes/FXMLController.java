/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.model.Model;
import it.polito.tdp.crimes.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	txtResult.clear();
    	Integer anno = boxAnno.getValue();
    	if(anno==null) {
    		txtResult.appendText("seleziona un anno");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	for(final Vertex v : this.model.getGrafo().vertexSet()) {
    		List<Vertex> lv = new ArrayList<>(Graphs.neighborListOf(this.model.getGrafo(), v));
    		Collections.sort(lv, new Comparator<Vertex>() {
    			public int compare(Vertex c1, Vertex c2) {
    				if(LatLngTool.distance(new LatLng(v.getLat(), v.getLon()), new LatLng(c1.getLat(), c1.getLon()), LengthUnit.KILOMETER) > LatLngTool.distance(new LatLng(v.getLat(), v.getLon()), new LatLng(c2.getLat(), c2.getLon()), LengthUnit.KILOMETER)) {
    					return 1;
    				}
    				else return -1;
    			}
    		});
    		txtResult.appendText(v.getId()+": ");
    		for(Vertex w : lv) {
    			txtResult.appendText(w.getId()+", ");
    		}
    		txtResult.appendText("\n"+"\n");
    	}
    	btnSimula.setDisable(false);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Integer anno = boxAnno.getValue();
    	if(anno==null) {
    		txtResult.appendText("seleziona un anno");
    		return;
    	}
    	Integer mese = boxMese.getValue();
    	if(mese==null) {
    		txtResult.appendText("seleziona un mese");
    		return;
    	}
    	Integer giorno = boxGiorno.getValue();
    	if(giorno==null) {
    		txtResult.appendText("seleziona un giorno");
    		return;
    	}
    	Integer agenti = -1;
    	try {
    		agenti = Integer.parseInt(txtN.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("inserisci un numero tra 1 e 10");
    	}
    	if(agenti<1 || agenti>10) {
    		txtResult.appendText("inserisci un numero tra 1 e 10");
    		return;
    	}
    	this.model.getSim().init(agenti, anno, mese, giorno, this.model.getGrafo(), this.model.getDao().policeStation(anno), this.model.getDao().listAllEvents(anno, mese, giorno));
    	this.model.getSim().run();
    	txtResult.appendText("malgestiti: "+this.model.getSim().getMalgestiti());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(this.model.getAnni());
    	btnSimula.setDisable(true);
    	boxMese.getItems().addAll(this.model.mesi());
    	boxGiorno.getItems().addAll(this.model.giorni());
    }
}
