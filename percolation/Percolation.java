public class Percolation {
    // test client (optional)
    private void print() {
        for(int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements.length; j++) {
                System.out.print(String.format("%d  ", elements[i][j]));
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(2);

        perc.open(0, 1);

        perc.open(1, 1);

        perc.open(1, 0);

        perc.open(0, 0);


        perc.print();
    }

    private int[][] elements;
    private int[][] sizes;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.elements = new int[n][n];
        this.sizes = new int[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                elements[i][j] = -1;
                sizes[i][j] = 0;
            }

    }

    public int index(int row, int col) {
        return elements.length * row + col;
    }

    public int deindexCol(int index) {
        return index % elements.length;
    }

    public int deindexRow(int index) {
        return (index - deindexCol(index)) / elements.length;
    }

    private int root(int index) {
        int parentIndex = index;
        int i = deindexRow(index), j = deindexCol(index);

        while(parentIndex != elements[i][j]) {
            parentIndex = elements[i][j];
            i = deindexRow(parentIndex);
            j = deindexCol(parentIndex);
        }

        return parentIndex;
    }

//    private int size(int row, int col) {
//        int rootIndex = root(row, col);
//        int rootRow = deindexRow(rootIndex), rootCol = deindexCol(rootIndex);
//
//        return sizes[rootRow][rootCol];
//    }

    private int size(int index) {
        int row = deindexRow(index), col = deindexCol(index);

        return sizes[row][col];
    }

    private void setElement(int index, int ele) {
        int col = deindexCol(index), row = deindexRow(index);
        elements[row][col] = ele;
    }

    private void setSize(int index, int size) {
        int col = deindexCol(index), row = deindexRow(index);
        sizes[row][col] = size;
    }

    private int getSize(int index) {
        int col = deindexCol(index), row = deindexRow(index);
        return sizes[row][col];
    }

    private int getElement(int index) {
        int col = deindexCol(index), row = deindexRow(index);
        return elements[row][col];
    }

    private boolean inBounds(int index) {
        int col = deindexCol(index), row = deindexRow(index);
        return (row >= 0 && row < elements.length) && (col >= 0 && col < elements.length);
    }

    private void union(int leftIndex, int rightIndex) {
        int leftRoot = root(leftIndex), rightRoot = root(rightIndex);
        int leftRootSize = size(leftRoot), rightRootSize = size(rightRoot);

        if(leftRootSize < rightRootSize) {
            setElement(rightRoot, leftRoot);
            setSize(leftRoot, leftRootSize + rightRootSize);
        } else {
            setElement(leftRoot, rightRoot);
            setSize(rightRoot, rightRootSize + leftRootSize);
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        elements[row][col] = index(row, col);
        sizes[row][col] = 1;

        int index = index(row, col);
        int leftIndex = index(row, col - 1), rightIndex = index(row, col + 1);
        int upIndex = index(row - 1, col), downIndex = index(row + 1, col);

        // check boundaries
//        if(isOpen())
        if(inBounds(leftIndex) && isOpen(leftIndex))
            union(index, leftIndex);
        if(inBounds(rightIndex) && isOpen(rightIndex))
            union(index, rightIndex);
        if(inBounds(upIndex) && isOpen(upIndex))
            union(index, upIndex);
        if(inBounds(downIndex) && isOpen(downIndex))
            union(index, downIndex);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return elements[row][col] >= 0;
    }

    private boolean isOpen(int index) {
        int row = deindexRow(index), col = deindexCol(index);
        return elements[row][col] >= 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
//        return elements[row][col] >= 0;
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for(int i = 0; i < elements.length; i++)
            for(int j = 0; j < elements.length; j++)
                if(isOpen(i, j))
                    count++;

        return count;
    }
    // does the system percolate?
    public boolean percolates() {

        return false;
    }
}
