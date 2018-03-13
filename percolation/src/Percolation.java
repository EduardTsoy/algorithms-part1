import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF fill;
    private final WeightedQuickUnionUF percol;
    private final boolean[] isOpen;
    private int numberOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        this.n = n;
        fill = new WeightedQuickUnionUF(n * n + 1);
        percol = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        for (int i = 1; i <= n; i++) {
            fill.union(i, virtualTop);
            percol.union(i, virtualTop);
            percol.union(virtualBottom - i, virtualBottom);
        }
        isOpen = new boolean[n * n + 1];
        numberOfOpenSites = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row,
                     int col) {
        int index = getIndex(row, col);
        if (!isOpen[index]) {
            numberOfOpenSites++;
            isOpen[index] = true;
            if (row > 1 && isOpen[index - n]) {
                fill.union(index, index - n);
                percol.union(index, index - n);
            }
            if (row < n && isOpen[index + n]) {
                fill.union(index, index + n);
                percol.union(index, index + n);
            }
            if (col > 1 && isOpen[index - 1]) {
                fill.union(index, index - 1);
                percol.union(index, index - 1);
            }
            if (col < n && isOpen[index + 1]) {
                fill.union(index, index + 1);
                percol.union(index, index + 1);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row,
                          int col) {
        int index = getIndex(row, col);
        return isOpen[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row,
                          int col) {
        int index = getIndex(row, col);
        return isOpen[index] && fill.connected(index, virtualTop);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percol.connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    /*
     * PRIVATE METHODS
     */

    private void valicateCoordinates(int row,
                                     int col) {
        validateCoordinate(row, "row");
        validateCoordinate(col, "col");
    }

    private void validateCoordinate(int row,
                                    String coord) {
        if (row <= 0) {
            throw new IllegalArgumentException(coord + " must be greater than 0.");
        }
        if (row > n) {
            throw new IllegalArgumentException(coord + " must not exceed " + n + ".");
        }
    }

    private int getIndex(int row,
                         int col) {
        valicateCoordinates(row, col);
        return (row - 1) * n + col;
    }

}
