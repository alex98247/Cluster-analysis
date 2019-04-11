package services.kohonenNetwork;

import models.Entity;

public interface KohonenNetwork {
    void train(Entity person) throws Exception;
    int handle(Entity person) throws Exception;
    Neuron[] getConfiguration();
}
