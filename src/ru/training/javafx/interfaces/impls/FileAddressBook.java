package ru.training.javafx.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.training.javafx.objects.Person;

import java.io.*;

/**
 * Created by Neboola on 20.11.2015.
 */
public class FileAddressBook extends CollectionAddressBook {

    File personListFile;

    public void init(){

        ObservableList<Person> personList = FXCollections.observableArrayList();
        try{
            personListFile = new File("src/ru/training/javafx/store/files/BOOK");
            System.out.println(personListFile.getPath());

            FileReader fileReader = new FileReader(personListFile);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

            int lineNumber = 0;
            String s, name = "", phone = "";


            do{
                s = lineNumberReader.readLine();

                if (lineNumber%2 == 0){
                    name = s;
                } else {
                    phone = s;
                    personList.add(new Person(name, phone));
                }


                lineNumber++;


            } while ((name != null) || (phone != null));


            System.out.println(lineNumber + " lines in the file");

            lineNumberReader.close();

        }catch(IOException e){
            System.out.println("IOException in FileAddressBook.init");
            e.printStackTrace();
        }

        super.setPersonList(personList);

    }

    @Override
    public void add(Person person) {
        super.add(person);

    }

    @Override
    public void delete(Person person) {
        super.delete(person);

    }

    @Override
    public void edit(Person person, String newName, String newPhone) {
        super.edit(person, newName, newPhone);

    }

    private void updateFile(ObservableList<Person> personList){

    }


}
