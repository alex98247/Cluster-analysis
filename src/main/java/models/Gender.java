package models;

public enum Gender {
    MALE,
    FEMALE;

    static public Gender getByInt(int index) {
        return Gender.values()[index];
    }
}