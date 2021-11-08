/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
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

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimili"
	private Button btnSimili; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimulazione"
	private Button btnSimulazione; // Value injected by FXMLLoader

	@FXML // fx:id="boxGenere"
	private ComboBox<String> boxGenere; // Value injected by FXMLLoader

	@FXML // fx:id="boxAttore"
	private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

	@FXML // fx:id="txtGiorni"
	private TextField txtGiorni; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAttoriSimili(ActionEvent event) {
		this.txtResult.clear();
		Actor attore = this.boxAttore.getValue();
		if (attore == null) {
			this.txtResult.appendText("Scegliere un attore");
			return;
		}

		List<Actor> attori = this.model.getConnessi(attore);

		this.txtResult.appendText(String.format("Attori connesi a %s:\n\n", attore.toString()));

		for (Actor a : attori) {
			this.txtResult.appendText(a.toString() + "\n");
		}

	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
		this.txtResult.clear();
		String genere = this.boxGenere.getValue();

		if (genere == null) {
			this.txtResult.appendText("Scegliere un genere");
			return;
		}

		this.model.creaGrafo(genere);

		List<Actor> attori = this.model.getAttori();

		this.boxAttore.getItems().addAll(attori);

	}

	@FXML
	void doSimulazione(ActionEvent event) {

		this.txtResult.clear();

		if (!this.model.isReady()) {
			this.txtResult.appendText("Genera il grafo");
			return;
		}

		Integer giorni;
		try {
			giorni = Integer.parseInt(this.txtGiorni.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero valido");
			return;
		}

		if (giorni < 0) {
			this.txtResult.appendText("Inserire un numero maggiore di 0");
			return;
		}

		this.model.simula(giorni);

		this.txtResult.appendText("Numero di giorni di pausa: " + this.model.getGiorniPausa() + "\n\n");
		this.txtResult.appendText("Attori intervistati: \n");
		for (Actor a : this.model.getIntervistati()) {
			this.txtResult.appendText(a.toString() + "\n");
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
		assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.boxGenere.getItems().addAll(this.model.getGeneri());
	}
}
