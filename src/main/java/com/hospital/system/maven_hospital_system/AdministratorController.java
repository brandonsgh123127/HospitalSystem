package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;



public class AdministratorController extends App implements Initializable{

	private Callback<TableColumn<Staff_Model, String>, TableCell<Staff_Model, String>> cellFactory;
	private Callback<TableColumn<Staff_Model, Integer>, TableCell<Staff_Model, Integer>> cellIntFactory;

	
	private String identification;
	@FXML
	private TableView<Staff_Model> table;
	@FXML
	private TableColumn<Staff_Model, String> last,first;
	@FXML
	private TableColumn<Staff_Model,Integer> id,role;
	@FXML
	private TableColumn<Staff_Model,String> addRemoveColumn;
	@FXML
	private CheckBox addCheck,removeCheck;
	@FXML
	private Button submitButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label hintLabel;
	@FXML
	private Stage stage;
	@FXML
	private Label label;
	@FXML
	private ObservableList<Staff_Model> tableContents;
	private Connection con;
	private TableViewSelectionModel selectionModel;

	//HashSet used for ID duplicates
	private HashSet<String> userIDs = new HashSet<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableContents = FXCollections.<Staff_Model>observableArrayList();
		TableViewSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		cellFactory = new Callback<TableColumn<Staff_Model, String>, TableCell<Staff_Model, String>>() {
	                public TableCell call(TableColumn p) {
	                    return new EditCell();
	                }
	    };
		cellIntFactory = new Callback<TableColumn<Staff_Model, Integer>, TableCell<Staff_Model, Integer>>() {
            public TableCell call(TableColumn p) {
                return new EditIntCell();
            }
};
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
		id.setOnEditCommit(new EventHandler<CellEditEvent<Staff_Model,Integer>>(){
			@Override
			public void handle(CellEditEvent<Staff_Model, Integer> e) {
				((Staff_Model) e.getTableView().getItems().get(
                            e.getTablePosition().getRow())
                            ).setUserID((e.getNewValue()));
				System.out.println("ID CHANGE");
				
			}
		});
		role.setOnEditCommit(new EventHandler<CellEditEvent<Staff_Model,Integer>>(){
			@Override
			public void handle(CellEditEvent<Staff_Model, Integer> e) {
				((Staff_Model) e.getTableView().getItems().get(
                            e.getTablePosition().getRow())
                            ).setUserRole((e.getNewValue()));
				System.out.println(tableContents.set(e.getTablePosition().getRow(), (Staff_Model) e.getTableView().getItems().get(
                        e.getTablePosition().getRow())
                        ).getUserRole());
				System.out.println("ROLE CHANGE");
			}
		});
		last.setOnEditCommit(new EventHandler<CellEditEvent<Staff_Model,String>>(){
			@Override
			public void handle(CellEditEvent<Staff_Model, String> e) {
				((Staff_Model) e.getTableView().getItems().get(
                            e.getTablePosition().getRow())
                            ).setLName((e.getNewValue()));
				tableContents.set(e.getTablePosition().getRow(), (Staff_Model) e.getTableView().getItems().get(
                        e.getTablePosition().getRow())
                        );
				System.out.println("LAST CHANGE");
				
			}
		});
		first.setOnEditCommit(new EventHandler<CellEditEvent<Staff_Model,String>>(){
			@Override
			public void handle(CellEditEvent<Staff_Model, String> e) {
				((Staff_Model) e.getTableView().getItems().get(
                            e.getTablePosition().getRow())
                            ).setFName((e.getNewValue()));
				tableContents.set(e.getTablePosition().getRow(), (Staff_Model) e.getTableView().getItems().get(
                        e.getTablePosition().getRow())
                        );
				System.out.println("FIRST CHANGE");
				
			}
		});
		//When enter key is pressed
		addRemoveColumn.setOnEditCommit(new EventHandler<CellEditEvent<Staff_Model,String>>(){
			@Override
			public void handle(CellEditEvent<Staff_Model, String> e) {
				try {
					((Staff_Model) e.getTableView().getItems().get(
					            e.getTablePosition().getRow())
					            ).setPass(e.getNewValue());
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tableContents.set(e.getTablePosition().getRow(), (Staff_Model) e.getTableView().getItems().get(
                        e.getTablePosition().getRow())
                        );
				System.out.println("Pass CHANGE");
				
			}
		});
		/* Password Column, shows when password is needed to be changed*/
		addRemoveColumn.setCellFactory(new Callback<TableColumn<Staff_Model,String>, TableCell<Staff_Model,String>>() {         
	        @Override
	        public TableCell<Staff_Model, String> call(TableColumn<Staff_Model, String> cell) {
	            return new PasswordFieldCell();
	        }
	    });	    
		addRemoveColumn.setCellValueFactory(new PropertyValueFactory("pass"));
		table.setItems(tableContents);
		table.refresh();
		final Stack<Integer> s = new Stack<>();//stack for action event handler to manage check marks
		s.push(1);

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
            	System.out.println("ACTION EVENT CHECKBOX");
                if (addCheck.isSelected()) { 
                    submitButton.setText("Add");
                    try {
                    if(x == 2){
                    	removeCheck.setSelected(false);
                    }}
                    catch(EmptyStackException ex) {
                    	System.out.print("Stack empty");
                    }
                    saveButton.setText("Save Entry");
                    s.add(1);
					try {
						addStaff();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                if(removeCheck.isSelected()) {
                	submitButton.setText("Remove");
                	try {
                		if(x==1) {
                			addCheck.setSelected(false);
                		}
                	}
                	catch(EmptyStackException ex) {
                		System.out.print("Checkbox Stack Empty!");
                }
                	saveButton.setText("Remove Entry");
                	s.add(2);
					try {
						removeStaff();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

                }
                //both unchecked
                if(!addCheck.isSelected()&&!removeCheck.isSelected()) {
                	if(x==0) {
                		s.push(1);
                		addCheck.setSelected(true);
                	}
                	else if(x==1) {
                		addCheck.setSelected(true);
                	}
                	else if(x==2) {
                		removeCheck.setSelected(true);
                	}
                		
                }
                	
            }
		};
        addCheck.setOnAction(checkEvent);
        removeCheck.setOnAction(checkEvent);
        //Event Handler When Add/Remove Button is Pressed
        EventHandler<ActionEvent> submitEvent = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) {
        	try {
        		if(submitButton.getText().contentEquals("Add"))
					addStaff();
				if(submitButton.getText().contentEquals("Remove"))
					removeStaff();
						
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        };
        submitButton.setOnAction(submitEvent);
        EventHandler<ActionEvent> addRemoveEvent = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) {
        	try {
        		if(saveButton.getText().contentEquals("Save Entry")) {
					addEntry();
        		}
				if(saveButton.getText().contentEquals("Remove Entry")) {
					System.out.println("Remove Entry");
					removeEntry();
				}
						
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        };
        saveButton.setOnAction(addRemoveEvent);
        

        
		
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
	private Scene displayPage() throws IOException {
		//Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Admin_Home.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
		return scene;
	}
	/**
	 * Method to populate the database on Admin Table.
	 * @throws SQLException
	 */
	@FXML private void fillDB() throws SQLException {
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("SELECT * FROM users"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			userIDs.add(Integer.toString(rs.getInt(2)));
			if(rs.getInt(1)!= Integer.valueOf(identification) )
					tableContents.add((new Staff_Model(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5))));	
		}	
		table.setItems(tableContents);
		table.refresh();
	}
	
	@FXML private void addStaff() throws SQLException{
		table.setEditable(true);
	    last.setCellFactory(TextFieldTableCell.forTableColumn());
	    first.setCellFactory(TextFieldTableCell.forTableColumn());
	    role.setCellFactory(TextFieldTableCell.<Staff_Model,Integer>forTableColumn(new IntegerStringConverter()));
	    addRemoveColumn.setCellFactory(new Callback<TableColumn<Staff_Model,String>, TableCell<Staff_Model,String>>() {         
	        @Override
	        public TableCell<Staff_Model, String> call(TableColumn<Staff_Model, String> cell) {
	            return new PasswordFieldCell();
	        }
	    });	    
	    //fillDB();
		hintLabel.setText("When Finished, Please Press Save");
		tableContents.add(new Staff_Model(randomIDGen(),0,"","",""));
		//table.setItems(tableContents);
	}
	
	@FXML private void removeStaff() throws SQLException{
		table.setEditable(false);
	    last.setCellFactory(cellFactory);
	    first.setCellFactory(cellFactory);
	    role.setCellFactory(cellIntFactory);///////
		table.setItems(tableContents);
		hintLabel.setText("Please Select the User to Remove, then Press 'Remove'");
		fillDB();
	}
	/*
	 * Generate New ID for user
	 */
		private int randomIDGen() {
			Random ran = new Random();
			int newID = ran.nextInt((int) Math.pow(2,12));
			if(userIDs.contains(newID)) {
				return randomIDGen();
			}
			else
				return newID;
		}
	
	@FXML private void addEntry() throws SQLException, NoSuchAlgorithmException{
		Staff_Model temp =table.getSelectionModel().getSelectedItems().get(0);
		try {
			PreparedStatement stmt=con.prepareStatement("INSERT INTO `users` VALUES ("+temp.getUserID()
								+ ","+temp.getUserRole()+", '"+temp.getFName()+"' , '"+temp.getLName()+"' , '"+temp.getPass()+"',0)");
			stmt.execute();
		}
		catch(SQLException e) {
			System.out.println("Updating Entry..." +temp.getUserRole() + temp.getUserID());
			PreparedStatement stmt=con.prepareStatement("UPDATE `users` SET FirstName = '" + temp.getFName() + "',LastName = '" + temp.getLName() + "',RoleID="
														+temp.getUserRole()+", PassHash= '" + temp.getPass() + "'  where UserID = "+temp.getUserID());
			stmt.execute();
			}
		
		fillDB();
	}
	@FXML private void removeEntry() throws SQLException {
		PreparedStatement stmt=con.prepareStatement("DELETE FROM users WHERE UserID="+table.getSelectionModel().getSelectedItems().get(0).getUserID());  
		//REMOVES CURRENT USER
		stmt.execute(); 
		tableContents.remove(table.getSelectionModel().getSelectedItems().get(0));
		fillDB();
//		}
	}
	
	
	
}

