package com.hospital.system.maven_hospital_system;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class EditIntCell extends TableCell<Staff_Model,Integer>{

	private TextField textField;
	
	public EditIntCell() {
		
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		if(textField!=null) {
			createTextField();
		}
		
		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		textField.selectAll();
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setText(String.valueOf(getItem()));
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}
	
	@Override
	public void updateItem(Integer item, boolean empty) {
		super.updateItem(item, empty);
		if(empty) {
			setText(null);
			setGraphic(textField);
		}
		else{
			if (isEditing()) {
                if (textField != null) {
                    textField.setText(String.valueOf((getInteger())));
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
            	try {
                textField.setText(String.valueOf((getInteger())));
            	}
            	catch(NullPointerException e) {
            		
            	}
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
		
	}
    private void createTextField() {
        textField = new TextField((String.valueOf((getInteger()))));
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(Integer.valueOf(textField.getText()));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
    private Integer getInteger() {
    	try {
        return getItem() == null ? null : getItem();
    	}
    	catch(NullPointerException e) {
    		System.out.println("Null Pointer in Table When Removing");
    	}
    	return -1;
    	}

	
}

