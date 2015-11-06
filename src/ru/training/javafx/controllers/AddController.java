package ru.training.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by Neboola on 07.11.2015.
 */
public class AddController {


    @FXML
    private Button addButton;

    public void addButtonClick(ActionEvent actionEvent) {

        addButton.setText("Добавлено");

    }
}
