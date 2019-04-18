package models;

import lombok.Data;

@Data
public class Entity {

    private double age;

    public Entity(Double age) {
        this.age = age;
    }
}
