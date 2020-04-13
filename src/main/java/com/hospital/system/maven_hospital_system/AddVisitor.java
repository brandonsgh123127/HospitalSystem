package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddVisitor implements Initializable{
	
	private Stage dialog,origStage;
	private Scene scene;
	private HashSet<String> isValid = new HashSet<>();
	@FXML
	private TextField fName,lName,address,city,state,zip,phone,email,insuranceCarrier,insuranceID;
	@FXML
	DatePicker DOB;
	@FXML
	Button submitButton;
	@FXML
	TableView table;
	@FXML
	TableColumn dateColumn,reasonColumn,doctorColumn,moreColumn;
	
	public AddVisitor(Stage stage) {
		this.origStage = stage;
		dialog = new Stage();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
		submitButton.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {		            	
		            	
		            }
		         });
	}
	public Stage display() {
	     dialog.initModality(Modality.NONE);
         dialog.initOwner(origStage);
 		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Patient_Information.fxml"));
 		Parent root;
			try {
				root = loader.load();
     		Scene scene = new Scene(root);
     		dialog.setScene(scene);
     		dialog.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return dialog;
	}
	public Stage addNew() {
		return null;
		
	}

}
