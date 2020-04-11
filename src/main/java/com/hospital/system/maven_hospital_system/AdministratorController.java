package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.*;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdministratorController extends Thread{

	private String identification;
	@FXML
	private TableView<Event> table;
	@FXML
	private TableColumn<Event, String> lastName,firstName,id,role;
	@FXML
	private CheckBox addCheck,removeCheck;
	@FXML
	private Button submitButton;
	@FXML
	private Stage stage;
	@FXML
	private Label label;
	@FXML
	private ObservableList<TableColumn<Event, ?>> tableContents;
	
	private Connection con;
	//private Scene scene;
	
	public AdministratorController(Stage stage, Scene scene,Connection con,String identification) {
		this.stage = stage;
		this.con=con;
		try{fillDB();}
		catch(Exception e) {
			System.out.println("FAILED TO FILL DATABASE WITH INFO!!");
		}
		//this.scene=scene;
	}
	
	public Scene displayPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		String path = System.getProperty("user.dir") + "\\fxml\\Admin_Home.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		//Load the FXML file
		Parent root = loader.load(fxmlStream);
		//this.scene = new Scene(root);
		//role = (TableColumn<Event,String>)scene.lookup("#role");
        Scene scene = new Scene(root);
		table = (TableView<Event>) scene.lookup("#table");
		//lastName = (TableColumn<Event,?> scene.lookup("#lastName"));
//		tableContents = table.getColumns();
//		lastName =(TableColumn<Event,String>) tableContents.get(3);
//		firstName =(TableColumn<Event,String>) tableContents.get(2);
//		id =(TableColumn<Event,String>) tableContents.get(0);
//		role =(TableColumn<Event,String>) tableContents.get(1);		
		stage.setScene(scene);
        stage.show();
		return scene;
	}
	
	public void fillDB() throws SQLException {
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("SELECT * FROM users"); 
		while(rs.next()) {
			table.getItems().addAll();

		}


		
	}
	
}
