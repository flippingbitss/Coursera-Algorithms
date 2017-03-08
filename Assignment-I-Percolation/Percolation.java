import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.time.temporal.Temporal;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] grid;
    private int n;

    public Percolation(int n) {
        this.n = n;
        if (n <= 0)
            throw new IllegalArgumentException("n must be a positive number");
        int total = n * n;
        uf = new WeightedQuickUnionUF(total + 2);
        grid = new boolean[total + 2]; // create n-by-n grid, with all sites blocked
        for (int i = 0; i < total; i++) {
            grid[i] = false;
        }
        grid[total] = grid[total + 1] = true;
        for (int i = 0; i < n; i++) {
            uf.union(total, i);
            uf.union(total + 1, n * (n - 1) + i);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new java.lang.IndexOutOfBoundsException("Index out of bounds");
        int currIdx = getIndex(row, col);

        if (grid[currIdx]) return;

        int total = n * n;
        int temp = 0;

        grid[currIdx] = true;
        for (int i = -1; i < 2; i += 2) {
            temp = currIdx - n * i;
            if (temp >= 0 && getX(temp) == getX(currIdx) && getY(temp) < n && getY(temp) >= 0)
                if (grid[temp])
                    uf.union(currIdx, temp);
            temp = currIdx - i;
            if (temp >= 0 && getY(temp) == getY(currIdx) && getX(temp) < n && getX(temp) >= 0)
                if (grid[temp])
                    uf.union(currIdx, temp);
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new java.lang.IndexOutOfBoundsException("Index out of bounds");
        return grid[getIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new java.lang.IndexOutOfBoundsException("Index out of bounds");
        return isOpen(row,col) && uf.connected(getIndex(row, col), n * n);
    }

    // number of open sitses
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < grid.length - 2; i++) {
            if (grid[i]) count++;
        }
        return count;
    }

    public boolean percolates() {
        return n==1 ? isOpen(1,1) : uf.connected(n * n, n * n + 1);
    } // does the system percolate?

    private int getIndex(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    private int getX(int idx) {
        return idx % n;
    }
    private int getY(int idx) {
        return idx / n;
    }


//    public static void main(String[] args) {
//        Percolation percolation = new Percolation(3);
//        System.out.println(percolation.isFull(1,1));
//    }

}
