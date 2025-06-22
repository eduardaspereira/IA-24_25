package basicneuralnetwork;

import basicneuralnetwork.activationfunctions.*;
import basicneuralnetwork.utilities.FileReaderAndWriter;
import basicneuralnetwork.utilities.MatrixUtilities;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.Random;

/**
 * Classe que representa uma rede neural artificial, incluindo métodos para inicialização, treinamento, previsão e manipulação de pesos e vieses.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que todas as operações com a rede neural sejam realizadas corretamente, incluindo a inicialização de pesos e vieses, o treinamento e a previsão.
 *
 */
public class NeuralNetwork {

    private ActivationFunctionFactory activationFunctionFactory = new ActivationFunctionFactory();

    private Random random = new Random();

    // Dimensions of the neural network
    private int inputNodes;
    private int hiddenLayers;
    private int hiddenNodes;
    private int outputNodes;

    private SimpleMatrix[] weights;
    private SimpleMatrix[] biases;

    private double learningRate;

    private String activationFunctionKey;

    /**
     * Construtor que gera uma nova rede neural com 1 camada oculta e o número especificado de nós nas camadas individuais.
     *
     * @param inputNodes Número de nós na camada de entrada.
     * @param hiddenNodes Número de nós na camada oculta.
     * @param outputNodes Número de nós na camada de saída.
     */
    // Generate a new neural network with 1 hidden layer with the given amount of nodes in the individual layers
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this(inputNodes, 1, hiddenNodes, outputNodes);
    }

    /**
     * Construtor que gera uma nova rede neural com o número especificado de camadas ocultas e nós nas camadas individuais.
     *
     * @param inputNodes Número de nós na camada de entrada.
     * @param hiddenLayers Número de camadas ocultas.
     * @param hiddenNodes Número de nós em cada camada oculta.
     * @param outputNodes Número de nós na camada de saída.
     */
    public NeuralNetwork(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenLayers = hiddenLayers;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeDefaultValues();
        initializeWeights();
        initializeBiases();
    }

    /**
     * Construtor de cópia que gera uma nova rede neural com os mesmos parâmetros e pesos da rede neural fornecida.
     *
     * @param nn A rede neural a ser copiada.
     */
    public NeuralNetwork(NeuralNetwork nn) {
        this.inputNodes = nn.inputNodes;
        this.hiddenLayers = nn.hiddenLayers;
        this.hiddenNodes = nn.hiddenNodes;
        this.outputNodes = nn.outputNodes;

        this.weights = new SimpleMatrix[hiddenLayers + 1];
        this.biases = new SimpleMatrix[hiddenLayers + 1];

        for (int i = 0; i < nn.weights.length; i++) {
            this.weights[i] = nn.weights[i].copy();
        }

        for (int i = 0; i < nn.biases.length; i++) {
            this.biases[i] = nn.biases[i].copy();
        }

        this.learningRate = nn.learningRate;

        this.activationFunctionKey = nn.activationFunctionKey;
    }

    /**
     * Inicializa os valores padrão da rede neural, como a taxa de aprendizado e a função de ativação.
     */
    private void initializeDefaultValues() {
        this.setLearningRate(0.1);

        // Sigmoid is the default ActivationFunction
        this.setActivationFunction(ActivationFunction.SIGMOID);
    }

    /**
     * Inicializa os pesos da rede neural com valores aleatórios.
     */
    private void initializeWeights() {
        weights = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the weights between the layers and fill them with random values
        for (int i = 0; i < weights.length; i++) {
            if (i == 0) { // 1st weights that connects inputs to first hidden layer
                weights[i] = SimpleMatrix.random64(hiddenNodes, inputNodes, -1, 1, random);
            } else if (i == weights.length - 1) { // last weights that connect last hidden layer to output
                weights[i] = SimpleMatrix.random64(outputNodes, hiddenNodes, -1, 1, random);
            } else { // everything else
                weights[i] = SimpleMatrix.random64(hiddenNodes, hiddenNodes, -1, 1, random);
            }
        }
    }

    /**
     * Inicializa os vieses da rede neural com valores aleatórios.
     */
    private void initializeBiases() {
        biases = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the biases and fill them with random values
        for (int i = 0; i < biases.length; i++) {
            if (i == biases.length - 1) { // bias for last layer (output layer)
                biases[i] = SimpleMatrix.random64(outputNodes, 1, -1, 1, random);
            } else {
                biases[i] = SimpleMatrix.random64(hiddenNodes, 1, -1, 1, random);
            }
        }
    }

    /**
     * Método de previsão, onde a entrada é uma matriz de uma coluna com os valores de entrada.
     *
     * @param input O array de entrada a ser usado para a previsão.
     * @return O array de saída resultante da previsão.
     * @throws WrongDimensionException Se a dimensão do array de entrada não corresponder ao número de nós de entrada.
     */

    public double[] guess(double[] input) {
        if (input.length != inputNodes){
            throw new WrongDimensionException(input.length, inputNodes, "Input");
        } else {
            // Get ActivationFunction-object from the map by key
            ActivationFunction activationFunction = activationFunctionFactory.getActivationFunctionByKey(activationFunctionKey);

            // Transform array to matrix
            SimpleMatrix output = MatrixUtilities.arrayToMatrix(input);

            for (int i = 0; i < hiddenLayers + 1; i++) {
                output = calculateLayer(weights[i], biases[i], output, activationFunction);
            }

            return MatrixUtilities.getColumnFromMatrixAsArray(output, 0);
        }
    }

    /**
     * Treina a rede neural com os dados de entrada e de saída esperada.
     *
     * @param inputArray O array de entrada para treinamento.
     * @param targetArray O array de saída esperada para treinamento.
     * @throws WrongDimensionException Se as dimensões dos arrays de entrada ou saída não corresponderem às dimensões da rede neural.
     */
    public void train(double[] inputArray, double[] targetArray) {
        if (inputArray.length != inputNodes) {
            throw new WrongDimensionException(inputArray.length, inputNodes, "Input");
        } else if (targetArray.length != outputNodes) {
            throw new WrongDimensionException(targetArray.length, outputNodes, "Output");
        } else {
            // Get ActivationFunction-object from the map by key
            ActivationFunction activationFunction = activationFunctionFactory.getActivationFunctionByKey(activationFunctionKey);

            // Transform 2D array to matrix
            SimpleMatrix input = MatrixUtilities.arrayToMatrix(inputArray);
            SimpleMatrix target = MatrixUtilities.arrayToMatrix(targetArray);

            // Calculate the values of every single layer
            SimpleMatrix layers[] = new SimpleMatrix[hiddenLayers + 2];
            layers[0] = input;
            for (int j = 1; j < hiddenLayers + 2; j++) {
                layers[j] = calculateLayer(weights[j - 1], biases[j - 1], input, activationFunction);
                input = layers[j];
            }

            for (int n = hiddenLayers + 1; n > 0; n--) {
                // Calculate error
                SimpleMatrix errors = target.minus(layers[n]);

                // Calculate gradient
                SimpleMatrix gradients = calculateGradient(layers[n], errors, activationFunction);

                // Calculate delta
                SimpleMatrix deltas = calculateDeltas(gradients, layers[n - 1]);

                // Apply gradient to bias
                biases[n - 1] = biases[n - 1].plus(gradients);

                // Apply delta to weights
                weights[n - 1] = weights[n - 1].plus(deltas);

                // Calculate and set target for previous (next) layer
                SimpleMatrix previousError = weights[n - 1].transpose().mult(errors);
                target = previousError.plus(layers[n - 1]);
            }
        }
    }



    /**
     * Gera uma cópia exata da rede neural.
     *
     * @return Uma nova instância de NeuralNetwork que é uma cópia exata da rede neural atual.
     */
    public NeuralNetwork copy(){
        return new NeuralNetwork(this);
    }

    /**
     * Mescla os pesos e vieses de duas redes neurais e retorna um novo objeto.
     * A proporção de mescla é 50:50 (metade dos valores será da nn1 e a outra metade da nn2).
     *
     * @param nn A segunda rede neural a ser mesclada.
     * @return Uma nova instância de NeuralNetwork resultante da mescla.
     */
    public NeuralNetwork merge(NeuralNetwork nn){
        return this.merge(nn, 0.5);
    }

    /**
     * Mescla os pesos e vieses de duas redes neurais e retorna um novo objeto.
     * Tudo além dos pesos e vieses será o mesmo do objeto em que este método é chamado (taxa de aprendizado, função de ativação, etc.).
     * A proporção de mescla é definida por probabilidade.
     *
     * @param nn A segunda rede neural a ser mesclada.
     * @param probability A probabilidade de cada elemento da matriz resultante ser proveniente de nn2.
     * @return Uma nova instância de NeuralNetwork resultante da mescla.
     * @throws WrongDimensionException Se as dimensões das duas redes neurais não forem compatíveis.
     */
    public NeuralNetwork merge(NeuralNetwork nn, double probability){
        // Check whether the nns have the same dimensions
        if(!Arrays.equals(this.getDimensions(), nn.getDimensions())){
            throw new WrongDimensionException(this.getDimensions(), nn.getDimensions());
        }else{
            NeuralNetwork result = this.copy();

            for (int i = 0; i < result.weights.length; i++) {
                result.weights[i] = MatrixUtilities.mergeMatrices(this.weights[i], nn.weights[i], probability);
            }

            for (int i = 0; i < result.biases.length; i++) {
                result.biases[i] = MatrixUtilities.mergeMatrices(this.biases[i], nn.biases[i], probability);
            }
            return result;
        }
    }

    /**
     * Realiza uma mutação gaussiana com a probabilidade dada, modificando ligeiramente os valores (pesos + vieses) com a probabilidade dada.
     * A probabilidade é um número entre 0 e 1. Dependendo da probabilidade, mais ou menos valores serão mutados (por exemplo, prob = 1.0: todos os valores serão mutados).
     *
     * @param probability A probabilidade de cada valor ser mutado.
     */
    public void mutate(double probability) {
        applyMutation(weights, probability);
        applyMutation(biases, probability);
    }

    /**
     * Adiciona um número gaussiano gerado aleatoriamente a cada elemento de uma matriz em um array de matrizes.
     * A probabilidade determina quantos valores serão modificados.
     *
     * @param matrices O array de matrizes a serem mutadas.
     * @param probability A probabilidade de cada valor ser mutado.
     */
    private void applyMutation(SimpleMatrix[] matrices, double probability) {
        for (SimpleMatrix matrix : matrices) {
            for (int j = 0; j < matrix.getNumElements(); j++) {
                if (random.nextDouble() < probability) {
                    double offset = random.nextGaussian() / 2;
                    matrix.set(j, matrix.get(j) + offset);
                }
            }
        }
    }

    /**
     * Função genérica para calcular uma camada.
     *
     * @param weights Os pesos da camada.
     * @param bias O viés da camada.
     * @param input A entrada da camada.
     * @param activationFunction A função de ativação a ser aplicada.
     * @return A matriz resultante da camada calculada.
     */
    private SimpleMatrix calculateLayer(SimpleMatrix weights, SimpleMatrix bias, SimpleMatrix input, ActivationFunction activationFunction) {
        // Calculate outputs of layer
        SimpleMatrix result = weights.mult(input);
        // Add bias to outputs
        result = result.plus(bias);
        // Apply activation function and return result
        return applyActivationFunction(result, false, activationFunction);
    }

    /**
     * Calcula o gradiente.
     *
     * @param layer A camada atual.
     * @param error O erro da camada.
     * @param activationFunction A função de ativação a ser aplicada.
     * @return A matriz resultante do gradiente calculado.
     */
    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error, ActivationFunction activationFunction) {
        SimpleMatrix gradient = applyActivationFunction(layer, true, activationFunction);
        gradient = gradient.elementMult(error);
        return gradient.scale(learningRate);
    }

    /**
     * Calcula os deltas.
     *
     * @param gradient O gradiente calculado.
     * @param layer A camada anterior.
     * @return A matriz resultante dos deltas calculados.
     */
    private SimpleMatrix calculateDeltas(SimpleMatrix gradient, SimpleMatrix layer) {
        return gradient.mult(layer.transpose());
    }

    /**
     * Aplica uma função de ativação a uma matriz.
     * Um objeto de uma implementação da interface ActivationFunction deve ser passado.
     * A função nesta classe será aplicada à matriz.
     *
     * @param input A matriz de entrada.
     * @param derivative Indica se a derivada da função de ativação deve ser aplicada.
     * @param activationFunction A função de ativação a ser aplicada.
     * @return A matriz resultante após a aplicação da função de ativação.
     */
    private SimpleMatrix applyActivationFunction(SimpleMatrix input, boolean derivative, ActivationFunction activationFunction) {
        // Applies either derivative of activation function or regular activation function to a matrix and returns the result
        return derivative ? activationFunction.applyDerivativeOfActivationFunctionToMatrix(input)
                          : activationFunction.applyActivationFunctionToMatrix(input);
    }

    /**
     * Salva a rede neural em um arquivo JSON.
     */
    public void writeToFile() {
        FileReaderAndWriter.writeToFile(this, null);
    }

    /**
     * Salva a rede neural em um arquivo JSON com o nome especificado.
     *
     * @param fileName O nome do arquivo onde a rede neural será salva.
     */
    public void writeToFile(String fileName) {
        FileReaderAndWriter.writeToFile(this, fileName);
    }

    /**
     * Carrega uma rede neural de um arquivo JSON.
     *
     * @return A rede neural carregada do arquivo.
     */
    public static NeuralNetwork readFromFile() {
        return FileReaderAndWriter.readFromFile(null);
    }

    /**
     * Carrega uma rede neural de um arquivo JSON com o nome especificado.
     *
     * @param fileName O nome do arquivo de onde a rede neural será carregada.
     * @return A rede neural carregada do arquivo.
     */
    public static NeuralNetwork readFromFile(String fileName) {
        return FileReaderAndWriter.readFromFile(fileName);
    }

    /**
     * Obtém o nome da função de ativação atualmente definida na rede neural.
     *
     * @return O nome da função de ativação.
     */
    public String getActivationFunctionName() {
        return activationFunctionKey;
    }

    /**
     * Define a função de ativação da rede neural.
     *
     * @param activationFunction O nome da função de ativação a ser definida.
     */
    public void setActivationFunction(String activationFunction) {
        this.activationFunctionKey = activationFunction;
    }

    /**
     * Adiciona uma nova função de ativação ao mapa de funções de ativação.
     *
     * @param key A chave que identificará a função de ativação.
     * @param activationFunction A função de ativação a ser adicionada.
     */
    public void addActivationFunction(String key, ActivationFunction activationFunction){
        activationFunctionFactory.addActivationFunction(key, activationFunction);
    }

    /**
     * Obtém a taxa de aprendizado atualmente definida na rede neural.
     *
     * @return A taxa de aprendizado.
     */
    public double getLearningRate() {
        return learningRate;
    }

    /**
     * Define a taxa de aprendizado da rede neural.
     *
     * @param learningRate A taxa de aprendizado a ser definida.
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * Obtém o número de nós na camada de entrada.
     *
     * @return O número de nós na camada de entrada.
     */
    public int getInputNodes() {
        return inputNodes;
    }

    /**
     * Obtém o número de camadas ocultas.
     *
     * @return O número de camadas ocultas.
     */
    public int getHiddenLayers() {
        return hiddenLayers;
    }

    /**
     * Obtém o número de nós em cada camada oculta.
     *
     * @return O número de nós em cada camada oculta.
     */
    public int getHiddenNodes() {
        return hiddenNodes;
    }

    /**
     * Obtém o número de nós na camada de saída.
     *
     * @return O número de nós na camada de saída.
     */
    public int getOutputNodes() {
        return outputNodes;
    }

    /**
     * Obtém os pesos da rede neural.
     *
     * @return Os pesos da rede neural.
     */
    public SimpleMatrix[] getWeights() {
        return weights;
    }

    /**
     * Define os pesos da rede neural.
     *
     * @param weights Os pesos a serem definidos.
     */
    public void setWeights(SimpleMatrix[] weights) {
        this.weights = weights;
    }

    /**
     * Obtém os vieses da rede neural.
     *
     * @return Os vieses da rede neural.
     */
    public SimpleMatrix[] getBiases() {
        return biases;
    }

    /**
     * Define os vieses da rede neural.
     *
     * @param biases Os vieses a serem definidos.
     */
    public void setBiases(SimpleMatrix[] biases) {
        this.biases = biases;
    }

    /**
     * Obtém as dimensões da rede neural.
     *
     * @return Um array de inteiros representando as dimensões da rede neural.
     */
    public int[] getDimensions(){
        return new int[]{inputNodes, hiddenLayers, hiddenNodes, outputNodes};
    }

}
