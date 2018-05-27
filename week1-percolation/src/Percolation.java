import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUAL_TOP = 0;

    private final int n;
    private final WeightedQuickUnionUF fill;
    private final boolean[] isOpen;
    private int numberOfOpenSites;

    // Index of this array is a component index, not a site index.
    // Will fail if WeightedQuickUnionUF implementation uses component indexes not within site index range.
    private final boolean[] componentConnectedToBottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        this.n = n;
        fill = new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[n * n + 1];
        numberOfOpenSites = 0;
        componentConnectedToBottom = new boolean[n * n + 1];
        for (int i = 1; i <= n; i++) {
            fill.union(i, VIRTUAL_TOP);
            componentConnectedToBottom[getIndex(n, i)] = true;
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row,
                     int col) {
        int index = getIndex(row, col);
        if (!isOpen[index]) {
            numberOfOpenSites++;
            isOpen[index] = true;
            if (row > 1 && isOpen[index - n]) {
                makeUnionAndMarkConnectedToBottom(index, index - n);
            }
            if (row < n && isOpen[index + n]) {
                makeUnionAndMarkConnectedToBottom(index, index + n);
            }
            if (col > 1 && isOpen[index - 1]) {
                makeUnionAndMarkConnectedToBottom(index, index - 1);
            }
            if (col < n && isOpen[index + 1]) {
                makeUnionAndMarkConnectedToBottom(index, index + 1);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row,
                          int col) {
        return isOpen[getIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row,
                          int col) {
        int index = getIndex(row, col);
        return isOpen[index] && fill.connected(index, VIRTUAL_TOP);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return numberOfOpenSites > 0 && componentConnectedToBottom[fill.find(VIRTUAL_TOP)];
    }

    // test client (optional)
    public static void main(String[] args) {
        // do nothing
    }

    /*
     * PRIVATE METHODS
     */

    private void makeUnionAndMarkConnectedToBottom(int site,
                                                   int neighbor) {
        int siteComponent = fill.find(site);
        int neighborComponent = fill.find(neighbor);
        boolean bottomConnected = componentConnectedToBottom[siteComponent] || componentConnectedToBottom[neighborComponent];
        componentConnectedToBottom[siteComponent] = bottomConnected;
        componentConnectedToBottom[neighborComponent] = bottomConnected;

        fill.union(site, neighbor);
    }

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
