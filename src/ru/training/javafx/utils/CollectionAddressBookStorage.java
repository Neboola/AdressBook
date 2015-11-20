package ru.training.javafx.utils;

import ru.training.javafx.interfaces.impls.CollectionAddressBook;

/**
 * Created by Neboola on 20.11.2015.
 */
public class CollectionAddressBookStorage {

    private static CollectionAddressBook book = new CollectionAddressBook();


    public static CollectionAddressBook getBook() {
        return book;
    }
}
