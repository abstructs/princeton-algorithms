import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean[] opened;
    private int size;

    public Percolation(int n) {
        size = n * n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
        opened = new boolean[n * n];
    }

    private int index(int row, int col) {
        return row * ((int) Math.sqrt(size)) + col;
    }

    private boolean inBounds(int index) {
        return index >= 0 && index < size;
    }

    public void open(int row, int col) {
        if(isOpen(row, col))
            return;
        // grader starts arrays at 1
        row--;
        col--;

        opened[index(row, col)] = true;

        int index = index(row, col);
        int leftIndex = index(row, col - 1), rightIndex = index(row, col + 1);
        int upIndex = index(row - 1, col), downIndex = index(row + 1, col);

        if(inBounds(leftIndex) && isOpen(leftIndex))
            weightedQuickUnionUF.union(index, leftIndex);
        if(inBounds(rightIndex) && isOpen(rightIndex))
            weightedQuickUnionUF.union(index, rightIndex);
        if(inBounds(upIndex) && isOpen(upIndex))
            weightedQuickUnionUF.union(index, upIndex);
        if(inBounds(downIndex) && isOpen(downIndex))
            weightedQuickUnionUF.union(index, downIndex);
    }

    public boolean isOpen(int row, int col) {
        // grader starts arrays at 1
        row--;
        col--;

        return opened[index(row, col)];
    }

    private boolean isOpen(int index) {
        return opened[index];
    }

    public boolean isFull(int row, int col) {
        // grader starts arrays at 1
        row--;
        col--;

        int cellIndex = index(row, col);

        if(!inBounds(cellIndex) || !isOpen(cellIndex))
            return false;

        for(int topIndex = 0; topIndex < (int) Math.sqrt(size); topIndex++) {
            if(weightedQuickUnionUF.connected(topIndex, cellIndex))
                return true;
        }

        return false;
    }

    public int numberOfOpenSites() {
        int count = 0;

        for(int i = 0; i < size; i++)
            if(isOpen(i))
                count++;

        return count;
    }

    public boolean percolates() {
        for(int i = 0; i < Math.sqrt(size); i++) {
            for(int j = 0; j < Math.sqrt(size); j++) {
                int topIndex = index(0, i), bottomIndex = index(((int) Math.sqrt(size)) - 1, j);

                if(isOpen(topIndex) && isOpen(bottomIndex) && weightedQuickUnionUF.connected(topIndex, bottomIndex))
                    return true;
            }
        }

        return false;
    }
}
