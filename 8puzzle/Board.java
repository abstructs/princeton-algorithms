/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    // unit tests (not graded)
    public static void main(String[] args) {
        int boardSize = 3;

        int [][] board = new int[boardSize][boardSize];

        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int position = i * boardSize + j;
                board[i][j] = position;
//                System.out.println(i * boardSize + j + 1);5
            }
        }

        Board b = new Board(board);
//        b.twin();
        System.out.println(b.neighbors());
    }

    private int[][] blocks;
    private int BLANK_VAL;
    private int BLANK_ROW;
    private int BLANK_COL;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];

        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }

        this.BLANK_ROW = 0;
        this.BLANK_COL = 0;
        this.BLANK_VAL = blocks[BLANK_ROW][BLANK_COL];
    }

    // board dimension n
    // number of blocks out of place
    public int dimension() {
        if(this.blocks.length == 0) return 0;
        return this.blocks.length;
    }

    public int hamming() {
        int blocksOutOfPlace = 0;

        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                if(blocks[i][j] != expectedPositon(i, j)) {
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
                int expectedPositon = expectedPositon(i, j);
                int position = blocks[i][j];

                totalDistance += Math.abs(expectedPositon - position);
            }
        }

        return totalDistance;
    }

    private int expectedPositon(int i, int j) {
        return i * blocks.length + j;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                int position = blocks[i][j];

                if(position != expectedPositon(i, j)) {
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
            BLANK_ROW = row1;
            BLANK_COL = col1;
        } else if(swap2 == BLANK_VAL) {
            BLANK_ROW = row2;
            BLANK_COL = col2;
        }
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board board = new Board(blocks);

        int randomRow1 = StdRandom.uniform(dimension());
        int randomCol1 = StdRandom.uniform(dimension());

        int randomRow2 = StdRandom.uniform(dimension());
        int randomCol2 = StdRandom.uniform(dimension());

        board.swap(randomRow1, randomCol1, randomRow2, randomCol2);

        return board;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if(y instanceof Board) {
            Board yBoard = (Board) y;

            if(dimension() != yBoard.dimension()) return false;

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
        Board topBoard = swapWithBlank(BLANK_ROW + 1, BLANK_COL);
        Board bottomBoard = swapWithBlank(BLANK_ROW - 1, BLANK_COL);

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
}
