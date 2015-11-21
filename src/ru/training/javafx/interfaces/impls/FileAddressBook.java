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
    String filePath = "src/ru/training/javafx/store/files/AddressBookFile.txt";

    public void init(){

        ObservableList<Person> personList = FXCollections.observableArrayList();
        try{
            personListFile = new File(filePath);

            personListFile.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(personListFile));

            int lineNumber = 0;
            String s, name = "", phone = "";

            while ((name != null) && (phone != null)) {
                s = br.readLine();
                lineNumber++;

                if (lineNumber%2 == 0){
                    phone = s;
                    personList.add(new Person(name, phone));
                } else {
                    name = s;
                }
            }

            br.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        super.setPersonList(personList);

    }

    @Override
    public void add(Person person) {
        if (person.getName() == null) {
            person.setName("");
        }
        if (person.getPhone() == null) {
            person.setPhone("");
        }
        super.add(person);
        updateFile(super.getPersonList());
    }

    @Override
    public void delete(Person person) {
        super.delete(person);
        updateFile(super.getPersonList());
    }

    @Override
    public void edit(Person person, String newName, String newPhone) {
        super.edit(person, newName, newPhone);
        updateFile(super.getPersonList());
    }

    private void updateFile(ObservableList<Person> personList){

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(personListFile));
            for (Person p : personList) {

                bw.write(p.getName() + "\r\n");
                bw.write(p.getPhone() + "\r\n");

            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
