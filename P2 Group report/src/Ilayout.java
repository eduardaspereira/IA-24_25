import java.util.List;

/**
 * Interface que define o layout e as operações essenciais para os estados no espaço de busca.
 * Esta interface é utilizada pelo algoritmo Best-First para explorar e comparar estados.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 2024-10-28
 * @inv Cada implementação de Ilayout deve garantir que o layout forneça uma maneira
 *      de calcular seus filhos, determinar se é objetivo, calcular o custo de transição (K)
 *      e uma heurística para a meta.
 */
interface Ilayout {

    /**
     * Gera e retorna uma lista de layouts filhos a partir do layout atual.
     *
     * @return Uma lista contendo todos os layouts filhos possíveis.
     */
    List<Ilayout> children();

    /**
     * Verifica se o layout atual corresponde ao layout objetivo fornecido.
     *
     * @param l O layout objetivo para verificar.
     * @return true se o layout atual for igual ao objetivo; caso contrário, false.
     */
    boolean isGoal(Ilayout l);

    /**
     * Retorna o custo de transição associado ao layout.
     *
     * @return O valor do custo de transição (K) para o layout atual.
     */
    double getK();

    /**
     * Calcula e retorna o valor heurístico do layout atual em relação a um layout objetivo.
     *
     * @param goal O layout objetivo para o cálculo da heurística.
     * @return O valor heurístico que estima a distância para o objetivo.
     */
    double heuristic(Ilayout goal);
}
