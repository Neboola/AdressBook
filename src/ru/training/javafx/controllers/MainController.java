package ru.training.javafx.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Person;


import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    @FXML
    private Button editButtonMain;
    @FXML
    private Button deleteButtonMain;
    @FXML
    private Button addButtonMain;
    @FXML
    private TextField searchTextFieldMain;
    @FXML
    private Button searchButtonMain;
    @FXML
    private TableView tableAddressBook;
    @FXML
    private Label labelCount;
    @FXML
    private TableColumn<Person, String> columnName;
    @FXML
    private TableColumn<Person, String> columnPhone;

    @FXML
    private void initialize(){
        //Указываем в параметре название поля, PropertyValueFactory автоматически считывает нужный геттер
        //поля класса Person
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                updateCountLabel();
                tableAddressBook.setItems(addressBookImpl.getPersonList());
            }
        });

        addressBookImpl.fillTestCollection(12);







    }



    private void updateCountLabel() {
        labelCount.setText("" + addressBookImpl.getPersonList().size());
    }


    public void showEdit(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/edit.fxml"));
            stage.setTitle("Изменить");
            stage.setMinWidth(300);
            stage.setMinHeight(150);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        }   catch (IOException e){
            e.printStackTrace();
        }

    }


    public void showAdd(ActionEvent actionEvent) {
        try{


            //addressBookImpl.getPersonList().add(new Person("NameAdded" + addressBookImpl.getPersonList().size(), "0000"));



            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/add.fxml"));
            stage.setTitle("Добавить");
            stage.setMinWidth(300);
            stage.setMinHeight(150);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        }   catch (IOException e){
            e.printStackTrace();
        }
    }
}
