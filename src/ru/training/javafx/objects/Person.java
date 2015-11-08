package ru.training.javafx.objects;

/**
 * Created by Neboola on 08.11.2015.
 */
public class Person {
    private String name;
    private String phone;

    Person(){

    }
    Person(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }


}
