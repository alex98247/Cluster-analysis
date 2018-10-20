import dbHadlers.DbHandler;
import models.Person;
import tools.Generator;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        List<Person> generate = Generator.generate(10000);
        DbHandler executor = new DbHandler();
        executor.insert(generate);

    }
}
