import java.io.Serializable;

public class Person implements Serializable {
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

    @Override
    public String toString(){
        return name + " " + surName;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        Person otherObj = (Person) other;
        return surName.equals(otherObj.surName) && name.equals(otherObj.name)
                && gender == otherObj.gender && age == otherObj.age
                && region.equals(otherObj.region) && taxNumber == otherObj.taxNumber;
    }

    @Override
    public int hashCode(){
        return 77 * age + 111 + taxNumber * 87;
    }
}