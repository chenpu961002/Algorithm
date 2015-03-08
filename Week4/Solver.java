public class Solver {
    private MinPQ<GameTree> pq;
    private MinPQ<GameTree> pqSwap;
    private Stack<Board> solution;
    private int moves;
    private boolean alreadyCal;
    private boolean result;

    private class GameTree implements Comparable<GameTree> {
        private Board current;
        private int step;
        private GameTree parent;
        public GameTree(Board current, int step, GameTree parent) {
            this.current = current;
            this.step = step;
            this.parent = parent;
        }
        public int compareTo(GameTree that) {
            int cmp = this.current.manhattan() + this.step
                      - (that.current.manhattan() + that.step);
            return cmp;
        }
    }

    public Solver(Board initial) {
        if (null == initial) throw new java.lang.NullPointerException();
        int N = initial.dimension();
        // virtual node, first previous node, set steps a very large value
        // to ensure the virtualNode deleted first
        GameTree virtualNode = new GameTree(new Board(new int[N][N]), -10000, null);
        // first node, initialized by initial
        GameTree firstNode = new GameTree(initial, 0, virtualNode);
        GameTree firstNodeSwap = new GameTree(initial.twin(), 0, virtualNode);
        this.pq = new MinPQ<GameTree>();
        this.pqSwap = new MinPQ<GameTree>();
        this.pq.insert(virtualNode);
        this.pqSwap.insert(virtualNode);
        this.pq.insert(firstNode);
        this.pqSwap.insert(firstNodeSwap);
        this.solution = null;
        this.moves = -1;

        // cal
        this.result = isSolvable();
        this.alreadyCal = true;
    }

    public boolean isSolvable() {
        if (this.alreadyCal)
            return this.result;
        else
            this.alreadyCal = true;

        try {
            this.result = isSolvable(false);
            return this.result;
        } catch (java.lang.OutOfMemoryError e1) {
            try {
                while (!this.pq.isEmpty())
                    this.pq.delMin();
                this.pq = null;
                isSolvable(true);
            } catch (java.lang.OutOfMemoryError e2) {
                e2.printStackTrace();
            }
            this.result = false;
            return this.result;
        }
    }

    private boolean isSolvable(boolean isSwap) {
        int step = 1;
        MinPQ<GameTree> realPQ = null;
        if (isSwap)
            realPQ = this.pqSwap;
        else
            realPQ = this.pq;

        // pop the virtural first
        GameTree previous = realPQ.delMin();
        while (true) {
            GameTree currentNode = realPQ.delMin();
            if (currentNode.current.isGoal()) {
                this.solution = new Stack<Board>();
                while (null != currentNode.parent) {
                    this.solution.push(currentNode.current);
                    this.moves++;
                    currentNode = currentNode.parent;
                }
                return true;
            }
            for (Board neighbor : currentNode.current.neighbors()) {
                if (neighbor.equals(previous.current))
                    continue;
                GameTree newNode = new GameTree(neighbor, step, currentNode);
                realPQ.insert(newNode);
            }
            step++;
        }
    }

    public int moves() {
        return this.moves;
    }

    public Iterable<Board> solution() {
        return solution;
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
