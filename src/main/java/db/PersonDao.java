package db;

import models.Person;

import java.util.List;

public interface PersonDao {

    List<Person> selectAll();
    void insert(List<Person> people);
}
