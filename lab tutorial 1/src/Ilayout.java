import java.util.List;

/**
 * Interface Ilayout define as operações fundamentais para um layout/estado num problema de procura.
 * Um layout deve fornecer uma lista de estados filhos, verificar se é o estado objetivo e retornar o custo para se mover para um sucessor.
 * @author Andreia Qiu
 * @author Eduarda Pereira
 * @author Guilherme Carmo
 * @version 1; 22/09/2024
 */
interface Ilayout {

    /**
     * Retorna os sucessores (filhos) do layout atual.
     * @return Lista dos layouts sucessores.
     */
    List<Ilayout> children();

    /**
     * Verifica se o layout atual é igual ao layout objetivo fornecido.
     * @param l Layout a ser comparado.
     * @return true se o layout atual for igual ao argumento l; false caso contrário.
     */
    boolean isGoal(Ilayout l);

    /**
     * Retorna o custo da transição do layout atual para um layout sucessor.
     * @return Custo da transição para o sucessor.
     */
    double getK();
}
