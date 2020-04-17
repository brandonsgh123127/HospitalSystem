package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class that will allow nurse to view Doctor's Patients
 * @author spada
 *
 */
public class DoctorView implements Initializable {
	@FXML
	private TableColumn<Visitor_Model,String> patientName,DOB,patientGender,roomNum;
	@FXML
	private TableColumn<Visitor_Model,String> unPatientName,unDOB,unGender,unRoomNum;

	@FXML
	private TableColumn<Visitor_Model,Button> add;
	
	@FXML
	private TableView<Visitor_Model> table;
	@FXML
	private TableView<Visitor_Model> unTable;
	@FXML
	private TextField searchBar;

	private Stage docStage,stage;
	private Scene scene;
	private Connection con;
	private Integer docID,nurseID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	public DoctorView(Stage stage, Scene scene, Connection con, Integer docID, Integer nurseID) {
		this.stage=stage;
		this.scene=scene;
		this.con=con;
		this.docID=docID;
		this.nurseID=nurseID;
		this.docStage = new Stage();
		display();
	}
	public Stage getDisplay() {
		return docStage;
	}
	private void display() {
	     docStage.initModality(Modality.APPLICATION_MODAL);
	     //docStage.initOwner(Stage);
	     FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Nurse_View_Doc.fxml"));
	 		Parent root;
				try {
					loader.setController(this);
					root = loader.load();
	     		Scene scene = new Scene(root);
	     		docStage.setScene(scene);
	     		docStage.show();
	     		updateTable();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	private void updateTable() throws SQLException{
		
	}

}
