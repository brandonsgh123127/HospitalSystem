package com.hospital.system.maven_hospital_system;

import javafx.event.Event;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;

public class PasswordFieldCell extends TableCell<Staff_Model, String> {
private PasswordField passwordField;    
public PasswordFieldCell() {
	if (passwordField == null)
		createPassField();
    passwordField.setEditable(true);
    this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    this.setGraphic(null);
}

@Override
protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if(!isEmpty()){
        setText(item);
        setGraphic(passwordField);
    }else{
        setGraphic(null);
    }
}
	protected void createPassField() {
		passwordField = new PasswordField();
	    passwordField.setOnKeyPressed(t -> {
	        if (t.getCode() == KeyCode.ENTER) {
	            commitEdit(passwordField.getText());
	        } else if (t.getCode() == KeyCode.ESCAPE) {
	            cancelEdit();
	        }
	    });
	}
	@Override
	public void commitEdit(String item) {    
		if (!isEditing() && !item.equals(getItem())) {
        TableView<Staff_Model> table = getTableView();
        if (table != null) {
            TableColumn<Staff_Model, String> column = getTableColumn();
            CellEditEvent<Staff_Model, String> event = new CellEditEvent<>(
                table, new TablePosition<Staff_Model,String>(table, getIndex(), column), 
                TableColumn.editCommitEvent(), item
            );
            Event.fireEvent(column, event);
        }
    }

    super.commitEdit(item);
}
}
