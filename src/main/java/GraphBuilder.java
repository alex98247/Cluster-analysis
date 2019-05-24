import info.debatty.java.stringsimilarity.JaroWinkler;
import org.jgrapht.ListenableGraph;

import java.time.Period;
import java.util.*;

public class GraphBuilder{
    static List<HashSet<Person>> build(int maxPersonNumber, double maxDistance, ListenableGraph<Person, MyJGraphXAdapter.MyEdge> g, int clusterNumber){
        List<Person> generate = Generator.generate(maxPersonNumber);
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
                        MyJGraphXAdapter.MyEdge edge = g.addEdge(person, curPerson);
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
            }*/
            ++i;
        }

        clustersList.sort((o1, o2) -> o2.size() - o1.size());
        List<HashSet<Person>> finalClusterList = new ArrayList<HashSet<Person>>();
        for (i = 0; i < clusterNumber; ++i){
            finalClusterList.add(clustersList.get(i));
        }
        for (i = clusterNumber; i < clustersList.size(); ++i){
            HashSet<Person> minCluster = finalClusterList.get(clusterNumber - 1);
            minCluster.addAll(clustersList.get(i));
            finalClusterList.set(clusterNumber - 1, minCluster);
            finalClusterList.sort((o1, o2) -> o2.size() - o1.size());
        }
        int sumSize = 0;
        for (HashSet<Person> cluster : finalClusterList){
            sumSize += cluster.size();
        }
        System.out.println("SumSize = " + sumSize + "\n");
//        clustersList.sort(Comparator.comparingInt(HashSet::size));
        System.out.println("There are " + finalClusterList.size() + " clusters");
        for (HashSet<Person> cluster : finalClusterList){
            System.out.print(cluster.size() + "   ");
        }
        return clustersList;
    }
}
