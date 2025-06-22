/*import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ContainerTest {
    private Container container;

    @BeforeEach
    void setUp() {
        // Configuração inicial do contêiner, exemplo: "A1 B2 C3"
        container = new Container("A1 B2 C3");
    }

    @Test
    void testInicializacaoContainer() {
        // Verifica se o contêiner é inicializado corretamente
        assertNotNull(container);
        assertEquals(3, container.children().size());
    }

    @Test
    void testMovimentoDeContainer() {
        // Verifica se o contêiner pode ser movido corretamente
        List<Ilayout> children = container.children();
        assertFalse(children.isEmpty());

        for (Ilayout child : children) {
            assertNotEquals(container, child);
        }
    }

    @Test
    void testHeuristica() {
        // Verifica o valor heurístico para uma configuração objetivo
        Container goalContainer = new Container("A B C");
        double heuristicValue = container.heuristic(goalContainer);
        assertTrue(heuristicValue >= 0, "Heurística deve ser não-negativa");
    }

    @Test
    void testEqualsAndHashCode() {
        // Verifica se dois contêineres com a mesma configuração são considerados iguais
        Container container2 = new Container("A1 B2 C3");
        assertEquals(container, container2);
        assertEquals(container.hashCode(), container2.hashCode());
    }
}
*/