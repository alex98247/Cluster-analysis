package models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String surname;

    @Transient
    private Gender gender;

    public int age;

    private String region;

    @Column(name = "tax_number")
    private int taxNumber;

    public static Person personFactory(String name, String surName, Gender gender, int age, String region, int taxNumber) {
        Person person = new Person();
        person.name = name;
        person.surname = surName;
        person.age = age;
        person.region = region;
        person.taxNumber = taxNumber;
        person.gender = gender;
        return person;
    }
}