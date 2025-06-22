import java.util.*;

/**
 * Classe que representa um contêiner que armazena pilhas de caracteres e suas informações.
 * Implementa a interface Ilayout e permite a clonagem dos objetos.
 * É usada para manipular estados de configuração e calcular heurísticas e custos de transição.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 2024-10-28
 * @inv O estado do contêiner é mantido em uma lista de pilhas, onde cada pilha representa
 *      uma pilha de caracteres, e a informação de cada contêiner é armazenada em um HashMap
 *      para associar caracteres ao seu custo.
 */
public class Container implements Ilayout, Cloneable {

    private List<Stack<Character>> containers;
    private HashMap<Character, Integer> containerInfo;
    private int cost;

    /**
     * Construtor que cria um contêiner a partir de uma configuração dada.
     *
     * @param config A configuração inicial dos contêineres em formato de string.
     * @throws IllegalStateException Se a configuração não for válida.
     */
    public Container(String config) throws IllegalStateException {
        containers = new ArrayList<>();
        containerInfo = new HashMap<>();
        this.cost = 0;

        String[] str = config.split(" ");
        for (String s : str) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isLetter(c) && !Character.isDigit(c)) {
                    throw new IllegalArgumentException("Entrada inválida: " + c);
                }

                if (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    containerInfo.put(c, Character.getNumericValue(s.charAt(i + 1)));
                    i++;
                } else {
                    containerInfo.put(c, 1);
                }
                stack.push(c);
            }
            containers.add(stack);
        }
    }

    /**
     * Construtor que cria um contêiner a partir de uma lista de pilhas e informações do contêiner.
     *
     * @param containers A lista de pilhas que representam os contêineres.
     * @param containerInfo Um mapa que contém a informação dos contêineres.
     */
    public Container(List<Stack<Character>> containers, HashMap<Character, Integer> containerInfo) {
        this.containers = containers;
        this.containerInfo = new HashMap<>(containerInfo);
        this.cost = 0;
    }

    /**
     * Gera os estados filhos do contêiner atual.
     *
     * @return Uma lista de filhos Ilayout representando as novas configurações.
     */
    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();

        for (int i = 0; i < containers.size(); i++) {
            Stack<Character> start = containers.get(i);

            if (!start.isEmpty()) {
                char c = start.peek();
                int containerCost = containerInfo.get(c);

                List<Stack<Character>> copy = deepCopy(i, -1);
                copy.get(i).pop();
                if (copy.get(i).isEmpty()) {
                    copy.remove(i);
                }

                Stack<Character> groundStack = new Stack<>();
                groundStack.push(c);
                copy.add(groundStack);

                Container display = new Container(copy, containerInfo);
                display.cost = containerCost;
                children.add(display);

                for (int j = 0; j < containers.size(); j++) {
                    if (i != j) {
                        List<Stack<Character>> copy2 = deepCopy(i, j);

                        if (!copy2.get(i).isEmpty()) {
                            copy2.get(i).pop();
                            copy2.get(j).push(c);
                        }

                        if (copy2.get(i).isEmpty()) {
                            copy2.remove(i);
                        }

                        Container display2 = new Container(copy2, containerInfo);
                        display2.cost = containerCost;
                        children.add(display2);
                    }
                }
            }
        }
        return children;
    }

    /**
     * Cria uma cópia profunda dos contêineres a partir de índices fornecidos.
     *
     * @param sourceIndex O índice da pilha de origem.
     * @param destIndex   O índice da pilha de destino.
     * @return Uma lista de pilhas clonadas.
     */
    @SuppressWarnings("unchecked")
    public List<Stack<Character>> deepCopy(int sourceIndex, int destIndex) {
        List<Stack<Character>> stacks = new ArrayList<>(containers);

        if (sourceIndex >= 0) {
            stacks.set(sourceIndex, (Stack<Character>) stacks.get(sourceIndex).clone());
        }
        if (destIndex >= 0) {
            stacks.set(destIndex, (Stack<Character>) stacks.get(destIndex).clone());
        }
        return stacks;
    }

    /**
     * Ordena os contêineres com base no elemento inferior das pilhas.
     */
    public void sortContainers() {
        containers.sort((s1, s2) -> {
            char stackBottom1 = s1.firstElement();
            char stackBottom2 = s2.firstElement();
            return Character.compare(stackBottom1, stackBottom2);
        });
    }

    @Override
    public Container clone() {
        return new Container(containers, containerInfo);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;

        Container display = (Container) o;
        if (this.containers.size() != display.containers.size()) return false;

        if (this.hashCode() == display.hashCode()) return true;

        return Objects.equals(containers, display.containers);
    }

    /**
     * Retorna uma representação em string do contêiner, incluindo os elementos das pilhas.
     *
     * @return A string que representa os contêineres.
     */
    @Override
    public String toString() {
        sortContainers();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < containers.size(); i++) {
            Stack<Character> container = containers.get(i);
            if (!container.isEmpty()) {
                sb.append("[");
                for (int j = 0; j < container.size(); j++) {
                    sb.append(container.get(j));
                    if (j < container.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                if (i < containers.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString().trim();
    }

    /**
     * Verifica se o layout atual é igual ao layout objetivo.
     *
     * @param l O layout objetivo.
     * @return true se o layout atual for o objetivo; false caso contrário.
     */

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    /**
     * Retorna o custo associado ao layout atual.
     *
     * @return O custo de transição do contêiner.
     */
    @Override
    public double getK() {
        return this.cost;
    }


    /**
     * Calcula e retorna o valor heurístico em relação ao layout objetivo.
     *
     * @param goal O layout objetivo.
     * @return O valor heurístico para o layout atual em relação ao objetivo.
     */
    @Override
    public double heuristic(Ilayout goal) {
        Container goalContainer = (Container) goal;
        double heuristicCost = 0;

        for (Stack<Character> currentStack : containers) {
            if (currentStack.isEmpty()) continue;

            char baseCurrent = currentStack.firstElement();
            boolean baseFoundInGoal = false;

            for (Stack<Character> goalStack : goalContainer.containers) {
                if (goalStack.isEmpty()) continue;

                char baseGoal = goalStack.firstElement();

                // Se encontramos uma pilha no goal com a mesma base
                if (baseCurrent == baseGoal) {
                    baseFoundInGoal = true;

                    // Comparar contentores posição a posição
                    for (int i = 0; i < currentStack.size(); i++) {
                        char currentChar = currentStack.get(i);

                        // Se não há mais elementos no stack goal para comparar ou os contentores diferem
                        if (i >= goalStack.size() || currentChar != goalStack.get(i)) {
                            // Somar o custo do contentor fora de posição
                            heuristicCost += containerInfo.get(currentChar);

                            // Somar o custo de cada contentor que bloqueia este
                            for (int j = i + 1; j < currentStack.size(); j++) {
                                heuristicCost += containerInfo.get(currentStack.get(j));
                            }
                            break;  // sair após encontrar o primeiro desalinhamento
                        }
                    }
                    break;  // sair após encontrar a base correspondente
                }
            }

            // Se a base não foi encontrada em nenhum stack do goal, somar custos de todos os contentores no stack
            if (!baseFoundInGoal) {
                for (char c : currentStack) {
                    heuristicCost += containerInfo.get(c);
                }
            }
        }

        return heuristicCost;
    }


}