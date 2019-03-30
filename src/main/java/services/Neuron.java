package services;

import java.util.Arrays;

public class Neuron {
    public double[] weight;

    Neuron(int inputDimension) {
        weight = new double[inputDimension];
        //Arrays.stream(weight).forEach(x -> Math.random());
        for (int i = 0; i < weight.length; i++) weight[i] = 2 * Math.random() - 1;
    }
}
