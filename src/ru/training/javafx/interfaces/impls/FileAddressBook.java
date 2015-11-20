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

            System.out.println(personListFile.getPath());

            BufferedReader br = new BufferedReader(new FileReader(personListFile));

            int lineNumber = 0;
            String s, name = "", phone = "";


            do{
                s = br.readLine();

                if (lineNumber%2 == 0){
                    name = s;
                } else {
                    phone = s;
                    personList.add(new Person(name, phone));
                }


                lineNumber++;


            } while ((name != null) || (phone != null));


            //System.out.println(lineNumber + " lines in the file");

            br.close();

        }catch(IOException e){
            //System.out.println("IOException in FileAddressBook.init");
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
        System.out.println("File updating....");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(personListFile));
            for (Person p : personList) {

                bw.write(p.getName() + "\r\n");
                bw.write(p.getPhone() + "\r\n");

                System.out.println("Person " + p.getName() + " " + p.getPhone() + " saved");
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
