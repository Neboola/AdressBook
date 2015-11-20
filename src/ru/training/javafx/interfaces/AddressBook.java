package ru.training.javafx.interfaces;

import ru.training.javafx.objects.Person;

/**
 * Created by Neboola on 08.11.2015.
 */
public interface AddressBook {

    void add(Person person);
    void delete(Person person);
    void edit(Person person, String newName, String newPhone);

}
