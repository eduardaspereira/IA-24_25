import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe PuzzleUnitTests contém testes unitários para verificar o funcionamento da classe Board.
 * Utiliza a biblioteca JUnit para validar os construtores e o método toString da classe Board.
 * @author Andreia Qiu
 * @author Eduarda Pereira
 * @author Guilherme Carmo
 * @version 1; 22/09/2024
 */
public class PuzzleUnitTests {

    /**
     * Testa o construtor da classe Board com uma string de entrada específica e verifica se a saída do método toString está correta.
     */
    @Test
    public void testConstructor() {
        // Cria um tabuleiro com a string "023145678" e verifica sua representação
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" 23");
        pw.println("145");
        pw.println("678");

        // Verifica se a representação do tabuleiro é igual à esperada
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    /**
     * Testa o construtor da classe Board com outra string de entrada e verifica a saída do método toString.
     */
    @Test
    public void testConstructor2() {
        // Cria um tabuleiro com a string "123485670" e verifica sua representação
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("123");
        pw.println("485");
        pw.println("67 ");

        // Verifica se a representação do tabuleiro é igual à esperada
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }
}
