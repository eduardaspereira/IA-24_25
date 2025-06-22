package basicneuralnetwork.utilities;

import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.ejml.data.Matrix;
import org.ejml.simple.SimpleOperations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe utilitária para salvar e carregar uma rede neural em/de um arquivo JSON.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que a rede neural seja corretamente serializada e desserializada, preservando todas as informações necessárias.
 */
public class FileReaderAndWriter {

    /**
     * Salva uma rede neural em um arquivo JSON.
     *
     * @param nn A rede neural a ser salva.
     * @param fileName O nome do arquivo onde a rede neural será salva. Se null, o nome padrão será "nn_data.json".
     * @throws IOException Se ocorrer um erro durante a escrita do arquivo.
     */
    public static void writeToFile(NeuralNetwork nn, String fileName){
        String name = fileName;

        if (fileName == null) {
            name = "nn_data";
        }

        try {
            FileWriter file = new FileWriter(name + ".json");
            Gson gson = getGsonBuilder().create();
            String nnData = gson.toJson(nn);

            file.write(nnData);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega uma rede neural de um arquivo JSON.
     *
     * @param fileName O nome do arquivo de onde a rede neural será carregada. Se null, o nome padrão será "nn_data.json".
     * @return A rede neural carregada do arquivo.
     * @throws IOException Se ocorrer um erro durante a leitura do arquivo.
     */
    public static NeuralNetwork readFromFile(String fileName) {
        NeuralNetwork nn = null;
        String name = fileName;

        if (fileName == null) {
            name = "nn_data.json";
        }

        try {
            Gson gson = getGsonBuilder().create();
            JsonReader jsonReader = new JsonReader(new FileReader(name));
            nn = gson.fromJson(jsonReader, NeuralNetwork.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nn;
    }

    /**
     * Obtém um GsonBuilder com todos os TypeAdapters necessários registrados.
     *
     * @return Um GsonBuilder configurado para serializar e desserializar a rede neural.
     */
    private static GsonBuilder getGsonBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ActivationFunction.class, new InterfaceAdapter<ActivationFunction>());
        gsonBuilder.registerTypeAdapter(Matrix.class, new InterfaceAdapter<Matrix>());
        gsonBuilder.registerTypeAdapter(SimpleOperations.class, new InterfaceAdapter<SimpleOperations>());
        gsonBuilder.setPrettyPrinting();

        return gsonBuilder;
    }

}
