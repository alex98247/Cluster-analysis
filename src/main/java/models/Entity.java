package models;

import annotations.Statistic;
import lombok.Data;

@Data
public class Entity {

    @Statistic
    private double age;

    public Entity(Double age) {
        this.age = age;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
