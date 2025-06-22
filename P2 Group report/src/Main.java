import java.util.Iterator;
import java.util.Scanner;
/**
 * Classe principal que executa o algoritmo Best-First para encontrar a
 * configuração final de um contêiner a partir de uma configuração inicial.
 * Lê as configurações iniciais e finais do contêiner, executa o algoritmo de busca
 * e exibe a solução, caso encontrada.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 2024-10-28
 * @inv A classe mantém como invariante o uso de instâncias de Container para representar
 *      as configurações inicial e final de um contêiner, utilizando o algoritmo Best-First
 *      para calcular o caminho mínimo entre elas.
 */
public class Main {
    /**
     * Método principal que executa a leitura dos contêineres, inicializa o algoritmo Best-First
     * e imprime o resultado da solução, caso exista.
     *
     * @param args Argumentos passados via linha de comando (não utilizados neste programa).
     * @throws Exception Lança uma exceção caso ocorra erro na execução do algoritmo.
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        BestFirst s = new BestFirst();
        Container initialContainer = new Container(sc.nextLine());
        Container goalContainer = new Container(sc.nextLine());

        Iterator<BestFirst.State> it = s.solve(initialContainer, goalContainer);

        if (it == null) {
            System.out.println("no solution found");
        } else {
            BestFirst.State lastState = null;

            // Percorre até o último estado da solução
            while (it.hasNext()) {
                lastState = it.next();
            }

            // Exibe o estado final e adiciona uma linha em branco antes do custo total
            if (lastState != null) {
                System.out.println(lastState.getLayout().toString().trim());
                System.out.println(); // Linha em branco
                System.out.println((int) lastState.getG());
            }
        }

        sc.close();
    }
}
