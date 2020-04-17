package com.hospital.system.maven_hospital_system;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
/**
 **Creates buttons for use within the Patient_Information View.
 ** 
 ** Adding Visits for Secretary.
 ** @author spada
 */
public class AddVisitCell extends TableCell<Visit_Model, Button> {
private Button button;    
public AddVisitCell() {
	if (button == null)
		createButton();
    button.setText("Add Visit");
    this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    this.setGraphic(button);
    EventHandler<ActionEvent> add = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) {
        	ObservableList<Visit_Model> tableContents;	
        	PatientVisit_Controller visit;
            TableRow row = getTableRow();
            TableColumn col = getTableColumn();            
            tableContents = getTableView().getItems();
            int lastIndex = 0;
            //System.out.println("Button Pressed + "+ ((Visit_Model)row.getItem()).getPatientID());
            if(row.getItem()==null) {
                for(int i = 0; i < tableContents.size()-1;i++) {
                	if(((Visit_Model)tableContents.get(i+1))==null) {
                		break;
                	}
                	else {
                		lastIndex++;
                	}
                }
            	//PatientVisit_Controller visit= new PatientVisit_Controller(((Visit_Model)view).getPatientID(),((Visit_Model) view).getVisitID(),((Visit_Model) view).getConnection());
            }
            	visit = new PatientVisit_Controller(tableContents.get(lastIndex).getPatientID(),tableContents.get(lastIndex).getFollowUpID(),tableContents.get(lastIndex).getConnection());            }
        };
    button.setOnAction(add);
}

private void createButton() {
	button = new Button();
}
}