package services;

import models.Entity;
import tools.Metric;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KohonenNetworkImpl implements KohonenNetwork {

    public Neuron[] neurons;
    double learningConstant = 0.3;
    double inputDimension;

    public KohonenNetworkImpl(int inputDimension, int neuronCount) {
        this.inputDimension = inputDimension;
        neurons = new Neuron[neuronCount];
        for(int i = 0; i < neurons.length; i++) neurons[i] = new Neuron(inputDimension);
    }

    public void train(Entity person) throws Exception {
        List<Double> distances = new LinkedList<>();

        for (int i = 0; i < neurons.length; i++) {
            distances.add(Metric.euclidMetric(person.fields, neurons[i].weight));
        }

        int minIndex = distances.indexOf(Collections.min(distances));
        for (int i = 0; i < inputDimension; i++) {
            neurons[minIndex].weight[i] += learningConstant * (person.fields[i] - neurons[minIndex].weight[i]);
        }
    }

    public int handle(Entity person) throws Exception {
        List<Double> power = new LinkedList<>();
        for (int i = 0; i < neurons.length; i++) {
            power.add(Metric.euclidMetric(person.fields, neurons[i].weight));
        }
        int maxIndex = power.indexOf(Collections.min(power));
        return maxIndex;
    }
}
