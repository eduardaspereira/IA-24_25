import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Board que implementa um tabuleiro para um problema de layout (por exemplo, o jogo do 8-puzzle).
 * Contém métodos para manipular o tabuleiro, gerar sucessores e verificar se atingiu o estado objetivo.
 * @author Andreia Qiu
 * @author Eduarda Pereira
 * @author Guilherme Carmo
 * @version 1; 22/09/2024
 * @inv O tabuleiro deve conter sempre valores válidos e ser de dimensão 3x3, com um espaço vazio representado por 0.
 */
class Board implements Ilayout, Cloneable {
    private static final int dim = 3; // Dimensão do tabuleiro (3x3)
    private int board[][]; // Representação do tabuleiro

    /**
     * Construtor padrão que cria um tabuleiro vazio.
     */
    public Board() {
        board = new int[dim][dim];
    }

    /**
     * Construtor que inicializa o tabuleiro a partir de uma string representando o estado inicial.
     * @param str String que representa o estado inicial do tabuleiro
     * @throws IllegalStateException Se o tamanho da string for inválido
     */
    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim) throw new
                IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    /**
     * Retorna a representação textual do tabuleiro.
     * @return String representando o estado do tabuleiro
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    sb.append(" "); // Representa o espaço vazio com um espaço em branco
                } else {
                    sb.append(board[i][j]);
                }
            }
            sb.append("\n"); // Nova linha após cada linha do tabuleiro
        }
        return sb.toString();
    }

    /**
     * Verifica se o tabuleiro atual é igual a outro objeto.
     * @param o Objeto a ser comparado
     * @return true se os tabuleiros forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != board1.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retorna o código hash do tabuleiro.
     * @return Código hash do tabuleiro
     */
    @Override
    public int hashCode() {
        return Objects.hash((Object) board);
    }

    /**
     * Gera e retorna uma lista de layouts sucessores possíveis, fazendo movimentos válidos do espaço vazio.
     * @return Lista de sucessores do layout atual
     */
    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        int[] zeroPosition = findZero(); // Encontra a posição do espaço vazio
        int zeroRow = zeroPosition[0];
        int zeroCol = zeroPosition[1];

        // Movimentos possíveis: cima, baixo, esquerda, direita
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : moves) {
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];
            if (isValidMove(newRow, newCol)) {
                Board child = (Board) this.clone(); // Clona o tabuleiro atual
                child.swap(zeroRow, zeroCol, newRow, newCol); // Troca o espaço vazio com a nova posição
                children.add(child);
            }
        }
        return children;
    }

    /**
     * Retorna o custo de movimentação. Neste caso, um custo uniforme de 1 por movimento.
     * @return Custo de cada movimento
     */
    @Override
    public double getK() {
        return 1.0; // Custo uniforme por movimento
    }

    /**
     * Clona o tabuleiro atual.
     * @return Uma cópia do tabuleiro
     */
    @Override
    public Object clone() {
        Board clone = new Board();
        for (int i = 0; i < dim; i++)
            System.arraycopy(this.board[i], 0, clone.board[i], 0, dim);

        return clone;
    }

    /**
     * Troca dois elementos no tabuleiro.
     * @param row1 Linha do primeiro elemento
     * @param col1 Coluna do primeiro elemento
     * @param row2 Linha do segundo elemento
     * @param col2 Coluna do segundo elemento
     */
    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    /**
     * Verifica se o movimento é válido, isto é, se a nova posição está dentro dos limites do tabuleiro.
     * @param row Linha da nova posição
     * @param col Coluna da nova posição
     * @return true se o movimento for válido, false caso contrário
     */
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < dim && col >= 0 && col < dim;
    }

    /**
     * Encontra a posição do espaço vazio (representado por 0) no tabuleiro.
     * @return Um array contendo a linha e a coluna do espaço vazio
     * @throws IllegalStateException Se não houver espaço vazio no tabuleiro
     */
    private int[] findZero() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalStateException("No zero found on the board");
    }

    /**
     * Verifica se o tabuleiro atual atingiu o estado objetivo.
     * @param l Layout objetivo
     * @return true se o tabuleiro atual for igual ao objetivo, false caso contrário
     */
    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    /**
     * Retorna o valor da função g para o estado atual. Pode ser implementado conforme necessário.
     * @return Valor da função g
     */
    public double getG() {

        return 0;

    }
}
