package ru.training.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.training.javafx.objects.Person;

/**
 * Created by Neboola on 07.11.2015.
 */
public class AddController {


    @FXML
    private Button addButtonAdd;
    @FXML
    private Button declineButtonAdd;
    @FXML
    private TextField nameTextFieldEdit;
    @FXML
    private TextField phoneTextFieldEdit;

    public void addButtonClick(ActionEvent actionEvent) {
        addButtonAdd.setText("Добавлено");
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setPerson(Person person){
        this.setPerson(person); // edited

        nameTextFieldEdit.setText(person.getName());
        phoneTextFieldEdit.setText(person.getPhone());
    }
}
