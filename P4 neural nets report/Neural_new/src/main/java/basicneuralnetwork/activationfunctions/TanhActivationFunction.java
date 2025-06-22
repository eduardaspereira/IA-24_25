package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Implementação da função de ativação Tanh (Tangente Hiperbólica) para uma rede neural.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que a função de ativação Tanh e sua derivada sejam aplicadas corretamente a uma matriz de entrada.
 */
public class TanhActivationFunction implements ActivationFunction {

    private static final String NAME = "TANH";

    /**
     * Aplica a função de ativação Tanh a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a função de ativação Tanh será aplicada.
     * @return A matriz resultante após a aplicação da função de ativação Tanh.
     */
    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = Math.tanh(value);

            output.set(i, 0, result);
        }

        // Formula:
        // 2 * (1 / (1 + Math.exp(2 * -input))) - 1;
        // Math.tanh(input);
        return output;
    }

    /**
     * Aplica a derivada da função de ativação Tanh a uma matriz de entrada.
     *
     * @param input A matriz de entrada à qual a derivada da função de ativação Tanh será aplicada.
     * @return A matriz resultante após a aplicação da derivada da função de ativação Tanh.
     */
    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 1 - (value * value);

            output.set(i, 0, result);
        }

        // Formula:
        // 1 - (input * input);
        return output;
    }

    /**
     * Aplica a derivada da função de ativação Tanh a uma matriz de entrada.
     *
     * @return A matriz resultante após a aplicação da derivada da função de ativação Tanh.
     */
    public String getName() {
        return NAME;
    }
}
