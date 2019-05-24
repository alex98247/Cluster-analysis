import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Metaphone;

import java.util.HashSet;

public class MyMain {


    public static void main(String[] args){
        String string = "sharlottte";
        String string1 = "sharlottte";
        System.out.print(Metrica.getJaccardSimilarity(string, string1) + "\n");
        HashSet<String> shingles = Metrica.getShingles(string);
        for (String shingle:shingles) {
            System.out.print(shingle + " ");
        }
        Metaphone metaphone = new Metaphone();
        System.out.println("\n" + metaphone.metaphone(string1) + "\n" + new DoubleMetaphone().doubleMetaphone(string1));
    }
}
