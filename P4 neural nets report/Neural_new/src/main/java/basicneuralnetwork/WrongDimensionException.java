package basicneuralnetwork;

import java.util.Arrays;

/**
 * Exceção lançada quando as dimensões de uma camada ou de duas redes neurais não correspondem às esperadas.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 */
public class WrongDimensionException extends RuntimeException {

    /**
     * Construtor que cria uma exceção com uma mensagem específica para a camada com dimensões incorretas.
     *
     * @param actual O número real de valores.
     * @param expected O número esperado de valores.
     * @param layer A camada onde a dimensão incorreta foi encontrada.
     */
    public WrongDimensionException(int actual, int expected, String layer){
        super("Expected " + expected + " value(s) for " + layer + "-layer but got " + actual + ".");
    }

    /**
     * Construtor que cria uma exceção com uma mensagem específica para duas redes neurais com dimensões incompatíveis.
     *
     * @param actual As dimensões reais da rede neural.
     * @param expected As dimensões esperadas da rede neural.
     */
    public WrongDimensionException(int[] actual, int[] expected){
        super("The dimensions of these two neural networks don't match: " + Arrays.toString(actual) + ", " + Arrays.toString(expected));
    }

    /**
     * Construtor que cria uma exceção com uma mensagem genérica para dimensões incompatíveis.
     */
    public WrongDimensionException(){
        super("Dimensions don't match.");
    }

}
