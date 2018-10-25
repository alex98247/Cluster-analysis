package db;

import models.Gender;
import models.Person;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PersonDaoImpl implements PersonDao {
    private static final Logger logger = Logger.getLogger(PersonDaoImpl.class);

    private static String host;
    private static String login;
    private static String password;

    public PersonDaoImpl() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            Properties property = new Properties();
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public List<Person> selectAll() {
        List<Person> people = new LinkedList<>();
        String sql = "SELECT *  FROM PEOPLE";

        try (Connection connection = DriverManager.getConnection(host, login, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                int age = resultSet.getInt("AGE");
                Gender gender = Gender.getbyNumber(resultSet.getInt("GENDER"));
                int tax_number = resultSet.getInt("TAX_NUMBER");
                String region = resultSet.getString("REGION");
                people.add(Person.personFactory(name, surname, gender, age, region, tax_number));
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return people;
    }

    public void insert(List<Person> people) {
        String sql = "INSERT INTO PEOPLE (NAME, SURNAME, GENDER, AGE, REGION, TAX_NUMBER) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(host, login, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (Person person : people) {
                preparedStatement.setString(1, person.getName());
                preparedStatement.setString(2, person.getSurName());
                preparedStatement.setInt(3, person.getIntGender());
                preparedStatement.setInt(4, person.getAge());
                preparedStatement.setString(5, person.getRegion());
                preparedStatement.setInt(6, person.getTaxNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}
