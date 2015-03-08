public class Board {
    private int[][] board;
    private int N;
    private int hammingValue;
    private int manhattanValue;
    public Board(int[][] blocks) {
        if (null == blocks) throw new java.lang.NullPointerException();
        this.N = blocks.length;
        this.hammingValue = -1;
        this.manhattanValue = -1;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        if (-1 != hammingValue)
            return hammingValue;
        hammingValue = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (N - 1 == i && N - 1 == j)
                    continue;
                if (N * i + j + 1 != board[i][j])
                    hammingValue++;
            }
        }
        return hammingValue;
    }

    public int manhattan() {
        if (-1 != manhattanValue)
            return manhattanValue;
        manhattanValue = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int ele = board[i][j];
                if (0 == ele)
                    continue;
                int row = (ele - 1) / N;
                int col = (ele - 1) % N;
                // System.out.println(ele + " " + i + " " + j + " " + row + " " + col);
                manhattanValue += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return manhattanValue;
    }

    public boolean isGoal() {
        return 0 == hamming();
    }

    public Board twin() {
        int row1 = -1, col1 = -1;
        int row2 = -1, col2 = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    row1 = i;
                    col1 = j;
                    row2 = i;
                    col2 = j + 1;
                }
            }
        }
        exch(row1, col1, row2, col2);
        Board t = new Board(board);
        exch(row1, col1, row2, col2);
        return t;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (null == y) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != N)
            return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != that.board[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        int originRow = -1;
        int originCol = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (0 == board[i][j]) {
                    originRow = i;
                    originCol = j;
                    break;
                }
            }
        }

        // up
        if (originRow > 0) {
            exch(originRow, originCol, originRow - 1, originCol);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originRow, originCol, originRow - 1, originCol);
        }

        //down
        if (originRow < N - 1) {
            exch(originRow, originCol, originRow + 1, originCol);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originRow, originCol, originRow + 1, originCol);
        }

        //left
        if (originCol > 0) {
            exch(originRow, originCol, originRow, originCol - 1);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originRow, originCol, originRow, originCol - 1);
        }

        //right
        if (originCol < N - 1) {
            exch(originRow, originCol, originRow, originCol + 1);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originRow, originCol, originRow, originCol + 1);
        }
        return boards;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private void exch(int row1, int col1, int row2, int col2) {
        int tmp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = tmp;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial.toString());

        System.out.println(initial.twin().toString());
        System.out.println("--------");
        for (Board b : initial.neighbors()) {
            System.out.println(b.toString());
        }
        System.out.println("hamming" + initial.hamming());
        System.out.println("manhattan" + initial.manhattan());
    }

}
