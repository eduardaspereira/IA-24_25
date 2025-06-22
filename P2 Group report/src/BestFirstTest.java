/*import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class BestFirstTest {
    private BestFirst bestFirst;
    private Container initialContainer;
    private Container goalContainer;

    @BeforeEach
    void setUp() {
        bestFirst = new BestFirst();
        initialContainer = new Container("A1 B2 C3");
        goalContainer = new Container("A B C");
    }

    @Test
    void testBuscaSolucao() {
        // Verifica se o algoritmo encontra uma solução
        Iterator<BestFirst.State> solution = bestFirst.solve(initialContainer, goalContainer);
        assertNotNull(solution, "A solução não deve ser nula");

        // Confere se o estado final é igual ao objetivo
        BestFirst.State lastState = null;
        while (solution.hasNext()) {
            lastState = solution.next();
        }
        assertNotNull(lastState);
        assertEquals(goalContainer, lastState.getLayout());
    }

    @Test
    void testCalculoDeCusto() {
        // Testa se o custo acumulado (g) e o valor F = g + h estão corretos
        BestFirst.State initialState = new BestFirst.State(initialContainer, null);
        assertEquals(0, initialState.getG(), "Custo inicial deve ser zero");

        BestFirst.State nextState = new BestFirst.State(goalContainer, initialState);
        assertTrue(nextState.getF() > 0, "O valor F deve ser positivo");
    }

    @Test
    void testEvitarCiclos() {
        // Verifica se o algoritmo evita ciclos e movimentos redundantes
        Iterator<BestFirst.State> solution = bestFirst.solve(initialContainer, goalContainer);
        Set<Ilayout> visitedLayouts = new HashSet<>();

        while (solution != null && solution.hasNext()) {
            BestFirst.State state = solution.next();
            assertFalse(visitedLayouts.contains(state.getLayout()), "O layout não deve ser revisitado");
            visitedLayouts.add(state.getLayout());
        }
    }
}
*/
