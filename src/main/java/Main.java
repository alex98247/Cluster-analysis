import models.Entity;
import services.KohonenNetworkImpl;
import tools.Graphic;

import javax.swing.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        //AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        //PersonRepository repository = context.getBean(PersonRepository.class);

        KohonenNetworkImpl kohonenNetwork = new KohonenNetworkImpl(1, 2);
        kohonenNetwork.train(new Entity(1));
        kohonenNetwork.train(new Entity(2));
        kohonenNetwork.train(new Entity(3));
        kohonenNetwork.train(new Entity(10));
        kohonenNetwork.train(new Entity(11));
        kohonenNetwork.train(new Entity(11));
        kohonenNetwork.train(new Entity(13));

        System.out.println(kohonenNetwork.handle(new Entity(3)));
        System.out.println(kohonenNetwork.handle(new Entity(5)));
        System.out.println(kohonenNetwork.handle(new Entity(12)));

        SwingUtilities.invokeLater(() -> {
            Graphic example = new Graphic("Diagram");
            double[] vec = Arrays.stream(kohonenNetwork.neurons).map(x -> x.weight[0]).mapToDouble(x -> x).toArray();
            example.setData(vec);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
