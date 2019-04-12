package services.kohonenNetwork;

import models.Entity;

public interface KohonenNetwork<T> {
    void train(T person) throws Exception;
    int handle(T person) throws Exception;
    Neuron[] getConfiguration();
}
