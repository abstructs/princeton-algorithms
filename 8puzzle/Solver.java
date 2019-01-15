/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

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


        // OG board
        board[0][0] = 1;
        board[0][1] = 4;
        board[0][2] = 3;
        board[1][0] = 7;
        board[1][1] = 0;
        board[1][2] = 8;
        board[2][0] = 6;
        board[2][1] = 5;
        board[2][2] = 2;

//        board[0][0] = 1;
//        board[0][1] = 2;
//        board[0][2] = 3;
//        board[1][0] = 4;
//        board[1][1] = 5;
//        board[1][2] = 6;
//        board[2][0] = 8;
//        board[2][1] = 7;
//        board[2][2] = 0;

        Board b = new Board(board);
//        System.out.println(b.twin());
//        System.out.println(b.neighbors());
//        System.out.println(b);

//        System.out.println("Original Board");
//        System.out.println(b);
//        System.out.println("Neighbors:");
//        System.out.println(b.neighbors());

//        System.out.println(b.manhattan());

        Solver solver = new Solver(b);
        System.out.println(solver.moves());
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode predesessor;
        private int priority;
        private int moves;

        private SearchNode(Board board, SearchNode predesessor) {
            this.predesessor = predesessor;
            this.board = board;
            this.moves = predesessor == null ? 0 : predesessor.moves + 1;
            this.priority = board.manhattan() + moves;
        }

        private boolean isPredesessor(Board board) {
            SearchNode temp = predesessor;

            while(temp != null) {
                if(board.equals(temp.getBoard())) {
                    return true;
                }

                temp = temp.predesessor;
            }

            return false;
        }

        private Board getBoard() {
            return this.board;
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    private Stack<Board> solution;
    private SearchNode finalSearchNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException();

        this.solution = getSolution(initial);
    }

    private Stack<Board> getSolution(Board inital) {
//        SearchNode currentSearchNode = new SearchNode(inital, null);
//        Board currentBoard;

//        SearchNode twinSearchNode = new SearchNode(inital.twin(), null);
//        Board twinBoard;

        ArrayList<Board> visitedNodes = new ArrayList<>();
        ArrayList<Board> twinVisitedNodes = new ArrayList<>();

//        visitedBoards.add(currentSearchNode);

//        System.out.println(visitedBoards.contains(currentSearchNode));
//        ArrayList<Board> twinVisitedBoards = new ArrayList<>();
//
        MinPQ<SearchNode> minPQ = new MinPQ<>(1);
        MinPQ<SearchNode> twinPQ = new MinPQ<>(1);
//

        minPQ.insert(new SearchNode(inital, null));
        twinPQ.insert(new SearchNode(inital.twin(), null));

//        visitedNodes.add(currentBoard);

//        System.out.println(currentBoard.manhattan());
//        visitedNodes.add(currentSearchNode);
//        twinPQ.insert(twinSearchNode);

//        int count = 0;

        SearchNode currentSearchNode;
        Board currentBoard;

        SearchNode twinSearchNode;
        Board twinBoard;

        do {
            currentSearchNode = minPQ.delMin();
            currentBoard = currentSearchNode.getBoard();

            twinSearchNode = twinPQ.delMin();
            twinBoard = twinSearchNode.getBoard();

            for (Board neighbor : currentBoard.neighbors()) {
                if(!currentSearchNode.isPredesessor(neighbor)) {
                    minPQ.insert(new SearchNode(neighbor, currentSearchNode));
                }
            }

            for (Board neighbor : twinBoard.neighbors()) {
                if(!twinSearchNode.isPredesessor(neighbor)) {
                    twinPQ.insert(new SearchNode(neighbor, twinSearchNode));
                }
            }

        } while(!currentBoard.isGoal() && !twinBoard.isGoal());

        // no solution found
        if(twinBoard.isGoal()) {
            return null;
        }

        this.finalSearchNode = currentSearchNode;

        Stack<Board> solution = new Stack<>();

        while(currentSearchNode != null) {
            solution.push(currentSearchNode.getBoard());
            currentSearchNode = currentSearchNode.predesessor;
        }

        return solution;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return this.solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return finalSearchNode != null ? finalSearchNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }
}
