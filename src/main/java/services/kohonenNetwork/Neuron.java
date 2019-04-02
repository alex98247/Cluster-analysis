package services.kohonenNetwork;

public class Neuron {
    public double[] weight;

    Neuron(int inputDimension) {
        weight = new double[inputDimension];
        for (int i = 0; i < weight.length; i++) weight[i] = 2 * Math.random() - 1;
    }
}
