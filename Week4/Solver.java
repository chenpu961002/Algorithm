public class Solver {
    private MinPQ<GameTree> pq;
    private boolean isSolvable;
    private Stack<Board> solution;
    private int moves;

    private class GameTree implements Comparable<GameTree> {
        private Board current;
        private int move;
        private GameTree parent;
        public GameTree(Board current, int move, GameTree parent) {
            this.current = current;
            this.move = move;
            this.parent = parent;
        }
        private int getPriority() {
            return this.current.manhattan() + this.move;
        }

        public int compareTo(GameTree that) {
            int cmp = this.getPriority() - that.getPriority();
            // if (cmp == 0) {
            // cmp = this.getMove() - that.getMove();
            // }
            return cmp;
        }

        public int getMove() {
            return this.move;
        }
    }

    public Solver(Board initial) {
        if (null == initial) throw new java.lang.NullPointerException();
        this.isSolvable = isSolvable(initial);
        this.solution = null;
        this.moves = -1;
        // virtual node, first previous node, set steps a very large value
        // to ensure the virtualNode deleted first
        // first node, initialized by initial
        if (this.isSolvable) {
            int N = initial.dimension();
            GameTree virtualNode = new GameTree(new Board(new int[N][N]), -10000, null);
            GameTree firstNode = new GameTree(initial, 0, virtualNode);
            this.pq = new MinPQ<GameTree>();
            // this.pq.insert(virtualNode);
            this.pq.insert(firstNode);
            toSolve();
        }
    }

    private int[] parseString(String board) {
        String[] s = board.split("\\s");
        Queue<String> q = new Queue<String>();
        for (String ss : s) {
            String ssTrim = ss.trim();
            if (!ssTrim.isEmpty()) {
                q.enqueue(ssTrim);
            }
        }
        q.dequeue();

        int[] result = new int[q.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(q.dequeue());
        }
        return result;
    }

    private void exch(int[]board, int idx1, int idx2) {
        int tmp = board[idx1];
        board[idx1] = board[idx2];
        board[idx2] = tmp;
    }

    private boolean isSolvable(Board initial) {
        // cal the sign of the problem
        // look for the position (p,q) of 0
        // sign = (N-1 - p ) + (N-1 -q)
        int sign = -1;
        int[] board = parseString(initial.toString());

        int N = (int) Math.sqrt(board.length);
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                int row = i / N;
                int col = i % N;
                sign = ((N - 1 - row) + (N - 1 - col)) % 2;
            }
        }

        // cal real sign
        int signReal = 0;
        for (int i = 0; i < board.length; i++) {
            // wrong position
            if (board[i] != i + 1) {
                // find the position of i+1 behind
                for (int j = i + 1; j < board.length; j++) {
                    if (board[j] == i + 1) {
                        exch(board, i, j);
                        signReal = 1 - signReal;
                        break;
                    }
                }
            }
        }
        return sign == signReal;
    }

    private void toSolve() {
        // pop the virtural first
        // GameTree previous = this.pq.delMin();
        // System.out.println(previous.getPriority());
        while (true) {
            // for (GameTree t : this.pq) {
            // System.out.println(t.current.toString());
            // }
            GameTree currentNode = this.pq.delMin();
            // System.out.println("!" + currentNode.getMove() + ":"
            // + currentNode.current.manhattan() + ":"
            // + currentNode.getPriority());

            if (currentNode.current.isGoal()) {
                this.solution = new Stack<Board>();
                this.moves = currentNode.move;
                while (null != currentNode.parent) {
                    this.solution.push(currentNode.current);
                    currentNode = currentNode.parent;
                }
                break;
            }
            for (Board neighbor : currentNode.current.neighbors()) {
                if (neighbor.equals(currentNode.parent.current))
                    continue;
                int currentStep = currentNode.move;
                GameTree newNode = new GameTree(neighbor, currentStep + 1, currentNode);
                this.pq.insert(newNode);
            }
        }
    }

    public int moves() {
        return this.moves;
    }

    public boolean isSolvable() {
        return this.isSolvable;
    }

    public Iterable<Board> solution() {
        return this.solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
