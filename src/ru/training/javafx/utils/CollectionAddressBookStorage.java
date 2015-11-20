package ru.training.javafx.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.training.javafx.interfaces.impls.CollectionAddressBook;
import ru.training.javafx.objects.Person;

/**
 * Created by Neboola on 20.11.2015.
 */
public class CollectionAddressBookStorage {



    //private static CollectionAddressBook book = new CollectionAddressBook();
    private static ObservableList<Person> personList = FXCollections.observableArrayList();


    public static ObservableList<Person> getPersonList() {
        return personList;
    }

    public static void fillTestCollection(int n){
        for (int i = 0; i < n; i++) {
            personList.add(new Person("test" + i, i + "00000000"));
        }
    }
}
