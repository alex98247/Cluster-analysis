import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

public class DbExecutor {
    private static final Logger logger = Logger.getLogger(DbExecutor.class);
    private Connection connection;
    private String host;
    private String login;
    private String password;

    public DbExecutor() {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");

        } catch (IOException ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }

    public void insert(List<Person> people) {
        Statement stmt;
        
        startConnection();

        StringBuilder sql = new StringBuilder("INSERT INTO PEOPLE (NAME, SURNAME, GENDER, AGE, REGION, TAX_NUMBER) VALUES ");
        for (Person person : people) {
            Formatter formatter = new Formatter();
            formatter.format("('%s', '%s', %s, %s, '%s', %s), ", person.name, person.surName, person.gender.ordinal(), person.age, person.region, person.taxNumber);
            sql.append(formatter.toString());
        }
        sql.replace(sql.length() - 2, sql.length(), ";");
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql.toString());
        } catch (SQLException ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
        closeConnection();
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
