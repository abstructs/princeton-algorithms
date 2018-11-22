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

    private void printSizes() {
        for(int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                System.out.print(String.format("%d  ", sizes[i][j]));
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(4);

        perc.open(0, 1);

        perc.open(1, 1);

        perc.open(3, 2);

        perc.open(2, 2);

//        perc.open(1, 0);

        perc.open(0, 0);

//        perc.open(3, 3);
        perc.open(2, 3);
        perc.open(1, 3);

        perc.open(1, 2);

//        perc.open(3, 1);

//        perc.open(3, 0);
//
//        perc.open(3, 1);
//        perc.open(3, 2);

//        System.out.println(perc.size(2));

//        perc.open(0, 0);

//        perc.printSizes();
//        perc.print();
//        System.out.println(perc.percolates());
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

    private int index(int row, int col) {
        return elements.length * row + col;
    }

    private int deindexCol(int index) {
        return index % elements.length;
    }

    private int deindexRow(int index) {
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

    private int root(int row, int col) {
        int parentIndex = index(row, col);
        int i = row, j = col;

        while(parentIndex != elements[i][j]) {
            parentIndex = elements[i][j];
            i = deindexRow(parentIndex);
            j = deindexCol(parentIndex);
        }

        return parentIndex;
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

    private boolean inBounds(int row, int col) {
        return (row >= 0 && row < elements.length) && (col >= 0 && col < elements.length);
    }

    private boolean connected(int leftIndex, int rightIndex) {
        int leftRoot = root(leftIndex), rightRoot = root(rightIndex);
        return leftRoot == rightRoot;
    }

    private void union(int leftIndex, int rightIndex) {
        if(connected(leftIndex, rightIndex))
            return;

        int leftRoot = root(leftIndex), rightRoot = root(rightIndex);
        int leftRootSize = getSize(leftRoot), rightRootSize = getSize(rightRoot);

        if(leftRootSize >= rightRootSize) {
            setElement(rightRoot, leftRoot);
            setSize(leftRoot, 1 + rightRootSize);
        } else {
            setElement(leftRoot, rightRoot);
            setSize(rightRoot, 1 + leftRootSize);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if(isOpen(row, col))
            return;

        // grader starts arrays at 1
        row--;
        col--;
        
        elements[row][col] = index(row, col);
        sizes[row][col] = 1;

        int index = index(row, col);
        int leftIndex = index(row, col - 1), rightIndex = index(row, col + 1);
        int upIndex = index(row - 1, col), downIndex = index(row + 1, col);

        if(inBounds(row, col - 1) && isOpen(leftIndex))
            union(index, leftIndex);
        if(inBounds(row, col + 1) && isOpen(rightIndex))
            union(index, rightIndex);
        if(inBounds(row - 1, col) && isOpen(upIndex))
            union(index, upIndex);
        if(inBounds(row + 1, col) && isOpen(downIndex))
            union(index, downIndex);
    }

    // is site (row, col) open?
    // row and col starts at 1
    public boolean isOpen(int row, int col) {
        if(!inBounds(row - 1, col - 1))
            throw new ArrayIndexOutOfBoundsException();

        return elements[row - 1][col - 1] >= 0;
    }

    private boolean isOpen(int index) {
        int row = deindexRow(index), col = deindexCol(index);
        return elements[row][col] >= 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if(!inBounds(row - 1, col - 1))
            throw new ArrayIndexOutOfBoundsException();
        else if(!isOpen(row, col)) {
            return false;
        }

        int rootIndex = root(index(row - 1, col - 1));
        for(int j = 1; j <= elements.length; j++) {
            if(isOpen(1, j) && connected(rootIndex, root(0, j - 1))) {
                return true;
            }
        }

        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for(int i = 1; i <= elements.length; i++)
            for(int j = 1; j <= elements.length; j++)
                if(isOpen(i, j))
                    count++;

        return count;
    }
    // does the system percolate?
    public boolean percolates() {
        for(int i = 0; i < elements.length; i++) {
            for(int j = 0; j < elements.length; j++) {
                int topIndex = index(0, i), bottomIndex = index(elements.length - 1, j);

                if(isOpen(topIndex) && isOpen(bottomIndex) && connected(topIndex, bottomIndex))
                    return true;
            }
        }

        return false;
    }
}
