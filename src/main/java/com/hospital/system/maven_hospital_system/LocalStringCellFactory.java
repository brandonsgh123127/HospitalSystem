package com.hospital.system.maven_hospital_system;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LocalStringCellFactory<T> implements Callback<TableColumn<Staff_Model, String>, TableCell<Staff_Model, String>> {

    @Override
    public TableCell<Staff_Model, String> call(TableColumn<Staff_Model, String> col) {
        return new TableCell<Staff_Model, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if ((item == null) && empty) {
                    setText(null);
                    return;
                }
            }

        };
    }


}