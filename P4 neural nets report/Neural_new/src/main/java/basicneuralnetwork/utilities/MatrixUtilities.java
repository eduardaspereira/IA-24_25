package basicneuralnetwork.utilities;

import basicneuralnetwork.WrongDimensionException;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

/**
 * Classe utilitária para operações relacionadas a matrizes, incluindo conversões e manipulações.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que todas as operações com matrizes sejam realizadas corretamente, sem erros de dimensão ou outros problemas.
 */
public class MatrixUtilities {

    /**
     * Classe utilitária para operações relacionadas a matrizes, incluindo conversões e manipulações.
     *
     * @author Andreia Qiu 79856
     * @author Eduarda Pereira 79749
     * @author Guilherme Carmo 79860
     * @version 1.0 (09/12/2024)
     * @inv A classe garante que todas as operações com matrizes sejam realizadas corretamente, sem erros de dimensão ou outros problemas.
     */
    public static SimpleMatrix arrayToMatrix(double[] i) {
        double[][] input = {i};
        return new SimpleMatrix(input).transpose();
    }

    /**
     * Converte uma matriz SimpleMatrix em um array 2D.
     *
     * @param i A matriz SimpleMatrix a ser convertida.
     * @return O array 2D resultante.
     */
    public static double[][] matrixTo2DArray(SimpleMatrix i) {
        double[][] result = new double[i.numRows()][i.numCols()];

        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                result[j][k] = i.get(j, k);
            }
        }
        return result;
    }

    /**
     * Retorna uma coluna específica de uma matriz como um array 1D.
     *
     * @param data A matriz da qual a coluna será extraída.
     * @param column O índice da coluna a ser extraída.
     * @return O array 1D resultante contendo os elementos da coluna.
     */
    public static double[] getColumnFromMatrixAsArray(SimpleMatrix data, int column) {
        double[] result = new double[data.numRows()];

        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i, column);
        }

        return result;
    }

    /**
     * Mescla duas matrizes e retorna uma nova matriz resultante.
     *
     * @param matrixA A primeira matriz a ser mesclada.
     * @param matrixB A segunda matriz a ser mesclada.
     * @param probability A probabilidade de cada elemento da matriz resultante ser proveniente de matrixB.
     * @return A matriz resultante da mescla.
     * @throws WrongDimensionException Se as dimensões das duas matrizes não forem compatíveis.
     */
    public static SimpleMatrix mergeMatrices(SimpleMatrix matrixA, SimpleMatrix matrixB, double probability) {
        if (matrixA.numCols() != matrixB.numCols() || matrixA.numRows() != matrixB.numRows()) {
            throw new WrongDimensionException();
        } else {
            Random random = new Random();
            SimpleMatrix result = new SimpleMatrix(matrixA.numRows(), matrixA.numCols());

            for (int i = 0; i < matrixA.getNumElements(); i++) {
                // %-chance of replacing this value with the one from the input nn
                if (random.nextDouble() > probability) {
                    result.set(i, matrixA.get(i));
                } else {
                    result.set(i, matrixB.get(i));
                }
            }

            return result;
        }
    }

}
