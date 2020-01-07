package com.jeremythunell.minmaxtree.examples.chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import com.jeremythunell.minmaxtree.minmaxtree.MinMaxTree;

import java.util.Scanner;

public class Chess {
    private Board currentBoard;
    private MinMaxTree<String> ai;

    public Chess() {
        currentBoard = new Board();
        ai = MinMaxTree.maxMinTree(new ChessGameState(currentBoard.getFen(), Side.WHITE));
    }

    public static void main(String[] args) {
        Chess game = new Chess();
        Scanner scanner = new Scanner(System.in);
        while (!game.isTerminal()) {
            boolean validMove = true;
            do {
                try {
                    System.out.println(game.toString());
                    System.out.println("Your turn, enter move in the form \"a2a3\"");
                    String move = scanner.nextLine();
                    game.doWhiteMove(move);
                } catch (IllegalArgumentException e) {
                    validMove = false;
                    System.out.println("Invalid move");
                }
            }while(!validMove);
            System.out.println(game.toString());
            System.out.println("\n\nComputer's turn:");
            if(game.isTerminal())
                break;
            game.doBlackMove();
        }
        System.out.println("Game over!");

    }

    private void doBlackMove() {
        String move = ai.getAction();
        currentBoard.doMove(new Move(move, Side.BLACK));
        ai.makeAction(move);
        long startTime = System.currentTimeMillis();
        ai.exploreToDepth(3);
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println((totalTime / 1000.0) + " seconds to explore 3 moves ahead");
    }

    private void doWhiteMove(String move) {
        currentBoard.doMove(new Move(move, Side.WHITE));
        ai.makeAction(move);
        ai.exploreNNodes(1000);
    }

    private boolean isTerminal() {
        return currentBoard.isMated() || currentBoard.isStaleMate();
    }

    @Override
    public String toString() {
        String[] rows = currentBoard.toString().split("\n");
        StringBuilder output = new StringBuilder();
        output.append("   ┌───────────────────────────────┐\n");
        output.append(" " + (8) + " ");
        for (char c : rows[0].toCharArray()) {
            output.append("│ ").append(c).append(" ");
        }
        output.append("│\n");
        for (int row = 1; row < rows.length - 1; row++) {
            output.append("   ├───┼───┼───┼───┼───┼───┼───┼───┤\n");
            output.append(" ").append(8 - row).append(" ");
            for (char c : rows[row].toCharArray()) {
                output.append("│ ").append(c).append(" ");
            }
            output.append("│\n");
        }
        output.append("   └───────────────────────────────┘\n");
        output.append("     A   B   C   D   E   F   G   H\n");
        return output.toString();
    }
}
