public class Board {
    private char[] board;
    private int N;
    private int hammingValue;
    private int manhattanValue;

    private Board(char[] blocks) {
        if (null == blocks) throw new java.lang.NullPointerException();
        this.N = (int) Math.sqrt(blocks.length);
        this.hammingValue = -1;
        this.manhattanValue = -1;
        this.board = new char[this.N * this.N];
        for (int i = 0; i < this.N * this.N; i++) {
            board[i] = blocks[i];
        }
    }

    public Board(int[][] blocks) {
        if (null == blocks) throw new java.lang.NullPointerException();
        this.N = blocks.length;
        this.hammingValue = -1;
        this.manhattanValue = -1;
        this.board = new char[this.N * this.N];
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                this.board[i * this.N + j] = convertToChar(blocks[i][j]);
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
        for (int i = 0; i < this.board.length; i++) {
            if (i == this.board.length - 1)
                continue;
            if (this.board[i] != convertToChar(i + 1))
                hammingValue++;
        }
        return hammingValue;
    }

    public int manhattan() {
        if (-1 != manhattanValue)
            return manhattanValue;

        manhattanValue = 0;
        for (int i = 0; i < this.board.length; i++) {
            int ele = convertToInt(this.board[i]);
            if (ele == 0)
                continue;
            int iRow = i / N;
            int iCol = i % N;
            int row = (ele - 1) / N;
            int col = (ele - 1) % N;
            manhattanValue += Math.abs(iRow - row) + Math.abs(iCol - col);
        }
        return manhattanValue;
    }

    public boolean isGoal() {
        return 0 == hamming();
    }

    public Board twin() {
        for (int i = 0; i < this.N * this.N; i += this.N) {
            if (this.board[i] != convertToChar(0) && this.board[i + 1] != convertToChar(0)) {
                exch(i, i + 1);
                Board t = new Board(this.board);
                exch(i, i + 1);
                return t;
            }
        }
        return null;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (null == y) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != N)
            return false;
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i] != that.board[i]) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        int originIdx = -1;
        for (int i = 0; i < this.board.length; i++) {
            if (board[i] == convertToChar(0)) {
                originIdx = i;
                break;
            }
        }

        // up
        if (originIdx - this.N >= 0) {
            exch(originIdx, originIdx - this.N);
            Board tmp = new Board(this.board);
            boards.enqueue(tmp);
            exch(originIdx, originIdx - this.N);
        }

        //down
        if (originIdx + this.N < this.board.length) {
            exch(originIdx, originIdx + this.N);
            Board tmp = new Board(this.board);
            boards.enqueue(tmp);
            exch(originIdx, originIdx + this.N);
        }

        //left
        if (originIdx % this.N > 0) {
            exch(originIdx, originIdx - 1);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originIdx, originIdx - 1);
        }

        //right
        if (originIdx % this.N < this.N - 1) {
            exch(originIdx, originIdx + 1);
            Board tmp = new Board(board);
            boards.enqueue(tmp);
            exch(originIdx, originIdx + 1);
        }
        return boards;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < this.board.length; i++) {
            s.append(String.format("%2d ", convertToInt(board[i])));
            if (i % this.N == this.N - 1)
                s.append("\n");
        }
        return s.toString();
    }

    private void exch(int idx1, int idx2) {
        char tmp = board[idx1];
        board[idx1] = board[idx2];
        board[idx2] = tmp;
    }

    private char convertToChar(int origin) {
        return (char) origin;
    }

    private int convertToInt(char orign) {
        return (int) orign;
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
