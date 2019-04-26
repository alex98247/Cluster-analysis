import javax.swing.*;
import info.debatty.java.stringsimilarity.*;
import org.apache.commons.codec.language.*;

import java.util.HashSet;

public class Main {

    public static void main(String[] args){
        int maxPersonNumber = 100;
        double maxDistance = 0.48;
        MyJGraphXAdapter applet = new MyJGraphXAdapter(maxPersonNumber, maxDistance);
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//        String string = "sharlottte";
//        String string1 = "sharlottte";
//        System.out.print(Metrica.getJaccardSimilarity(string, string1));
        //HashSet<String> shingles = Metrica.getShingles(string);
//        for (String shingle:shingles) {
//            System.out.print(shingle + " ");
//        }
//        Metaphone metaphone = new Metaphone();
//        System.out.println(metaphone.metaphone(string1) + "\n" + new DoubleMetaphone().doubleMetaphone(string1));

    }
}
