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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    private Stage mainStage;

    private static final String RU_CODE = "ru";
    private static final String EN_CODE = "en";

    private static final String FXML_DIALOG = "../fxml/dialog.fxml";
    private static final String FXML_CONFIRM = "../fxml/confirm.fxml";
    public static final String BUNDLES_FOLDER = "ru.training.javafx.bundles.Locale";

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

    private Parent fxmlDialog;
    private Parent fxmlConfirm;
    private VBox currentRoot;

    private FXMLLoader fxmlLoaderDialog = new FXMLLoader();
    private FXMLLoader fxmlLoaderConfirm = new FXMLLoader();
    private DialogController dialogController;
    private ConfirmController confirmController;
    private Stage dialogStage;
    private Stage confirmStage;

    private ResourceBundle resourceBundle;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
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
        System.out.println("initialize(), fillData();");
        initializeLoaders();
        fillComboLocales();
        confirmController.setAddressBookImpl(addressBookImpl);

    }

    private void fillData() {
        addressBookImpl.fillTestCollection(12);
        System.out.println("initialize(), inside fillData();");
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

                fxmlDialog = loadFXMLDialog(selectedLang.getLocale());
                fxmlConfirm = loadFXMLConfirm(selectedLang.getLocale());

                //notyfy all observers about language changing
                setChanged();
                notifyObservers(selectedLang);
            }
        });

    }

    private void initializeLoaders(){
        try{
            fxmlLoaderDialog.setLocation(getClass().getResource(FXML_DIALOG));
            fxmlLoaderDialog.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, LocaleManager.RU_LOCALE));
            fxmlDialog = (Parent) fxmlLoaderDialog.load();
            dialogController = fxmlLoaderDialog.getController();

            fxmlLoaderConfirm.setLocation(getClass().getResource(FXML_CONFIRM));
            fxmlLoaderConfirm.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, LocaleManager.RU_LOCALE));
            fxmlConfirm = (Parent) fxmlLoaderConfirm.load();
            confirmController = fxmlLoaderConfirm.getController();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private VBox loadFXMLDialog(Locale locale){
        fxmlLoaderDialog = new FXMLLoader();
        fxmlLoaderDialog.setLocation(getClass().getResource(FXML_DIALOG));
        fxmlLoaderDialog.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

        VBox node = null;

        try{
            node = (VBox) fxmlLoaderDialog.load();
            dialogController = fxmlLoaderDialog.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;

    }

    private VBox loadFXMLConfirm(Locale locale){
        fxmlLoaderConfirm = new FXMLLoader();
        fxmlLoaderConfirm.setLocation(getClass().getResource(FXML_CONFIRM));
        fxmlLoaderConfirm.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

        VBox node = null;

        try{
            node = (VBox) fxmlLoaderConfirm.load();
            confirmController = fxmlLoaderConfirm.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;

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
                updateTable();
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
                confirmController.setPerson(selectedPerson);
                showConfirm(resourceBundle.getString("delete_confirmation"));
                //addressBookImpl.delete(selectedPerson);

                updateTable();
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

    private void showConfirm(String tittle){
        if(confirmStage==null){
            confirmStage = new Stage();

            confirmStage.setMinWidth(300);
            confirmStage.setMinHeight(150);
            confirmStage.setResizable(false);
            confirmStage.setScene(new Scene(fxmlConfirm));
            confirmStage.initModality(Modality.WINDOW_MODAL);
            confirmStage.initOwner(mainStage);

        }
        confirmStage.setTitle(tittle);
        confirmStage.showAndWait();
    }

    public void mouseClicked(Event event) {
        //System.out.println("click");
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
