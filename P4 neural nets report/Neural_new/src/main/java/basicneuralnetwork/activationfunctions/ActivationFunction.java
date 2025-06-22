
package basicneuralnetwork.activationfunctions;
import org.ejml.simple.SimpleMatrix;

/**
 * Interface que define as operações básicas para funções de ativação em uma rede neural.
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv Todas as classes que implementam esta interface devem garantir que os métodos `applyActivationFunctionToMatrix` e `applyDerivativeOfActivationFunctionToMatrix` sejam consistentes com a função de ativação e sua derivada, respectivamente.
 */
public interface ActivationFunction {

    /**
     * Constantes que representam os tipos de funções de ativação suportadas.
     */
    String SIGMOID = "SIGMOID";
    String TANH = "TANH";
    String RELU = "RELU";

    /**
     * Aplica a função de ativação a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a função de ativação será aplicada.
     * @return A matriz resultante após a aplicação da função de ativação.
     */
    SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input);

    /**
     * Aplica a derivada da função de ativação a uma matriz de entrada.
     * Note que esta não é a derivada real, pois a função de ativação já foi aplicada à entrada.
     *
     * @param input A matriz de entrada à qual a derivada da função de ativação será aplicada.
     * @return A matriz resultante após a aplicação da derivada da função de ativação.
     */
    SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input);

    /**
     * Retorna o nome da função de ativação.
     *
     * @return O nome da função de ativação como uma string.
     */
    String getName();

}
