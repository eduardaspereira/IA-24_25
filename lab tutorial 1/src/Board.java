import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() {
        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim) throw new
                IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    sb.append(" "); // Represent empty space with a blank
                } else {
                    sb.append(board[i][j]);
                }
            }
            sb.append("\n"); // New line after each row
        }
        return sb.toString();
    }


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


    @Override
    public int hashCode() {
        return Objects.hash((Object) board);
    }


    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        int[] zeroPosition = findZero(); // Find the position of the blank space
        int zeroRow = zeroPosition[0];
        int zeroCol = zeroPosition[1];

        // Possible moves: up, down, left, right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : moves) {
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];
            if (isValidMove(newRow, newCol)) {
                Board child = (Board) this.clone(); // Clone the current board
                child.swap(zeroRow, zeroCol, newRow, newCol); // Swap the blank with the new position
                children.add(child);
            }
        }
        return children;
    }

    // Implementation of the getK() method
    @Override
    public double getK() {
        return 1.0; // Simple uniform cost for each move
    }

    // Clone the current board
    @Override
    public Object clone() {
        Board clone = new Board();
        for (int i = 0; i < dim; i++)
            System.arraycopy(this.board[i], 0, clone.board[i], 0, dim);

        return clone;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < dim && col >= 0 && col < dim;
    }

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

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }


    public double getG() {
        // Implement the cost function, if needed
        return 0;
    }
}