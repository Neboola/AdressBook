package ru.training.javafx.interfaces.impls;

import ru.training.javafx.interfaces.AddressBook;
import ru.training.javafx.objects.Person;

import java.util.ArrayList;

/**
 * Created by Neboola on 08.11.2015.
 */
public class CollectionAddressBook implements AddressBook {

    private ArrayList<Person> personList = new ArrayList<Person>();

    public ArrayList<Person> getPersonList(){
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
}
