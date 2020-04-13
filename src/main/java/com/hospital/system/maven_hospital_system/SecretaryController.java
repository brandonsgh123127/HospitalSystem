package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecretaryController extends App implements Initializable{

	private Stage stage;
	private Scene scene;
	private Connection con;
	@FXML
	private Button addUser;
	@FXML
	private TableView table;
	@FXML
	private TableColumn<Visitor_Model,String> lName,fName,DOB;
	@FXML
	private TextField search;
	@Override
	
	//USER- 3270  PASS = gru
	/*
	 * Initialize for adding new patients... 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		lName.setCellValueFactory(new PropertyValueFactory("lName"));
		fName.setCellValueFactory(new PropertyValueFactory("fName"));
		DOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		
	    addUser.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	AddVisitor add = new AddVisitor(stage,con);
		            	final Stage dialog = add.display();
		            	

		            }
		         });

	}
	/**
	 * Constructor for Seccretary control object, populate values
	 * @param stage current stage to be displayed
	 * @param scene
	 * @param con
	 */
	public SecretaryController(Stage stage, Scene scene,Connection con) {
	    this.stage = stage;
	    this.scene=scene;
		this.con=con;
		try{displayPage();System.out.println("DisplayPage");}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows for stage to change scenes due to a different controller being active.
	 * @return The new scene created for eaasy access.
	 * @throws IOException
	 */
	public Scene displayPage() throws IOException {
		//Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Search_Page.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
		return scene;
	}
	
	@FXML private void addVisitor() {
	 
	}

}
