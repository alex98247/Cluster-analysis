package models;

public class Person {
    private String name;
    private String surname;
    private Gender gender;
    private int age;
    private String region;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public int getIntGender() {
        return gender.ordinal();
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(int taxNumber) {
        this.taxNumber = taxNumber;
    }
}