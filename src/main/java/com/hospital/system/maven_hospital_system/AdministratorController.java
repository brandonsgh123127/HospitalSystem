package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AdministratorController extends App implements Initializable{

	private String identification;
	@FXML
	private TableView<Staff_Model> table;
	@FXML
	private TableColumn<Staff_Model, String> last,first;
	@FXML
	private TableColumn<Staff_Model,Integer> id,role;
	@FXML
	private CheckBox addCheck,removeCheck;
	@FXML
	private Button submitButton;
	@FXML
	private Stage stage;
	@FXML
	private Label label;
	@FXML
	private ObservableList<Staff_Model> tableContents;
	private List<Staff_Model> staff;
	private Connection con;
	//private Scene scene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableContents = FXCollections.<Staff_Model>observableArrayList();
        try{
        	fillDB();
        }
        catch(SQLException e) {
        	System.err.println("ERROR LOADING SQL DB for ADMIN!");
        }
		id.setCellValueFactory(new PropertyValueFactory("userID"));
		role.setCellValueFactory(new PropertyValueFactory("userRole"));
		last.setCellValueFactory(new PropertyValueFactory("fName"));
		first.setCellValueFactory(new PropertyValueFactory("lName"));
		table.setItems(tableContents);
		System.out.println("Success");
		table.refresh();
		
	}
	
	public AdministratorController(Stage stage, Scene scene,Connection con,String identification) {
	     assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Admin_Home.fxml'.";
	     assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'Admin_Home.fxml'.";
	     this.stage = stage;
		this.con=con;
		this.identification=identification;
		try{displayPage();}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scene displayPage() throws IOException {
		//Load the FXML file
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Admin_Home.fxml"));		//this.scene = new Scene(root);
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
		return scene;
	}
	
	@FXML public void fillDB() throws SQLException {
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("SELECT * FROM users"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+ " " + rs.getInt(2)+ " " + rs.getString(3)+ " " + rs.getString(4));
			tableContents.add((new Staff_Model((Integer)rs.getInt(1),(Integer)rs.getInt(2),rs.getString(3),rs.getString(4))));	
		}	
		table.setItems(tableContents);
		table.refresh();
	}
	
	
}
