import java.util.*;

/**
 * Classe que implementa o algoritmo de busca Best-First para encontrar o caminho
 * mais curto entre um layout inicial e um layout objetivo.
 * Utiliza uma fila de prioridade para ordenar estados com base em um valor heurístico.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 2024-10-28
 * @inv A classe BestFirst mantém a invariante de que o layout objetivo deve ser
 *      definido para cada nova execução do método solve, sendo utilizado para
 *      calcular a heurística dos estados e determinar a solução.
 *
 */
class BestFirst {
    protected PriorityQueue<State> abertos;
    private HashSet<Ilayout> abertosSet;
    private Map<Ilayout, State> fechados;
    private State actual;
    private static Ilayout objective;

    /**
     * Classe interna que representa um estado no espaço de busca.
     */
    static class State {
        private Ilayout layout;
        private State father;
        private double g;
        /**
         * Retorna o layout associado ao estado.
         *
         * @return O layout do estado.
         */

        public Ilayout getLayout() {
            return this.layout;
        }

        /**
         * Construtor que cria um novo estado a partir de um layout e um estado pai.
         *
         * @param l O layout associado ao estado.
         * @param n O estado pai do estado atual.
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null) {
                g = father.g + l.getK();
            } else {
                g = 0.0;
            }
        }

        /**
         * Retorna o valor de f(n) do estado, que é a soma do custo g e da heurística h.
         *
         * @return O valor f(n) do estado.
         */
        public double getF() {
            return this.g + this.layout.heuristic(objective);
        }

        /**
         * Retorna a representação em string do estado.
         *
         * @return A string que representa o layout do estado.
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Retorna o custo acumulado até este estado.
         *
         * @return O custo g do estado.
         */
        public double getG() {
            return g;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Objects.equals(layout, state.layout);
        }

        @Override
        public int hashCode() {
            return layout.hashCode();
        }
    }

    /**
     * Gera os sucessores de um estado dado.
     * @param n O estado do qual os sucessores serão gerados.
     * @return Uma lista de estados sucessores.
     */
    private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    /**
     * Resolve o problema a partir de um layout inicial até um layout objetivo.
     * @param s O layout inicial.
     * @param goal O layout objetivo.
     * @return Um iterador sobre a sequência de estados que leva à solução.
     */
    public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;

        abertos = new PriorityQueue<>(10,
                (s1, s2) -> {
                    double f1 = s1.getF();
                    double f2 = s2.getF();

                    return (int) Math.signum(f1 - f2);
                });
        abertosSet = new HashSet<>();
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        abertosSet.add(s);

        while (!abertos.isEmpty()) {
            actual = abertos.poll();
            abertosSet.remove(actual.layout);

            if (actual.layout.isGoal(objective)) {
                List<State> solutionPath = new ArrayList<>();
                State current = actual;
                while (current != null) {
                    solutionPath.add(current);
                    current = current.father;
                }
                Collections.reverse(solutionPath);
                return solutionPath.iterator();
            }

            if (!fechados.containsKey(actual.layout)) {
                fechados.put(actual.layout, actual);
                List<State> sucs = sucessores(actual);
                for (State succ : sucs) {
                    if (!fechados.containsKey(succ.layout) && !abertosSet.contains(succ.layout)) {
                        abertosSet.add(succ.layout);
                        abertos.add(succ);
                    }
                }
            }
        }

        return null;
    }

}