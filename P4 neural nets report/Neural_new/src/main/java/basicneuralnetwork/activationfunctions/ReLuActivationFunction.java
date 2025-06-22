package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Implementação da função de ativação ReLU (Rectified Linear Unit) para uma rede neural.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que a função de ativação ReLU e sua derivada sejam aplicadas corretamente a uma matriz de entrada.
 *
 */
public class ReLuActivationFunction implements ActivationFunction {

    /**
     * Nome da função de ativação ReLU.
     */
    private static final String NAME = "RELU";

    /**
     * Aplica a função de ativação ReLU a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a função de ativação ReLU será aplicada.
     * @return A matriz resultante após a aplicação da função de ativação ReLU.
     */
    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = value > 0 ? value : 0;

            output.set(i, 0, result);
        }

        // Formula:
        // for input < 0: 0, else input
        return output;
    }

    /**
     * Aplica a derivada da função de ativação ReLU a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a derivada da função de ativação ReLU será aplicada.
     * @return A matriz resultante após a aplicação da derivada da função de ativação ReLU.
     */
    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = value > 0 ? 1 : 0;

            output.set(i, 0, result);
        }

        // Formula:
        // for input > 0: 1, else 0
        return output;
    }

    /**
     * Retorna o nome da função de ativação ReLU.
     *
     * @return O nome da função de ativação ReLU como uma string.
     */
    public String getName() {
        return NAME;
    }
}
