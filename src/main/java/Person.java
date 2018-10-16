public class Person {
    public String name;
    public String surName;
    public Gender gender;
    public int age;
    public String region;
    public int taxNumber;

    public static Person personFactory(String name, String surName, Gender gender, int age, String region, int taxNumber) {
        Person person = new Person();
        person.name = name;
        person.surName = surName;
        person.age = age;
        person.region = region;
        person.taxNumber = taxNumber;
        person.gender = gender;
        return person;
    }
}