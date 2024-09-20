import java.util.*;

class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getK();
            else g = 0.0;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }


    final private List<State> sucessores(State n) {
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


    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        fechados = new HashMap<> ();
        abertos.add(new State(s, null));

        while (!abertos.isEmpty()) {
            // Remove the state with the lowest cost from the open set
            actual = abertos.poll();

            // Check if this state is the goal state
            if (actual.layout.isGoal(objective)) {
                // Create a list to store the solution path
                List<State> solutionPath = new ArrayList<>();
                State current = actual;
                while (current != null) {
                    solutionPath.add(current);
                    current = current.father;
                }
                // Reverse the path and return an iterator
                Collections.reverse(solutionPath);
                return solutionPath.iterator();
            }

            // Add the current state to the closed set
            fechados.put(actual.layout, actual);

            // Generate the successors of the current state
            List<State> sucs = sucessores(actual);
            for (State succ : sucs) {
                // If the successor is not in the closed set, add it to the open set
                if (!fechados.containsKey(succ.layout)) {
                    abertos.add(succ);
                }
            }
        }

        // If no solution was found, return an empty iterator
        return Collections.emptyIterator();
    }

}

