package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Nurse Home Page, Display
 * @author i-pod
 *
 */
public class NurseController implements Initializable{
	Stage stage,docStage;
	Scene scene;
	Connection con;
	private Integer id;
	@FXML
	private TableView<Staff_Model> table;
	@FXML
	private TableColumn<Staff_Model,String> Name;
	@FXML
	private TableColumn<Staff_Model,Integer>ID;//,More;
	@FXML
	private TextField Search;
	private ObservableList<Staff_Model> tableContents;	

	public NurseController(Stage stage, Scene scene, Connection con,Integer id) {
		this.id=id;
		this.stage=stage;
		this.scene=scene;
		this.con=con;
		
		try {
			display();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Name.setCellValueFactory(new PropertyValueFactory("name"));
		ID.setCellValueFactory(new PropertyValueFactory("userID"));
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	DoctorView curDoc= new DoctorView(stage,scene,con,((Staff_Model) table.getSelectionModel().getSelectedItems().get(0)).getUserID(),id);
	            	docStage= curDoc.getDisplay();
	            	docStage.setOnHidden( new EventHandler<WindowEvent>() {
	        			@Override
	        			public void handle(WindowEvent event) {
	        				System.out.println("Closed out of Doctor View");
							//updateDataTable();	
	        			}});           		

		        }
		    }
		});
	}
	
	private void display() throws IOException {
		//Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Nurse_Home.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		try {
			updateDataTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        stage.show();

		
	}
	
	

	private void updateDataTable() throws SQLException{
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM users WHERE RoleID= "+ 1); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			try { //Update secretary home table--> List of all patients
				tableContents.add(new Staff_Model(rs.getInt(1),1,rs.getString(3),rs.getString(4)));
			}
			catch(SQLException e) {
				System.err.println("An error occurred while adding values to table!");
				break;
			}
		}
		table.setItems(tableContents);
		table.refresh();


	}
	
	
	
	
	
	
	
	
	
}