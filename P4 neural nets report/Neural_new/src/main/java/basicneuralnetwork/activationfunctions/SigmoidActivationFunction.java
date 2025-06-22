package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Implementação da função de ativação Sigmoid para uma rede neural.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que a função de ativação Sigmoid e sua derivada sejam aplicadas corretamente a uma matriz de entrada.
 *
 */
public class SigmoidActivationFunction implements ActivationFunction {

    /**
     * Nome da função de ativação Sigmoid.
     */
    private static final String NAME = "SIGMOID";

    /**
     * Aplica a função de ativação Sigmoid a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a função de ativação Sigmoid será aplicada.
     * @return A matriz resultante após a aplicação da função de ativação Sigmoid.
     */
    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 1 / (1 + Math.exp(-value));

            output.set(i, 0, result);
        }

        // Formula:
        // 1 / (1 + Math.exp(-input));
        return output;
    }

    /**
     * Aplica a derivada da função de ativação Sigmoid a uma matriz de entrada.
     * Note que esta não é a derivada real, pois a função de ativação já foi aplicada à entrada.
     *
     * @param input A matriz de entrada à qual a derivada da função de ativação Sigmoid será aplicada.
     * @return A matriz resultante após a aplicação da derivada da função de ativação Sigmoid.
     */
    // Derivative of Sigmoid (not real derivative because Activation function has already been applied to the input)
    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = value * (1 - value);

            output.set(i, 0, result);
        }

        // Formula:
        // input * (1 - input);
        return output;
    }

    /**
     * Retorna o nome da função de ativação Sigmoid.
     *
     * @return O nome da função de ativação Sigmoid como uma string.
     */
    public String getName() {
        return NAME;
    }
}
