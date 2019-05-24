import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.SorensenDice;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import org.jgrapht.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.awt.*;

public class MyJGraphXAdapter extends JApplet {

    private double maxDistance;

    private int maxPersonNumber;

    private int clusterNumber;

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<Person, MyEdge> jgxAdapter;

    public MyJGraphXAdapter(int maxPersonNumber, double maxDistance, int clusterNumber){
        this.maxPersonNumber = maxPersonNumber;
        this.maxDistance = maxDistance;
        this.clusterNumber = clusterNumber;
    }



    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    @Override
    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<Person, MyEdge> g =
                new DefaultListenableGraph<Person, MyEdge>(new DefaultUndirectedWeightedGraph<Person, MyEdge>(MyEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<Person, MyEdge>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
//        component.zoomOut();
//        component.zoomOut();
//        component.zoomOut();

        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        /*List<Person> generate = Generator.generate(maxPersonNumber);
        Iterator<Person> iterator = generate.iterator();

        int i = 0;
        HashSet<Person> personSet = new HashSet<Person>();
        List<HashSet<Person>> clustersList = new ArrayList<HashSet<Person>>();
        //SorensenDice sorensenDice = new SorensenDice();
        JaroWinkler metrics = new JaroWinkler();
        while (iterator.hasNext()){
            if (i % 1000 == 0){
                System.out.println(i);
            }
            Person person = iterator.next();
            g.addVertex(person);
            personSet.add(person);

            if (i == 0){
                HashSet<Person> cluster = new HashSet<Person>();
                cluster.add(person);
                clustersList.add(cluster);
            }

            boolean addedToCluster = false;
            for (HashSet<Person> cluster : clustersList){
                for (Person curPerson : cluster){
                    double distance = Metrica.getDistance(person.name, curPerson.name);
                    if (distance < maxDistance) {
                        MyEdge edge = g.addEdge(person, curPerson);
                        g.setEdgeWeight(edge, distance);
                        cluster.add(person);
                        addedToCluster = true;
                        break;
                    }
                }
                if (addedToCluster) {
                    break;
                }
            }
            if (!addedToCluster){
                HashSet<Person> clusterToAdd = new HashSet<Person>();
                clusterToAdd.add(person);
                clustersList.add(clusterToAdd);
            }

            /*for (Person curPerson:personSet) {
                if (curPerson.equals(person))
                    continue;
                double distance = Metrica.getDistance(person.name, curPerson.name);
//                double distance = metrics.distance(person.name, curPerson.name);
//                double distance = Metrica.levenshtein(person.name, curPerson.name);
                if (distance < maxDistance) {
                    MyEdge edge = g.addEdge(person, curPerson);
                    g.setEdgeWeight(edge, distance);
                    break;
                }
            }//
            ++i;
        }

        System.out.println("There are " + clustersList.size() + " clusters");
        for (HashSet<Person> cluster : clustersList){
            System.out.print(cluster.size() + "   ");
        }*/

        List<HashSet<Person>> clustersList = GraphBuilder.build(maxPersonNumber, maxDistance, g, clusterNumber);
        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}

