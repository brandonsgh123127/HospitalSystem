package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;


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
	private Connection con;

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
		last.setCellValueFactory(new PropertyValueFactory("lName"));
		first.setCellValueFactory(new PropertyValueFactory("fName"));
		table.setItems(tableContents);
		System.out.println("Ouch");
		table.refresh();
		id.setEditable(true);
		final Stack<Integer> s = new Stack<>();//stack for action event handler to manage check marks
		/*
		 * Handler for Check box on admin page for adding and removing staff.
		 */
        EventHandler<ActionEvent> checkEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	int x = 0;
            	try {
            		x = s.pop();
            	}
            	catch(EmptyStackException ex) {
            		System.out.println("EMPTY STACK");
            	}
            	System.out.println("ACTION EVENT");
                if (addCheck.isSelected()) { 
                    submitButton.setText("Add");
                    try {
                    if(x == 2){
                    	removeCheck.setSelected(false);
                    }}
                    catch(EmptyStackException ex) {
                    	System.out.print("Stack empty");
                    }
                    s.add(1);
                }
                if(removeCheck.isSelected()) {
                	submitButton.setText("Remove");
                	try {
                		if(x==1) {
                			addCheck.setSelected(false);
                		}
                	}
                	catch(EmptyStackException ex) {
                		System.out.print("Stack empty");
                }
                	s.add(2);
                }
                	
            }
		};
        addCheck.setOnAction(checkEvent);
        removeCheck.setOnAction(checkEvent);
        
        EventHandler<ActionEvent> submitEvent = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) {
        	try {
				addStaff();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        };
        submitButton.setOnAction(submitEvent);
		
	}
	/**
	 * Constructor for Admin control object, populate values
	 * @param stage current stage to be displayed
	 * @param scene
	 * @param con
	 * @param identification
	 */
	public AdministratorController(Stage stage, Scene scene,Connection con,String identification) {
	    this.stage = stage;
		this.con=con;
		this.identification=identification;
		table = (TableView)scene.lookup("#table");
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
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Admin_Home.fxml"));
		loader.setController(this);
		Parent root = loader.load();		//this.scene = new Scene(root);
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
		return scene;
	}
	/**
	 * Method to populate the database on Admin Table.
	 * @throws SQLException
	 */
	@FXML public void fillDB() throws SQLException {
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("SELECT * FROM users"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+ " " + rs.getInt(2)+ " " + rs.getString(3)+ " " + rs.getString(4));
			tableContents.add((new Staff_Model((Integer)rs.getInt(2),(Integer)rs.getInt(1),rs.getString(3),rs.getString(4))));	
		}	
		table.setItems(tableContents);
		table.refresh();
	}
	
	@FXML public void addStaff() throws SQLException{
		table.setEditable(true);
		//tableContents.add(new Staff_Model(1,12,"fjad","adjak"));
		table.setItems(tableContents);
		first.setCellFactory(new LocalStringCellFactory<>());
		last.setCellFactory(new LocalStringCellFactory<>());
	}
	
	
}

