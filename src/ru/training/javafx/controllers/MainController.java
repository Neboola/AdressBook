package ru.training.javafx.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Person;


import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    private Stage mainStage;

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

    private Parent fxmlDialog; //FXMLEdit
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private DialogController dialogController;
    private Stage dialogStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize(){
        //Указываем в параметре название поля, PropertyValueFactory автоматически считывает нужный геттер
        //поля класса Person
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

        initializeListeners();


        //tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); //SINGLE - default

        addressBookImpl.fillTestCollection(12);
        tableAddressBook.setItems(addressBookImpl.getPersonList());


        initializeLoader();

    }

    private void initializeListeners(){

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                updateCountLabel();
                //tableAddressBook.setItems(addressBookImpl.getPersonList());
            }
        });

        tableAddressBook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    dialogController.setPerson((Person) tableAddressBook.getSelectionModel().getSelectedItem());
                    showDialog("edit_dialog");
                }
            }
        });

    }

    private void initializeLoader(){
        try{
            fxmlLoader.setLocation(getClass().getResource("../fxml/dialog.fxml"));
            fxmlDialog = (Parent) fxmlLoader.load();
            dialogController = fxmlLoader.getController();


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCount.setText("" + addressBookImpl.getPersonList().size());
    }

    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        Person selectedPerson = (Person) tableAddressBook.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "addButtonMain" :
/*
                dialogController.setPerson(new Person());
                showDialog(parentWindow, "add_dialog");
                addressBookImpl.add(dialogController.getPerson());
*/
                Person newPerson = new Person();
                dialogController.setPerson(newPerson);
                showDialog("add_dialog");

                if(!newPerson.getName().equals("") && !newPerson.getPhone().equals("")) {
                    addressBookImpl.add(newPerson);
                }

                break;
            case "editButtonMain" :
                dialogController.setPerson(selectedPerson);
                showDialog("edit_dialog");
                break;
            case "deleteButtonMain" :
                //addressBookImpl.getPersonList().remove(selectedPerson);
                addressBookImpl.delete(selectedPerson);
                break;
        }
    }

    //=======

    private void showDialog(String tittle){
        if(dialogStage==null){
            dialogStage = new Stage();
            //Parent root = FXMLLoader.load(getClass().getResource("../fxml/dialog.fxml"));

            dialogStage.setMinWidth(300);
            dialogStage.setMinHeight(150);
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(fxmlDialog));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);

        }
        dialogStage.setTitle(tittle);
        dialogStage.showAndWait();
    }


    public void mouseClicked(Event event) {
        System.out.println("click");
    }
}
