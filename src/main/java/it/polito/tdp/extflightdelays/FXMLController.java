package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<String> cmbBoxStati;

    @FXML
    private Button btnVisualizzaVelivoli;

    @FXML
    private TextField txtT;

    @FXML
    private TextField txtG;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	
    	txtResult.appendText("Crea grafo...");
    	
    	this.model.creaGrafo();
    	
    	txtResult.appendText("\n\n#VERTICI: "+this.model.numeroVertici());
    	txtResult.appendText("\n#ARCHI: "+this.model.numeroArchi());
    }

    @FXML
    void doSimula(ActionEvent event) {

    	String scelto = this.cmbBoxStati.getValue();
    	
    	if(scelto==null) {
    		txtResult.setText("Selezionare uno stato di partenza!");
    		return;
    	}
    	
    	String ti = this.txtT.getText();
    	String gi = this.txtG.getText();
    	
    	try {
    		
    		int T = Integer.parseInt(ti);
    		int G = Integer.parseInt(gi);
    		
    		this.model.simula(T, G, scelto);
        	txtResult.setText("I turisti negli stati:\n"+this.model.risultatoSimulazione());
        	
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire sia per i giorni che per i turisti un numero intero positivo.");
    		return;
    	}
    	
    }

    @FXML
    void doVisualizzaVelivoli(ActionEvent event) {

    	String scelto = this.cmbBoxStati.getValue();
    	
    	if(scelto==null) {
    		txtResult.setText("Selezionare uno stato di partenza!");
    		return;
    	}
    	txtResult.appendText("\n\nStati associati a "+scelto+":\n"+this.model.statiAssociati(scelto));
    	
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnVisualizzaVelivoli != null : "fx:id=\"btnVisualizzaVelivoli\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtT != null : "fx:id=\"txtT\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtG != null : "fx:id=\"txtG\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxStati.getItems().addAll(this.model.elencoStati());
	}
}
