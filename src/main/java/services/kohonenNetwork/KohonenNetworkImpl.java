package services.kohonenNetwork;

import models.Entity;
import services.tools.MetricService;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KohonenNetworkImpl implements KohonenNetwork {

    private Neuron[] neurons;
    private final double learningConstant = 0.3;
    private final int inputDimension;
    private final int outDimension;


    public KohonenNetworkImpl(int inputDimension, int outDimension) {
        this.inputDimension = inputDimension;
        this.outDimension = outDimension;

        neurons = new Neuron[outDimension];
        for (int i = 0; i < outDimension; i++) neurons[i] = new Neuron(inputDimension);
    }

    public void train(Entity entity) throws Exception {
        Field[] a = entity.getClass().getDeclaredFields();
        int minDistanceIndex = getMinDistanceIndex(entity);

        //Update neuron weights for neuron with minDistanceIndex
        for (int i = 0; i < inputDimension; i++) {

            Field field = a[i];
            field.setAccessible(true);
            double fieldValue = field.getDouble(entity);
            double weight = neurons[minDistanceIndex].weight[i];
            neurons[minDistanceIndex].weight[i] += learningConstant * (fieldValue - weight);
        }
    }

    public int handle(Entity entity) throws Exception { return getMinDistanceIndex(entity); }

    private int getMinDistanceIndex(Entity entity) throws Exception {

        double field = entity.getAge();
        List<Double> distances = new LinkedList<>();

        for (int i = 0; i < outDimension; i++)
            distances.add(MetricService.euclidMetric(new double[]{field}, neurons[i].weight));

        double minDistance = Collections.min(distances);
        int minDistanceIndex = distances.indexOf(minDistance);
        return minDistanceIndex;
    }

    private boolean isValid(Field field){
        if(field.getType() == Boolean.TYPE) return true;
        return false;
    }

    public Neuron[] getConfiguration() {
        return neurons;
    }
}
