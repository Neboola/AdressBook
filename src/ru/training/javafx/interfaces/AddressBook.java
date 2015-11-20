package ru.training.javafx.interfaces;

import javafx.collections.ObservableList;
import ru.training.javafx.objects.Person;

import java.io.File;

/**
 * Created by Neboola on 08.11.2015.
 */
public interface AddressBook {

    void add(Person person);
    void delete(Person person);
    void edit(Person person, String newName, String newPhone);
    //void fillTestCollection(int n);
    //ObservableList<Person> getPersonList();
    //ObservableList<Person> subStringSearch(String searchString);
    //void init();

}
