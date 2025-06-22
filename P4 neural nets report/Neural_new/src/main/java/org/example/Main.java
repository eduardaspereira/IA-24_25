package org.example;
//new main
import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.utilities.CSVToDoubleArray;
import basicneuralnetwork.utilities.FileReaderAndWriter;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Load the training datasets
        List<double[]> train_dataset = CSVToDoubleArray.readCSVToDoubleArray("src/main/java/dataset/dataset.csv");
        List<double[]> train_labels = CSVToDoubleArray.readCSVToDoubleArray("src/main/java/dataset/labels.csv");

        // Load the test datasets (provided by your teacher)
        List<double[]> test_dataset = CSVToDoubleArray.readCSVToDoubleArray("src/main/java/dataset/dataset.csv");
        List<double[]> test_labels = CSVToDoubleArray.readCSVToDoubleArray("src/main/java/dataset/labels.csv");

        // Create a new Neural Network
        NeuralNetwork neuralNetwork = new NeuralNetwork(400, 1, 1, 1);

        // Number of training epochs
        int epochs = 10;

        // Train the network
        for (int n = 0; n < epochs; n++) {
            System.out.println("Iteration number " + (n + 1));
            System.out.println("Training...");

            for (int i = 0; i < train_dataset.size(); i++) {
                neuralNetwork.train(train_dataset.get(i), train_labels.get(i));
            }
        }

        // Test the network with the new dataset
        System.out.println("Testing on datasetcsv...");
        int correctPredictions = 0;

        for (int i = 0; i < test_dataset.size(); i++) {
            double[] data = test_dataset.get(i);
            double[] label = test_labels.get(i);

            // Get the network's prediction
            double prediction = neuralNetwork.guess(data)[0];

            // Convert prediction to binary classification (0 or 1)
            int predictedClass = (prediction > 0.5) ? 1 : 0; // If >0.5, classify as 1; otherwise 0.
            int actualClass = (int) label[0];

            System.out.println("Predicted: " + predictedClass);
            System.out.println("Actual: " + actualClass);
            System.out.println();

            // Check if the prediction is correct
            if (predictedClass == actualClass) {
                correctPredictions++;
            }
        }

        // Save the trained network to a file
        FileReaderAndWriter.writeToFile(neuralNetwork, "trained_network");
    }
}
