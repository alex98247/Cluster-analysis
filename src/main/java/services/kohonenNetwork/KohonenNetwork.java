package services.kohonenNetwork;

public interface KohonenNetwork<T> {
    void train(T person);
    int handle(T person);
    Neuron[] getConfiguration();
}
