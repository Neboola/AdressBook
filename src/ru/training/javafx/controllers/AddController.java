package ru.training.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by Neboola on 07.11.2015.
 */
public class AddController {


    @FXML
    private Button addButtonAdd;
    @FXML
    private Button declineButtonAdd;
    @FXML
    private TextField nameTextFieldAdd;
    @FXML
    private TextField phoneTextFieldAdd;

    public void addButtonClick(ActionEvent actionEvent) {

        addButtonAdd.setText("Добавлено");

    }
}
