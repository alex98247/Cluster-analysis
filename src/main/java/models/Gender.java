package models;

public enum Gender {
    MALE,
    FEMALE;

    static public Gender getbyNumber(int index) {
        return Gender.values()[index];
    }
}