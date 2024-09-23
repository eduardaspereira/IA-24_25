import java.util.Iterator;
import java.util.Scanner;

/**
 * Classe Main que executa a resolução do problema de layout utilizando o algoritmo Best-First.
 * O programa lê o estado inicial e o estado objetivo do tabuleiro e tenta encontrar a solução.
 * Se houver uma solução, imprime o caminho até o objetivo e o custo total.
 * @author Andreia Qiu
 * @author Eduarda Pereira
 * @author Guilherme Carmo
 * @version 1; 22/09/2024
 */
public class Main {

    /**
     * Método principal que inicia a execução do programa.
     * Lê o estado inicial e o estado objetivo do tabuleiro a partir da entrada, resolve o problema e imprime o resultado.
     * @param args Argumentos da linha de comando
     * @throws Exception Se ocorrer algum erro durante a execução
     */
    public static void main (String [] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // Cria uma instância do algoritmo Best-First
        BestFirst s = new BestFirst();

        // Lê o estado inicial e o estado objetivo a partir da entrada e resolve o problema
        Iterator<BestFirst.State> it = s.solve(new Board(sc.next()), new Board(sc.next()));

        // Verifica se há solução
        if (it == null) {
            System.out.println("no solution found");
        } else {
            // Imprime os estados da solução e o custo total
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                System.out.println(i);
                if (!it.hasNext()) {
                    // Imprime o custo total da solução
                    System.out.println((int) i.getG());
                }
            }
        }

        // Fecha o scanner
        sc.close();
    }
}
