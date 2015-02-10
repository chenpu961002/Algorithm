/**
 * Created by cxlyc007 on 2/5/15.
 */
public class Percolation {
    private int N;
    private boolean[] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufShadow;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        this.grid = new boolean[N * N + 1];
        this.uf = new WeightedQuickUnionUF(N * N + 1);
        this.ufShadow = new WeightedQuickUnionUF(N * N + 2);
    }

    public static void main(String[] args) {
    }

    public void open(int i, int j) {
        if (i <= 0 || i > this.N || j <= 0 || j > this.N) {
            throw new IndexOutOfBoundsException();
        }
        int idx = this.N * (i - 1) + j;
        if (this.grid[idx]) {
            return;
        }
        // process i, j
        this.grid[idx] = true;
        if (i == 1) {
            this.uf.union(0, idx);
            this.ufShadow.union(0, idx);
        }
        if (i == N) {
            this.ufShadow.union(idx, N * N + 1);
        }

        // process i-1,j
        if (isSatisfied(i - 1, j)) {
            this.uf.union(idx, this.N * (i - 2) + j);
            this.ufShadow.union(idx, this.N * (i - 2) + j);
        }

        // process i+1,j
        if (isSatisfied(i + 1, j)) {
            this.uf.union(idx, this.N * i + j);
            this.ufShadow.union(idx, this.N * i + j);
        }

        // process i,j-1
        if (isSatisfied(i, j - 1)) {
            this.uf.union(idx, this.N * (i - 1) + j - 1);
            this.ufShadow.union(idx, this.N * (i - 1) + j - 1);
        }

        // process i,j+1
        if (isSatisfied(i, j + 1)) {
            this.uf.union(idx, this.N * (i - 1) + j + 1);
            this.ufShadow.union(idx, this.N * (i - 1) + j + 1);
        }
        // System.out.println(percolates());
    }

    private boolean isSatisfied(int i, int j) {
        if (i <= 0 || i > this.N || j <= 0 || j > this.N) {
            return false;
        }
        if (!this.grid[this.N * (i - 1) + j]) {
            return false;
        }
        return true;
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > this.N || j <= 0 || j > this.N) {
            throw new IndexOutOfBoundsException();
        }
        return this.grid[this.N * (i - 1) + j];
    }

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > this.N || j <= 0 || j > this.N) {
            throw new IndexOutOfBoundsException();
        }
        return this.uf.connected(0, this.N * (i - 1) + j);
    }

    public boolean percolates() {
        return this.ufShadow.connected(0, this.N * this.N + 1);
    }
}
