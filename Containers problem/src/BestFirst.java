import java.util.*;

class BestFirst {
    protected Queue<State> abertos; // Fila de estados a serem processados
    private Map<Ilayout, State> fechados; // Conjunto de estados já processados
    private State actual; // Estado atual sendo processado
    private Ilayout objective; // Estado objetivo a ser alcançado

    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        /**
         * Construtor da classe State.
         *
         * @param l Layout do estado atual
         * @param n Estado pai (null se for o estado inicial)
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            g = (father != null) ? father.g + l.getK() : 0.0;
        }

        public String toString() {
            return layout.toString();
        }

        public double getK() {
            return g;
        }

        public int hashCode() {
            return layout.hashCode();
        }

        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    /**
     * Gera uma lista de sucessores para o estado atual, evitando retornar ao estado pai.
     *
     * @param n Estado atual
     * @return Lista de estados sucessores
     */
    private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        for (Ilayout e : n.layout.children()) {
            if (n.father == null || !e.equals(n.father.layout)) { // Evita adicionar o pai como sucessor
                sucs.add(new State(e, n)); // Cria um novo estado sucessor
            }
        }
        return sucs;
    }

    /**
     * Resolve o problema a partir de um estado inicial até um estado objetivo.
     *
     * @param s Estado inicial
     * @param goal Estado objetivo
     * @return Iterador do caminho solução ou null se não houver solução
     */
    public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(Comparator.comparingDouble(State::getK));
        fechados = new HashMap<>();

        // Adiciona o estado inicial aos abertos
        abertos.add(new State(s, null));

        while (!abertos.isEmpty()) {
            actual = abertos.poll();
            if (actual.layout.isGoal(objective)) {
                List<State> solutionPath = new ArrayList<>();
                while (actual != null) {
                    solutionPath.add(actual);
                    actual = actual.father;
                }
                Collections.reverse(solutionPath);
                return solutionPath.iterator();
            }

            // Adiciona o estado atual aos fechados
            fechados.put(actual.layout, actual);

            // Processa os sucessores
            for (State succ : sucessores(actual)) {
                if (!fechados.containsKey(succ.layout)) {
                    abertos.add(succ);
                    fechados.put(succ.layout, succ); // Cache o sucessor para evitar recomputação
                }
            }
        }
        return null;
    }
}