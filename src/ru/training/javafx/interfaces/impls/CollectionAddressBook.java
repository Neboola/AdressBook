package ru.training.javafx.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.training.javafx.interfaces.AddressBook;
import ru.training.javafx.objects.Person;
import ru.training.javafx.utils.CollectionAddressBookStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Neboola on 08.11.2015.
 */
public class CollectionAddressBook implements AddressBook {



    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonList(){
        return personList;
    }
    public void setPersonList(ObservableList<Person> personList) {
        this.personList = personList;
    }

    @Override
    public void add(Person person) {
/*
        if (person.getName() == null) {
            person.setName("");
        }
        if (person.getPhone() == null) {
            person.setPhone("");
        }
*/
        personList.add(person);
    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    @Override
    public void edit(Person person, String newName, String newPhone) {
        person.setPhone(newPhone);
        person.setName(newName);
    }

    public ObservableList<Person> subStringSearch(String searchString){

        ObservableList<Person> tempList = FXCollections.observableArrayList();
        for(Person person : personList){
            //if(person.getName().indexOf(searchString) != -1){
            if(person.getName().toLowerCase().contains(searchString.toLowerCase())){
                tempList.add(person);
            }
        }
        return tempList;

    }


    public void init() {
        personList = CollectionAddressBookStorage.getPersonList();
    }

    public void printInConsole(){


        for(Person p : personList){
            System.out.println("Имя: " + p.getName() + " Телефон: " + p.getPhone());
        }
    }



}
