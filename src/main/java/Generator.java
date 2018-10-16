import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Generator {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final int maxAge = 100;
    private static final int maxTaxNumber = 99999999;

    static public List<Person> generate(int count) {

        List<Person> people = new LinkedList();
        String name;
        String surName;
        String region;
        Gender gender;
        int taxNumber;
        int age;

        List<String> maleNames = loadData("src/main/resources/MaleNames.txt");
        logger.info("MaleNames loaded");
        List<String> femaleNames = loadData("src/main/resources/FemaleNames.txt");
        logger.info("FemaleNames loaded");
        List<String> surNames = loadData("src/main/resources/Surnames.txt");
        logger.info("Surnames loaded");
        List<String> regions = loadData("src/main/resources/Regions.txt");
        logger.info("Regions loaded");

        for (int i = 0; i < count; i++) {
            gender = Gender.values()[randomGenerate(0, 2)];
            if (gender == Gender.MALE) {
                name = maleNames.get(randomGenerate(0, maleNames.size()));
            } else {
                name = femaleNames.get(randomGenerate(0, femaleNames.size()));
            }
            surName = surNames.get(randomGenerate(0, surNames.size()));
            region = regions.get(randomGenerate(0, regions.size()));
            taxNumber = randomGenerate(0, maxTaxNumber);
            age = randomGenerate(0, maxAge);
            people.add(Person.personFactory(name, surName, gender, age, region, taxNumber));
        }
        return people;
    }

    static private List<String> loadData(String path) {
        List<String> data = new LinkedList();
        try {
            FileReader fileReader = new FileReader(path);
            String line;
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
        return data;
    }

    static private int randomGenerate(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
