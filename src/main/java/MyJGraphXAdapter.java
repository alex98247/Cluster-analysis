import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import org.jgrapht.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.awt.*;

public class MyJGraphXAdapter    extends
        JApplet {

    private int maxDistance;

    private int maxPersonNumber;

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<Person, MyEdge> jgxAdapter;

    public MyJGraphXAdapter(int maxPersonNumber, int maxDistance){
        this.maxPersonNumber = maxPersonNumber;
        this.maxDistance = maxDistance;
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
        component.zoomOut();
        component.zoomOut();
        component.zoomOut();

        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        List<Person> generate = Generator.generate(100000);
        Iterator<Person> iterator = generate.iterator();

        int i = 0;
        HashSet<Person> personSet = new HashSet<Person>();
        while (iterator.hasNext() && i < maxPersonNumber){
            Person person = iterator.next();
            g.addVertex(person);
            personSet.add(person);
            for (Person curPerson:personSet) {
                if (curPerson.equals(person))
                    continue;
                int distance = Metrica.levenshtein(person.name, curPerson.name);
                if (distance < maxDistance) {
                    MyEdge edge = g.addEdge(person, curPerson);
                    g.setEdgeWeight(edge, distance);
                }
            }
            ++i;
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
    }
}

