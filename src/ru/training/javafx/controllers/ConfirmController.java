package ru.training.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.training.javafx.interfaces.AddressBook;
//import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Person;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Neboola on 17.11.2015.
 */
public class ConfirmController implements Initializable {

    private Person person;

    private AddressBook addressBookImpl;

    public void setAddressBookImpl(AddressBook addressBookImpl) {
        this.addressBookImpl = addressBookImpl;
    }

    private ResourceBundle resourceBundle;

    @FXML
    private Label nameTextField;

    public void setPerson(Person person){
        this.person = person;
        nameTextField.setText(resourceBundle.getString("delete") + " " + person.getName() + "?");

    }

    public void actionDelete(ActionEvent actionEvent){

        addressBookImpl.delete(person);
        actionClose(actionEvent);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    @Override
    public void initialize(URL url, ResourceBundle resource) {
        resourceBundle = resource;
    }
}
