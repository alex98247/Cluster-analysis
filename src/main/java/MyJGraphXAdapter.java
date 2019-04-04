import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.demo.JGraphXAdapterDemo;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import org.jgrapht.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

//HELLO
//public class Main {

    /*private static Multigraph<Person, DefaultWeightedEdge> buildGraph1(){
        List<Person> generate = Generator.generate(100000);
        Iterator<Person> iterator = generate.iterator();

        Multigraph<Person, DefaultWeightedEdge> graph = new Multigraph<Person, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        int i = 0;
        HashSet<Person> personSet = new HashSet<Person>();

        while (iterator.hasNext()){
            Person person = iterator.next();
            graph.addVertex(person);
            personSet.add(person);
            for (Person curPerson:personSet) {
                if (curPerson.equals(person))
                    continue;
                DefaultWeightedEdge edge = graph.addEdge(person, curPerson);
                graph.setEdgeWeight(edge, Metrica.levenshtein(person.name, curPerson.name));
            }
            System.out.println(person.name + " " + person.surName);
            ++i;
            if (i == 10){
                break;
            }
        }
        return graph;
    }*/

    /*private static ListenableGraph<Person, DefaultWeightedEdge> buildGraph(){
        List<Person> generate = Generator.generate(100000);
        Iterator<Person> iterator = generate.iterator();

        ListenableGraph<Person, DefaultWeightedEdge> graph = new DefaultListenableGraph<Person, DefaultWeightedEdge>(new DefaultUndirectedWeightedGraph<Person, DefaultWeightedEdge>(DefaultWeightedEdge.class));
        int i = 0;
        HashSet<Person> personSet = new HashSet<Person>();

        while (iterator.hasNext()){
            Person person = iterator.next();
            graph.addVertex(person);
            personSet.add(person);
            for (Person curPerson:personSet) {
                if (curPerson.equals(person))
                    continue;
                DefaultWeightedEdge edge = graph.addEdge(person, curPerson);
                graph.setEdgeWeight(edge, Metrica.levenshtein(person.name, curPerson.name));
            }
            System.out.println(person.name + " " + person.surName);
            ++i;
            if (i == 10){
                break;
            }
        }
        return graph;
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("DemoGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ListenableGraph<Person, DefaultWeightedEdge> g = buildGraph();
        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<String, DefaultWeightedEdge>(g);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.add(new mxGraphComponent(graphAdapter));

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });

//        DbExecutor executor = new DbExecutor();
//        executor.insert(generate);

    }*/

public class MyJGraphXAdapter    extends
        JApplet {

    private static final int MAX_DISTANCE = 4;

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<Person, MyEdge> jgxAdapter;

    /*public static void main(String[] args) {
        MyJGraphXAdapter applet = new MyJGraphXAdapter();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/


    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    @Override
    public void init()
    {
        //System.out.println("HELLO");
        // create a JGraphT graph
//        ListenableGraph<String, DefaultEdge> g =
//                new DefaultListenableGraph<String, DefaultEdge>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class));

        ListenableGraph<Person, MyEdge> g =
                new DefaultListenableGraph<Person, MyEdge>(new DefaultUndirectedWeightedGraph<Person, MyEdge>(MyEdge.class));

        // create a visualization using JGraph, via an adapter
        /*Set<Person> hashSet = g.vertexSet();
        Iterator iterator = hashSet.iterator();
        for (Person person:hashSet) {
            System.out.println(person.name + " " + person.surName);
        }*/

        jgxAdapter = new JGraphXAdapter<Person, MyEdge>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        component.zoomOut();
        component.zoomOut();
        component.zoomOut();

        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        List<Person> generate = Generator.generate(100000);
        Iterator<Person> iterator = generate.iterator();

        int i = 0;
        HashSet<Person> personSet = new HashSet<Person>();

        while (iterator.hasNext()){
            Person person = iterator.next();
            g.addVertex(person);
            personSet.add(person);
            for (Person curPerson:personSet) {
                if (curPerson.equals(person))
                    continue;

                int distance = Metrica.levenshtein(person.name, curPerson.name);
                if (distance < MAX_DISTANCE) {
                    MyEdge edge = g.addEdge(person, curPerson);
                    g.setEdgeWeight(edge, distance);
                }
            }
            System.out.println(person.name + " " + person.surName);
            ++i;
            if (i == 100){
                break;
            }
        }

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}

