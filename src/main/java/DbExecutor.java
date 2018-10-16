import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.List;

public class DbExecutor {
    private static final Logger logger = Logger.getLogger(DbExecutor.class);
    Connection connection;

    public DbExecutor() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/CourseWork",
                    "postgres",
                    "7896541230");

            logger.info("DB connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(List<Person> people) {
        Statement stmt;
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
        }
    }
}
