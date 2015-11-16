package ru.training.javafx.controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import ru.training.javafx.objects.Lang;
import ru.training.javafx.objects.Person;
import ru.training.javafx.utils.LocaleManager;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainController extends Observable implements Initializable{



    private static final String FXML_MAIN = "../fxml/dialog.fxml";
    public static final String BUNDLES_FOLDER = "ru.training.javafx.bundles.Locale";

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    private Stage mainStage;

    private static final String RU_CODE = "ru";
    private static final String EN_CODE = "en";

    private Locale localeDialog;

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
    private Button clearTextButtonMain;
    @FXML
    private TableView tableAddressBook;
    @FXML
    private Label labelCount;
    @FXML
    private TableColumn<Person, String> columnName;
    @FXML
    private TableColumn<Person, String> columnPhone;
    @FXML
    private ComboBox comboLocales;

    private Parent fxmlDialog; //FXMLEdit
    private FXMLLoader fxmlLoader /*= new FXMLLoader()*/;
    private DialogController dialogController;
    private Stage dialogStage;

    private ResourceBundle resourceBundle;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setLocaleDialog(Locale localeDialog) {
        this.localeDialog = localeDialog;
    }

    //@FXML
    @Override
    public void initialize(URL url, ResourceBundle resource) {

        resourceBundle = resource;

        //Указываем в параметре название поля, PropertyValueFactory автоматически считывает нужный геттер //поля класса Person
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
        //tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); //SINGLE - default
        initializeListeners();
        fillData();
        initializeLoader(localeDialog);
        fillComboLocales();

    }

    private void fillData() {
        addressBookImpl.fillTestCollection(12);
        tableAddressBook.setItems(addressBookImpl.getPersonList());

    }

    private void fillComboLocales(){
        Lang langRU = new Lang(0, RU_CODE, resourceBundle.getString("ru"), LocaleManager.RU_LOCALE);
        Lang langEN = new Lang(1, EN_CODE, resourceBundle.getString("en"), LocaleManager.EN_LOCALE);

        comboLocales.getItems().add(langRU);
        comboLocales.getItems().add(langEN);

        if(LocaleManager.getCurrentLang() == null){
            comboLocales.getSelectionModel().select(0);
        } else {
            comboLocales.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
        }

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
                    showDialog(resourceBundle.getString("edit_dialog"));
                }
            }
        });

        comboLocales.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Lang selectedLang = (Lang) comboLocales.getSelectionModel().getSelectedItem();
                LocaleManager.setCurrentLang(selectedLang);

                //notyfy all observers about language changing
                setChanged();
                notifyObservers(selectedLang);
            }
        });

    }

    private void initializeLoader(Locale locale){
        try{

            fxmlLoader.setLocation(getClass().getResource(FXML_MAIN));

            fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

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

        //Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

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
                showDialog(resourceBundle.getString("add_dialog"));

                if(!newPerson.getName().equals("") && !newPerson.getPhone().equals("")) {
                    addressBookImpl.add(newPerson);
                }

                break;
            case "editButtonMain" :
                if(!personIsSelected(selectedPerson)) {
                    return;
                }
                dialogController.setPerson(selectedPerson);
                showDialog(resourceBundle.getString("edit_dialog"));
                break;
            case "deleteButtonMain" :
                if(!personIsSelected(selectedPerson)) {
                    return;
                }
                addressBookImpl.delete(selectedPerson);
                break;
        }
    }

    private boolean personIsSelected(Person selectedPerson){
        if(selectedPerson == null) {
            return false;
        } else {
            return true;
        }

    }

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


    public void searchButtonClick(ActionEvent actionEvent) {
        if(!searchTextFieldMain.getText().equals("")){
            ObservableList<Person> tempList = addressBookImpl.subStringSearch(searchTextFieldMain.getText());
            tableAddressBook.setItems(tempList);
            labelCount.setText(String.valueOf(tempList.size()));
        } else {
            tableAddressBook.setItems(addressBookImpl.getPersonList());
            updateCountLabel();
        }

    }

    public void clearTextButtonClick(ActionEvent actionEvent) {
        updateTable();
    }

    private void updateTable(){
        searchTextFieldMain.clear();
        tableAddressBook.setItems(addressBookImpl.getPersonList());
        updateCountLabel();
    }
}
