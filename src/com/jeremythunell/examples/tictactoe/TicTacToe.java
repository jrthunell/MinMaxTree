package com.jeremythunell.examples.tictactoe;

import com.jeremythunell.minmaxtree.MinMaxTree;

import java.util.Scanner;

public class TicTacToe {

    private TicTacToeGameState gameState;
    private MinMaxTree<Integer> ai;

    public TicTacToe() {
        gameState = TicTacToeGameState.newGame();
        ai = MinMaxTree.minMaxTree(gameState);
    }

    private boolean isFinished() {
        return gameState.isTerminal();
    }

    private void doXTurn(int x, int y) {
        gameState = gameState.doTurn(x - 1, y - 1);
        ai.makeAction((y - 1) * 3 + (x-1));
        ai.exploreToDepth(10);
    }

    private void doOTurn() {
        int aiTurn = ai.getAction();
        gameState = gameState.doTurn(aiTurn % 3, aiTurn / 3);
        ai.makeAction(aiTurn);
        ai.exploreToDepth(10);
    }

    @Override
    public String toString() {
        return gameState.toString();
    }


    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        System.out.println(game);
        Scanner in = new Scanner(System.in);


        while (!game.isFinished()) {
            System.out.println("X's turn, enter coordinates");
            int x = -1, y = -1;
            do {
                try {
                    String line = in.nextLine();
                    String[] tokens = line.split(",");
                    if (tokens.length != 2)
                        throw new RuntimeException("Need two numbers, got " + tokens.length);
                    x = Integer.parseInt(tokens[0]);
                    if (x < 1 || x > 3)
                        throw new RuntimeException("X needs to be between 1 and 3");
                    y = Integer.parseInt(tokens[1]);
                    if (y < 1 || y > 3)
                        throw new RuntimeException("Y needs to be between 1 and 3");
                } catch (Exception e) {
                    e.printStackTrace();
                    x = y = -1;
                }
            } while (x < 1);
            assert x > 0 && x < 4;
            assert y > 0 && y < 4;

            game.doXTurn(x, y);
            System.out.println("X's turn: ");
            System.out.println(game.toString() + "\n\n");
            if(game.isFinished())
                break;
            System.out.println("O's turn: ");
            game.doOTurn();
            System.out.println(game.toString() + "\n\n");
        }
        System.out.println("======= GAME OVER ========");
    }
}
