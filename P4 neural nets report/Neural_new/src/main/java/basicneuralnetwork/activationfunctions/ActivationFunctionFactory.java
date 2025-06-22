package basicneuralnetwork.activationfunctions;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por criar e gerenciar instâncias de funções de ativação para uma rede neural.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que todas as funções de ativação armazenadas no mapa sejam válidas e que a chave correspondente seja única.
 */
public class ActivationFunctionFactory {

    /**
     * Mapa que armazena as funções de ativação indexadas por suas chaves.
     */
    private Map<String, ActivationFunction> activationFunctionMap = new HashMap<>();

    /**
     * Construtor que inicializa o mapa com as funções de ativação básicas: Sigmoid, Tanh e ReLU.
     */
    public ActivationFunctionFactory () {
        // Fill map with all the activation functions
        ActivationFunction sigmoid = new SigmoidActivationFunction();
        activationFunctionMap.put(sigmoid.getName(), sigmoid);

        ActivationFunction tanh = new TanhActivationFunction();
        activationFunctionMap.put(tanh.getName(), tanh);

        ActivationFunction relu = new ReLuActivationFunction();
        activationFunctionMap.put(relu.getName(), relu);
    }

    /**
     * Obtém uma função de ativação a partir de uma chave.
     *
     * @param activationFunctionKey A chave que identifica a função de ativação desejada.
     * @return A função de ativação correspondente à chave fornecida, ou null se a chave não existir.
     */
    public ActivationFunction getActivationFunctionByKey (String activationFunctionKey) {
        return activationFunctionMap.get(activationFunctionKey);
    }

    /**
     * Adiciona uma nova função de ativação ao mapa.
     *
     * @param key A chave que identificará a função de ativação.
     * @param activationFunction A função de ativação a ser adicionada.
     */
    public void addActivationFunction(String key, ActivationFunction activationFunction) {
        activationFunctionMap.put(key, activationFunction);
    }
}
