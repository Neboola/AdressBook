package ru.training.javafx.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Person;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("Телефонная книга");
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(scene);
        primaryStage.show();

        //testData();


    }


    public static void main(String[] args) {
        launch(args);

    }

    private void testData(){

        CollectionAddressBook addressBook = new CollectionAddressBook();
        addressBook.fillTestCollection(20);
        addressBook.printInConsole();



    }




}
