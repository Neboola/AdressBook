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
        import ru.training.javafx.objects.Person;


        import java.io.IOException;

/**
 * Created by Neboola on 07.11.2015.
 */
public class DialogController {

    @FXML
    private Button editButton;
    @FXML
    private Button declineButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;

    private Person person;


    //public void editButtonClick(ActionEvent actionEvent) {
    //    editButton.setText("Готово!");
    //}



    public void setPerson(Person person){
        this.person = person;
        nameTextField.setText(person.getName());
        phoneTextField.setText(person.getPhone());
    }

    public void actionSave(ActionEvent actionEvent){
        person.setPhone(phoneTextField.getText());
        person.setName(nameTextField.getText());
        actionClose(actionEvent);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


}

