import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int theLast;
    private final WeightedQuickUnionUF uf;
    private final boolean[] isOpen;
    private int numberOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        theLast = n * n + 1;
        for (int i = 1; i <= n; i++) {
            uf.union(i, 0);
            uf.union(theLast - i, theLast);
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
                uf.union(index, index - n);
            }
            if (row < n && isOpen[index + n]) {
                uf.union(index, index + n);
            }
            if (col > 1 && isOpen[index - 1]) {
                uf.union(index, index - 1);
            }
            if (col < n && isOpen[index + 1]) {
                uf.union(index, index + 1);
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
        return isOpen[index] && uf.connected(index, 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, theLast);
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
