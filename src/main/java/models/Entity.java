package models;

import lombok.Data;

@Data
public class Entity {

    private double age;

    public Entity(int age) {
        this.age = age;
    }
}
