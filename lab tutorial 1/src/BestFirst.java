import java.util.*;
/**
 * Classe BestFirst que implementa o algoritmo de procura Best-First para encontrar um caminho do estado inicial ao objetivo.
 * Utiliza uma fila de prioridade (PriorityQueue) baseada no custo g, calculado pela função getK do layout.
 * @author Andreia Qiu
 * @author Eduarda Pereira
 * @author Guilherme Carmo
 * @version 1; 22/09/2024
 * @inv a fila de abertos nunca contém estados repetidos, e o conjunto fechados guarda estados já processados.
 */
class BestFirst {
    protected Queue<State> abertos; // Fila de estados a serem processados
    private Map<Ilayout, State> fechados; // Conjunto de estados já processados
    private State actual; // Estado atual sendo processado
    private Ilayout objective; // Estado objetivo a ser alcançado

    /**
     * Classe interna State representa um estado da procura, contendo um layout e referências ao pai.
     */
    static class State {
        private Ilayout layout; // Layout do estado
        private State father; // Estado pai do estado atual
        private double g; // Custo g (distância do estado inicial ao estado atual)

        /**
         * Construtor da classe State.
         * @param l Layout do estado atual
         * @param n Estado pai (null se for o estado inicial)
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getK(); // Calcula o custo g somando o custo do pai com o custo do layout atual
            else g = 0.0; // Se não houver pai, custo g é zero
        }

        /**
         * Retorna a representação textual do layout.
         * @return String representando o layout do estado
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Retorna o valor do custo g.
         * @return Custo g (distância acumulada do estado inicial)
         */
        public double getG() {
            return g;
        }

        /**
         * Retorna o código hash do estado baseado em sua representação textual.
         * @return Código hash do estado
         */
        public int hashCode() {
            return toString().hashCode();
        }

        /**
         * Verifica se dois estados são iguais com base no layout.
         * @param o Objeto a ser comparado
         * @return true se os layouts forem iguais, false caso contrário
         */
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    /**
     * Gera os sucessores de um determinado estado.
     * @param n Estado atual para o qual os sucessores serão gerados
     * @return Lista de estados sucessores
     */
    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) { // Evita adicionar o pai como sucessor
                State nn = new State(e, n); // Cria um novo estado sucessor
                sucs.add(nn);
            }
        }
        return sucs;
    }

    /**
     * Resolve o problema de procura encontrando um caminho do estado inicial ao objetivo.
     * Utiliza uma fila de prioridade para expandir estados com menor custo g.
     * @param s Estado inicial do problema
     * @param goal Estado objetivo a ser alcançado
     * @return Iterador para a sequência de estados que compõem o caminho da solução, ou um iterador vazio se não houver solução
     * @see https://en.wikipedia.org/wiki/Best-first_search
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG())); // Compara os estados com base no custo g
        fechados = new HashMap<>();
        abertos.add(new State(s, null)); // Adiciona o estado inicial na fila de abertos

        while (!abertos.isEmpty()) {
            // Remove o estado com o menor custo da fila de abertos
            actual = abertos.poll();

            // Verifica se este estado é o objetivo
            if (actual.layout.isGoal(objective)) {
                // Cria uma lista para armazenar o caminho da solução
                List<State> solutionPath = new ArrayList<>();
                State current = actual;
                while (current != null) {
                    solutionPath.add(current); // Adiciona o estado atual ao caminho
                    current = current.father; // Retrocede até o estado inicial
                }
                // Inverte o caminho e retorna um iterador
                Collections.reverse(solutionPath);
                return solutionPath.iterator();
            }

            // Adiciona o estado atual ao conjunto de fechados
            fechados.put(actual.layout, actual);

            // Gera os sucessores do estado atual
            List<State> sucs = sucessores(actual);
            for (State succ : sucs) {
                // Se o sucessor não estiver no conjunto de fechados, adiciona-o na fila de abertos
                if (!fechados.containsKey(succ.layout)) {
                    abertos.add(succ);
                }
            }
        }

        // Se nenhuma solução for encontrada, retorna um iterador vazio
        return Collections.emptyIterator();
    }
}
