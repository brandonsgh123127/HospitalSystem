package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Control Secretary, whose id is 0
 * @author spada
 *
 */
public class SecretaryController implements Initializable{

	private Stage stage,dialog;
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
	@FXML
	private ObservableList<Visitor_Model> tableContents;	
	/*
	 * Initialize for adding new patients... 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lName.setCellValueFactory(new PropertyValueFactory("lName"));
		fName.setCellValueFactory(new PropertyValueFactory("fName"));
		DOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	AddVisitor add = new AddVisitor(stage,con,((Visitor_Model) table.getSelectionModel().getSelectedItems().get(0)).getUserID());
	            	dialog = add.getDisplay();
	            	dialog.setOnHidden( new EventHandler<WindowEvent>() {
	        			@Override
	        			public void handle(WindowEvent event) {
	        				try {
	        					System.out.println("Update table secretary");
	        					updateDataTable();
	        				} catch (SQLException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}	
	        			}});         

		        }
		    }
		});
		
		/*
		 * Search For Specific Doctor
		 */
		search.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				Statement stmt;
				try {
					stmt = con.createStatement();
					if(search.getText().equals(null))
						updateDataTable();
					else {
					ResultSet rs=stmt.executeQuery("SELECT * FROM Patients WHERE (PatientID LIKE '%"+ search.getText() +
							"%' OR fName LIKE '%"+ search.getText() + "%' OR lName LIKE '%"+ search.getText() +
							"%' OR CONCAT(fName,'', lName, '') LIKE \"%"+ search.getText() + "%\") "); 
					tableContents=FXCollections.observableArrayList();
					
					while(rs.next()) {
						boolean res; //Boolean needed for adding data to table...  if resident or not
						if(rs.getInt(12) ==1)
							res=true;
						else
							res=false;
						tableContents.add(new Visitor_Model(rs.getInt(1),rs.getString(3),rs.getString(2),rs.getString(10),
								rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(9),rs.getString(8),rs.getString(11),res,
								rs.getString(13),rs.getString(14)));					}
					table.setItems(tableContents);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
  		

		
	    addUser.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	AddVisitor add = new AddVisitor(stage,con);
		            	dialog = add.getDisplay();
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
	private Scene displayPage() throws IOException {
		//Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Search_Page.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		try {
		updateDataTable();
		}
		catch(SQLException e) {
			System.out.println("Failed to retrieve data from Patient Table!");
		}
        stage.show();
		return scene;
	}
	
	private void updateDataTable() throws SQLException{
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM Patients"); 
		tableContents=FXCollections.observableArrayList();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(rs.next()) {
			boolean res; //Boolean needed for adding data to table...  if resident or not
			if(rs.getInt(12) ==1)
				res=true;
			else
				res=false;
			try { //Update secretary home table--> List of all patients
				tableContents.add(new Visitor_Model(rs.getInt(1),rs.getString(3),rs.getString(2),rs.getString(10),
						rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(9),rs.getString(8),rs.getString(11),res,
						rs.getString(13),rs.getString(14)));
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
