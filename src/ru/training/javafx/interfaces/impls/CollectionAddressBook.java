package ru.training.javafx.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.training.javafx.interfaces.AddressBook;
import ru.training.javafx.objects.Person;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Neboola on 08.11.2015.
 */
public class CollectionAddressBook implements AddressBook {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonList(){
        return personList;
    }

    @Override
    public void add(Person person) {
        personList.add(person);
    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    @Override
    public void edit(Person person) {

    }

    public void printInConsole(){


        for(Person p : personList){
            System.out.println("Имя: " + p.getName() + " Телефон: " + p.getPhone());
        }
    }

    public void fillTestCollection(int n){
        for (int i = 0; i < n; i++) {
            add(new Person("test" + i, i + "00000000"));
        }
    }


}
