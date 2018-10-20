package dbHadlers;

import models.Gender;
import models.Person;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DbHandler {
    private static final Logger logger = Logger.getLogger(DbHandler.class);
    private Connection connection;
    private String host;
    private String login;
    private String password;

    public DbHandler() {

        FileInputStream fis;
        Properties property;

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property = new Properties();
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");

        } catch (IOException ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }

    public List<Person> selectAll() {
        startConnection();

        Statement statement;
        List<Person> people = new LinkedList<Person>();
        String sql = "SELECT *  FROM PEOPLE";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                int age = resultSet.getInt("AGE");
                Gender gender = Gender.values()[resultSet.getInt("GENDER")];
                int tax_number = resultSet.getInt("TAX_NUMBER");
                String region = resultSet.getString("REGION");
                people.add(Person.personFactory(name, surname, gender, age, region, tax_number));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                closeConnection();
            }
        }
        return people;
    }

    public void insert(List<Person> people) throws SQLException {
        startConnection();

        String sql = "INSERT INTO PEOPLE (NAME, SURNAME, GENDER, AGE, REGION, TAX_NUMBER) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            for (Person person : people) {
                preparedStatement.setString(1, person.getName());
                preparedStatement.setString(2, person.getSurName());
                preparedStatement.setInt(3, person.getIntGender());
                preparedStatement.setInt(4, person.getAge());
                preparedStatement.setString(5, person.getRegion());
                preparedStatement.setInt(6, person.getTaxNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                closeConnection();
            }
        }
    }

    private void startConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, login, password);

            logger.info("DB connected");

        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }

    private void closeConnection() {
        try {

            connection.close();
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }
}
