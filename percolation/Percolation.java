import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean[] opened;
    private int size;

    public Percolation(int n) {
        size = n * n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
        opened = new boolean[n * n];

        createVirtualSites();
    }

    private void createVirtualSites() {
        for(int i = 1; i < Math.sqrt(size); i++)
            weightedQuickUnionUF.union(0, i);

        for(int i = 1; i < Math.sqrt(size); i++) {
            int cell = size - i - 1;
            weightedQuickUnionUF.union(size - 1, cell);
        }
    }

    private int index(int row, int col) {
        return row * ((int) Math.sqrt(size)) + col;
    }

    private boolean inBounds(int index) {
        return index >= 0 && index < size;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < Math.sqrt(size) && col >= 0 && col < Math.sqrt(size);
    }

    public void open(int row, int col) {
        if(isOpen(row, col))
            return;
        // grader starts arrays at 1
        row--;
        col--;

        int openIndex = index(row, col);

        opened[openIndex] = true;

        int leftIndex = index(row, col - 1), rightIndex = index(row, col + 1);
        int upIndex = index(row - 1, col), downIndex = index(row + 1, col);

        if(inBounds(row, col - 1) && isOpen(leftIndex))
            weightedQuickUnionUF.union(openIndex, leftIndex);
        if(inBounds(row, col + 1) && isOpen(rightIndex))
            weightedQuickUnionUF.union(openIndex, rightIndex);
        if(inBounds(row - 1, col) && isOpen(upIndex))
            weightedQuickUnionUF.union(openIndex, upIndex);
        if(inBounds(row + 1, col) && isOpen(downIndex))
            weightedQuickUnionUF.union(openIndex, downIndex);
    }

    public boolean isOpen(int row, int col) {
        // grader starts arrays at 1
        row--;
        col--;

        if(!inBounds(row, col))
            throw new IllegalArgumentException();

        return opened[index(row, col)];
    }

    private boolean isOpen(int index) {
        return opened[index];
    }

    public boolean isFull(int row, int col)  {
        // grader starts arrays at 1
        row--;
        col--;

        if(!inBounds(row, col))
            throw new IllegalArgumentException();

        int cellIndex = index(row, col);

        return isOpen(cellIndex) && weightedQuickUnionUF.connected(cellIndex, 0);
    }

    public int numberOfOpenSites() {
        int count = 0;

        for(int i = 0; i < size; i++)
            if(isOpen(i))
                count++;

        return count;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, size - 1);
    }
}
