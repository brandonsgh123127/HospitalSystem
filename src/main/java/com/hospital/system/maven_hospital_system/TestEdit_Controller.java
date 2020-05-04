package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Test Viewer for Lab-Technician
 * @author spada
 *
 */
public class TestEdit_Controller implements Initializable {
	private Connection con;
	private Stage stage,testStage;
	private String testID;
	@FXML
	private TableColumn<Test_Model,String> patient,Date;
	@FXML
	private TableView<Test_Model> table;
	@FXML
	private Text test;
	@FXML
	private TextField result;
	@FXML
	private MenuButton testStatus;
	@FXML
	private MenuItem complete,incomplete;

	private ObservableList<Test_Model> tableContents;	

	
	public TestEdit_Controller(Stage stage2, Connection con, String testID) {
		this.stage=stage2;
		this.con=con;
		this.testID=testID;

		try {
			display();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void display() throws IOException{
		testStage = new Stage();
		testStage.initModality(Modality.NONE);
		testStage.initOwner(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Test_Edit_Pop_Up.fxml"));
		Parent root;
			try {
				loader.setController(this);
				root = loader.load();
    		Scene scene = new Scene(root);
    		testStage.setScene(scene);
    		updateTable();
    		testStage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void updateTable() {
		Statement stmt;
		tableContents=FXCollections.observableArrayList();
		try {
			stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("        SELECT p1.TestTypeID,p1.VisitID,p1.Result, p2.lName,p2.fName,p2.DateOfBirth,p3.Date,p4.testType,p1.Status" + 
				"       FROM Tests AS p1 INNER JOIN patients AS p2 INNER JOIN visits AS p3 INNER JOIN testType AS p4" + 
				"         ON p1.testID=" + testID +" AND p2.patientID=p3.patientID AND p1.visitID= p3.visitID AND p4.testTypeID = p1.testTypeID"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
				tableContents.add(new Test_Model(rs.getString(4) + "," + rs.getString(5),rs.getString(7),String.valueOf(rs.getInt(1)),"-",rs.getString(3),testID));
				test.setText(rs.getString(8));
				result.setText(rs.getString(3));
				if(rs.getString(9).equals("Complete")) {
					testStatus.setText("Complete");
				}
				else if(rs.getString(9).equals("Incomplete")) {
					testStatus.setText("Incomplete");
				}
		}
		 table.setItems(tableContents);
		}
		catch(Exception e) {
			System.err.println("Failed to retrieve patient visit general info!");
			e.printStackTrace();
		}
		
	}

	public Stage getDisplay() {
		// TODO Auto-generated method stub
		return testStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		patient.setCellValueFactory(new PropertyValueFactory("patient"));
		Date.setCellValueFactory(new PropertyValueFactory("date"));
		table.setEditable(false);

	    
		result.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER) {

				PreparedStatement stmt2;
				try {
					stmt2 = con.prepareStatement("UPDATE Tests SET Result='"+result.getText()+"' WHERE TestID=" + testID);
					stmt2.execute();
				} catch (SQLException e) {
					System.err.println("There was an error in modifying Result Text Field!!!!!");
					e.printStackTrace();
				}
				updateTable();
				
			}
			}
		});
		
		
		testStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("test Status update");
				complete.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						

						PreparedStatement stmt2;
						try {
							stmt2 = con.prepareStatement("UPDATE Tests SET Status='"+"Complete" +"' WHERE TestID=" + testID);
							stmt2.execute();
						} catch (SQLException e) {
							System.err.println("There was an error in modifying Status Text Field!!!!!");
							e.printStackTrace();
						}
						updateTable();
						
					}

					
				});
				incomplete.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						PreparedStatement stmt2;
						try {
							stmt2 = con.prepareStatement("UPDATE Tests SET Status='"+"Incomplete" +"' WHERE TestID=" + testID);
							stmt2.execute();
							System.out.println("Update Status");
						} catch (SQLException e) {
							System.err.println("There was an error in modifying Status Text Field!!!!!");
							e.printStackTrace();
						}
						updateTable();
						
					}

					
				});
				
				
				
			}
			
		});

	}	
}
