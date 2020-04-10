package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdministratorController {

	@FXML
	private TableView<Event> table;
	@FXML
	private TableColumn<Event, String> lastName,firstName,id,role;
	@FXML
	private CheckBox addCheck,removeCheck;
	@FXML
	private Button submitButton;
	
	private Stage stage;
	private Scene scene;
	
	public AdministratorController(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene=scene;
	}
	
	public Scene displayPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		String path = System.getProperty("user.dir") + "\\fxml\\Admin_Home.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		//Load the FXML file
		Parent root = loader.load(fxmlStream);
		this.scene = new Scene(root);
		table = (TableView<Event>) scene.lookup("#table");
		//role = (TableColumn<Event,String>)scene.lookup("#role");
		return scene;

	}
	
}
