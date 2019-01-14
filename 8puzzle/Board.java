/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board implements Comparable<Board> {
    // unit tests (not graded)
    public static void main(String[] args) {
        int boardSize = 3;

        int [][] board = new int[boardSize][boardSize];

        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int position = i * boardSize + j;
                board[i][j] = position + 1;
//                System.out.println(i * boardSize + j + 1);5
            }
        }

        board[boardSize - 1][boardSize - 1] = 0;

        Board b = new Board(board);

        System.out.println(b.isGoal());
    }

    private int[][] blocks;
    private int manhattan;
    private final int BLANK_VAL = 0;
    private int BLANK_ROW;
    private int BLANK_COL;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];

        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                int blockValue = blocks[i][j];

                this.blocks[i][j] = blockValue;

                if(blockValue == this.BLANK_VAL) {
                    this.BLANK_ROW = i;
                    this.BLANK_COL = j;
                }
            }
        }

        manhattan = manhattan();
    }

    // board dimension n

    public int dimension() {
        return 2;
    }

    // number of blocks out of place
    public int hamming() {
        int blocksOutOfPlace = 0;

        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                int expectedValue = expectedValue(i, j);
                int actualValue = blocks[i][j];

                if(actualValue == BLANK_VAL) continue;

                if(actualValue != expectedValue(i, j)) {
                    blocksOutOfPlace++;
                }
            }
        }

        return blocksOutOfPlace;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int totalDistance = 0;

        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                int expectedValue = expectedValue(i, j);
                int actualValue = blocks[i][j];

                if(actualValue == BLANK_VAL) continue;

                totalDistance += Math.abs(expectedValue - actualValue);
            }
        }

        return totalDistance;
    }

    private int expectedValue(int i, int j) {
        if(i == blocks.length - 1 && j == blocks.length - 1) {
            return 0;
        } else {
            int position = i * blocks.length + j;
            return position + 1;
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                int position = blocks[i][j];

                if(position != expectedValue(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int swap1 = blocks[row1][col1];
        int swap2 = blocks[row2][col2];

        blocks[row1][col1] = swap2;
        blocks[row2][col2] = swap1;

        if(swap1 == BLANK_VAL) {
            BLANK_ROW = row2;
            BLANK_COL = col2;
        } else if(swap2 == BLANK_VAL) {
            BLANK_ROW = row1;
            BLANK_COL = col1;
        }
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board board = new Board(blocks);

        int row1 = 0;
        int col1 = BLANK_COL == 0 ? 1 : 0;

        int row2 = 0;
        int col2 = BLANK_COL == 0 ? 2 : 1;
        
        board.swap(row1, col1, row2, col2);

        return board;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if(y instanceof Board) {
            Board yBoard = (Board) y;

            if(blocks.length != yBoard.blocks.length) return false;

            int[][] yBlocks = yBoard.blocks;

            for(int i = 0; i < blocks.length; i++) {
                for(int j = 0; j < blocks.length; j++) {
                    if(blocks[i][j] != yBlocks[i][j]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private Board swapWithBlank(int row, int col) {
        if(row < 0 || row >= blocks.length || col < 0 || col >= blocks.length) {
            return null;
        }

        Board board = new Board(blocks);

        board.swap(row, col, board.BLANK_ROW, board.BLANK_COL);

        return board;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Board leftBoard = swapWithBlank(BLANK_ROW, BLANK_COL - 1);
        Board rightBoard = swapWithBlank(BLANK_ROW, BLANK_COL + 1);
        Board topBoard = swapWithBlank(BLANK_ROW - 1, BLANK_COL);
        Board bottomBoard = swapWithBlank(BLANK_ROW + 1, BLANK_COL);

        ArrayList<Board> boards = new ArrayList<>();

        if(leftBoard != null) boards.add(leftBoard);
        if(rightBoard != null) boards.add(rightBoard);
        if(topBoard != null) boards.add(topBoard);
        if(bottomBoard != null) boards.add(bottomBoard);

        return boards;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < blocks.length; i++) {
            builder.append("\n");
            for(int j = 0; j < blocks.length; j++) {
                builder.append(blocks[i][j]);
                builder.append(" ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public int compareTo(Board board) {
        if(board == null) return 1;
        return Integer.compare(this.manhattan, board.manhattan);
    }
}
