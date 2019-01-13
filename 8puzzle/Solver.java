/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Solver {
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        int boardSize = 3;

        int [][] board = new int[boardSize][boardSize];

//        for(int i = 0; i < boardSize; i++) {
//            for (int j = 0; j < boardSize; j++) {
//                int position = i * boardSize + j;
//                board[i][j] = position;
////                System.out.println(i * boardSize + j + 1);
//            }
//        }



        board[0][0] = 1;
        board[0][1] = 4;
        board[0][2] = 3;
        board[1][0] = 7;
        board[1][1] = 0;
        board[1][2] = 8;
        board[2][0] = 6;
        board[2][1] = 5;
        board[2][2] = 2;

        Board b = new Board(board);

//        System.out.println();

        Solver solver = new Solver(b);
    }

    private ArrayList<Board> visitedBoards;

//    private Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

//        this.initial = initial;
        visitedBoards = new ArrayList<>();
        solveBoard(initial);
    }

    private void solveBoard(Board board) {
        System.out.println(board);
        return;
//        if(board.isGoal()) {
//            System.out.println(board);
////            System.out.println("solved");
//            return;
//        }
//
//        visitedBoards.add(board);
//
//        ArrayList<Board> boards = (ArrayList<Board>) board.neighbors();
//
////        System.out.println(boards);
//
//        for(Board b : boards) {
//            if(!visitedBoards.contains(b)) {
//                solveBoard(b);
//            }
//        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }
}
