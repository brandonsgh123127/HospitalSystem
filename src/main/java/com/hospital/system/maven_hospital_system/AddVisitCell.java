package com.hospital.system.maven_hospital_system;

import java.sql.SQLException;

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
        	System.out.println("Button Pressed");
        }
        };
    button.setOnAction(add);
}

private void createButton() {
	button = new Button();
}
}