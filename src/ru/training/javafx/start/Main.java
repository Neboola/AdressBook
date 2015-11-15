package ru.training.javafx.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.training.javafx.controllers.MainController;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Lang;
import ru.training.javafx.objects.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main extends Application implements Observer{

    @Override
    public void start(Stage primaryStage) throws Exception{
/*
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("Телефонная книга");
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(scene);
        primaryStage.show();

        //testData();
*/
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));

        fxmlLoader.setResources(ResourceBundle.getBundle("ru.training.javafx.bundles.Locale", new Locale("ru")));

        Parent fxmlMain = (Parent) fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle(fxmlLoader.getResources().getString("phone_book"));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(315);
        primaryStage.setScene(new Scene(fxmlMain, 300, 400));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }

    private void testData(){



        CollectionAddressBook addressBook = new CollectionAddressBook();
        addressBook.fillTestCollection(20);
        addressBook.printInConsole();



    }


    @Override
    public void update(Observable o, Object arg) {
        Lang lang = (Lang) arg;
        VBox newNode = loadFXML(lang.getLocale()); //obtain new tree of components with current locale

        currentRoot.getChildren().setAll(newNode.getChildren()); //replace old children components by new components - with current locale
    }

    // loads new tree of components and returns as VBox - root component in FXML
    private VBox loadFXML(Locale locale){

    }
}
