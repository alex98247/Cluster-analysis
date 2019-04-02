import models.Entity;
import services.kohonenNetwork.KohonenNetworkImpl;
import services.kohonenNetwork.Neuron;
import tools.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws Exception {
        //AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        //PersonRepository repository = context.getBean(PersonRepository.class);

        KohonenNetworkImpl kohonenNetwork = new KohonenNetworkImpl(1, 2);
        Entity[] entities = new Entity[]{
                new Entity(1),
                new Entity(2),
                new Entity(3),
                new Entity(10),
                new Entity(11),
                new Entity(12),
                new Entity(13),
        };
        Point2D.Double[] dataSet = Arrays.stream(entities).map(x -> new Point2D.Double(x.getAge(), 0)).toArray(Point2D.Double[]::new);

        Arrays.stream(entities).forEach(x -> {
            try {
                kohonenNetwork.train(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        System.out.println(kohonenNetwork.handle(new Entity(3)));
        System.out.println(kohonenNetwork.handle(new Entity(5)));
        System.out.println(kohonenNetwork.handle(new Entity(12)));

        SwingUtilities.invokeLater(() -> {
            Graphic example = new Graphic("Diagram");
            Neuron[] neurons = kohonenNetwork.getConfiguration();
            Point2D.Double[] clusterCenters = Arrays.stream(neurons).map(x -> new Point2D.Double(x.weight[0], 0)).toArray(Point2D.Double[]::new);
            example.setData(dataSet, clusterCenters);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
