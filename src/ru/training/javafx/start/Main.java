package ru.training.javafx.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.training.javafx.controllers.MainController;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Lang;
import ru.training.javafx.objects.Person;
import ru.training.javafx.utils.LocaleManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main extends Application implements Observer{

    private static final String FXML_MAIN = "../fxml/main.fxml";
    public static final String BUNDLES_FOLDER = "ru.training.javafx.bundles.Locale";

    private Stage primaryStage;
    private Parent fxmlMain;
    private MainController mainController;
    private FXMLLoader fxmlLoader;
    private GridPane currentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        createGUI(LocaleManager.RU_LOCALE);
    }

    private void createGUI(Locale locale) {

        currentRoot = loadFXML(locale);
        Scene scene = new Scene(currentRoot, 300, 400);

        mainController.setMainStage(primaryStage);

        primaryStage.setScene(scene);

        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(315);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);


    }

    private GridPane loadFXML(Locale locale){
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_MAIN));
        fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

        GridPane node = null;

        try{
            node = (GridPane) fxmlLoader.load();
            mainController = fxmlLoader.getController();
            mainController.addObserver(this);
            primaryStage.setTitle(fxmlLoader.getResources().getString("phone_book"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;

    }

    private void testData(){

        CollectionAddressBook addressBook = new CollectionAddressBook();
        addressBook.fillTestCollection(20);
        addressBook.printInConsole();

    }

    @Override
    public void update(Observable o, Object arg) {
        Lang lang = (Lang) arg;
        GridPane newNode = loadFXML(lang.getLocale()); //obtain new tree of components with current locale

        currentRoot.getChildren().setAll(newNode.getChildren()); //replace old children components by new components - with current locale
    }

}
