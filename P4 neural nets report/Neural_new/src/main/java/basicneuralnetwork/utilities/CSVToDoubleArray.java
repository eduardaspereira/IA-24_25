package basicneuralnetwork.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para ler um arquivo CSV e convertê-lo em uma lista de arrays de doubles.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que cada linha do arquivo CSV seja convertida corretamente em um array de doubles.
 */
public class CSVToDoubleArray {

    /**
     * Lê um arquivo CSV e converte cada linha em um array de doubles.
     *
     * @param filePath O caminho do arquivo CSV a ser lido.
     * @return Uma lista de arrays de doubles, onde cada array representa uma linha do arquivo CSV.
     * @throws IOException Se ocorrer um erro de leitura do arquivo.
     */
    public static List<double[]> readCSVToDoubleArray(String filePath) {
        List<double[]> dataset = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line
            while ((line = br.readLine()) != null) {
                // Split the line into a String array
                String[] stringValues = line.split(",");

                // Convert String array to double array
                double[] doubleValues = new double[stringValues.length];
                for (int i = 0; i < stringValues.length; i++) {
                    doubleValues[i] = Double.parseDouble(stringValues[i]);
                }

                // Add to the dataset
                dataset.add(doubleValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }
}
