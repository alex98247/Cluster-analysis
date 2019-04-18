package services.kohonenNetwork;

import org.apache.log4j.Logger;
import services.formatterService.FormatterService;
import services.generatorService.GeneratorService;
import services.tools.MetricService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KohonenNetworkImpl<T> implements KohonenNetwork<T> {

    private static final Logger logger = Logger.getLogger(KohonenNetworkImpl.class);
    private Neuron[] neurons;
    private final double learningConstant = 0.3;
    private final int inputDimension;
    private final int outDimension;
    private FormatterService<T> formatterService;


    public KohonenNetworkImpl(FormatterService<T> formatterService, int inputDimension, int outDimension) {
        this.formatterService = formatterService;
        this.inputDimension = inputDimension;
        this.outDimension = outDimension;

        neurons = new Neuron[outDimension];
        for (int i = 0; i < outDimension; i++) neurons[i] = new Neuron(inputDimension);
        logger.info("Network init completed");
    }

    public void train(T entity) {
        double[] formattedFields = formatterService.formatToDouble(entity);
        int minDistanceIndex = getMinDistanceIndex(entity);

        //Update neuron weights for neuron with minDistanceIndex
        for (int i = 0; i < inputDimension; i++) {
            double fieldValue = formattedFields[i];
            double weight = neurons[minDistanceIndex].weight[i];
            neurons[minDistanceIndex].weight[i] += learningConstant * (fieldValue - weight);
        }
    }

    public int handle(T entity) {
        return getMinDistanceIndex(entity);
    }

    private int getMinDistanceIndex(T entity) {

        double[] formattedFields = formatterService.formatToDouble(entity);
        List<Double> distances = new LinkedList<>();

        for (int i = 0; i < outDimension; i++)
            distances.add(MetricService.euclidMetric(formattedFields, neurons[i].weight));

        double minDistance = Collections.min(distances);
        int minDistanceIndex = distances.indexOf(minDistance);
        return minDistanceIndex;
    }

    public Neuron[] getConfiguration() {
        return neurons;
    }
}
