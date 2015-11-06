package ru.training.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Created by Neboola on 07.11.2015.
 */
public class EditController {

    @FXML
    private Button editButtonEdit;
    @FXML
    private Button declineButtonEdit;
    @FXML
    private TextField nameTextFieldEdit;
    @FXML
    private TextField phoneTextFieldEdit;


    public void editButtonClick(ActionEvent actionEvent) {

        editButtonEdit.setText("Изменено");

    }


}
