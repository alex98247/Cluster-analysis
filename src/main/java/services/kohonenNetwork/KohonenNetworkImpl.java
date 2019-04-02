package services.kohonenNetwork;

import models.Entity;
import tools.Metric;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KohonenNetworkImpl implements KohonenNetwork {

    private Neuron[] neurons;
    double learningConstant = 0.3;
    int inputDimension;
    int outDimension;


    public KohonenNetworkImpl(int inputDimension, int outDimension) {
        this.inputDimension = inputDimension;
        this.outDimension = outDimension;

        neurons = new Neuron[outDimension];
        for (int i = 0; i < outDimension; i++) neurons[i] = new Neuron(inputDimension);
    }

    public void train(Entity entity) throws Exception {

        double field = entity.getAge();
        int minIndex = getMinIndex(entity);

        for (int i = 0; i < inputDimension; i++) {
            neurons[minIndex].weight[i] += learningConstant * (field - neurons[minIndex].weight[i]);
        }
    }

    public int handle(Entity entity) throws Exception {

        return getMinIndex(entity);
    }

    private int getMinIndex(Entity entity) throws Exception {

        double field = entity.getAge();
        List<Double> distances = new LinkedList<>();

        for (int i = 0; i < outDimension; i++)
            distances.add(Metric.euclidMetric(new double[]{field}, neurons[i].weight));
        int minIndex = distances.indexOf(Collections.min(distances));
        return minIndex;
    }

    public Neuron[] getConfiguration() {
        return neurons;
    }
}
