package com.jeremythunell.examples.tictactoe;

import com.jeremythunell.minmaxtree.MinMaxGameState;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TicTacToeGameState implements MinMaxGameState<Integer> {

    private boolean xsTurn;
    private char[][] gameboard;

    public TicTacToeGameState(boolean xsTurn, char[][] gameboard) {
        this.xsTurn = xsTurn;
        this.gameboard = gameboard;
    }

    public static TicTacToeGameState newGame() {
        return new TicTacToeGameState(true, new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}});
    }


    @Override
    public Map<MinMaxGameState<Integer>, Integer> getChildren() {
        Map<MinMaxGameState<Integer>, Integer> children = new LinkedHashMap<>();
        for (int i = 0; i < 9; i++) {
            int x = i % 3;
            int y = i / 3;
            char xOrO = xsTurn ? 'X' : 'O';
            if (gameboard[x][y] == ' ') {
                char[][] newBoard = cloneGameBoard();
                newBoard[x][y] = xOrO;
                children.put(new TicTacToeGameState(!xsTurn, newBoard), i);
            }
        }
        return children;
    }

    @Override
    public double evaluate() {
        // Higher score means O is winning
        return xScore() - oScore();
    }

    private double xScore() {
        // the number of pieces X has in each row/column/diagonal, or 0 if they can't win that row
        int score = 0;
        // horizontal
        for (int x = 0; x < 3; x++) {
            int rowScore = 0;
            for (int y = 0; y < 3; y++) {
                if (gameboard[x][y] == 'O') {
                    rowScore = 0;
                    break;
                } else if (gameboard[x][y] == 'X') {
                    rowScore++;
                }
            }
            score += (rowScore == 3) ? 10000 : rowScore;
        }
        // vertical
        for (int y = 0; y < 3; y++) {
            int colScore = 0;
            for (int x = 0; x < 3; x++) {
                if (gameboard[x][y] == 'O') {
                    colScore = 0;
                    break;
                } else if (gameboard[x][y] == 'X') {
                    colScore++;
                }
            }
            score += (colScore == 3) ? 10000 : colScore;
        }
        // diag top-left to bottom-right
        int diagScore = 0;
        for (int i = 0; i < 3; i++) {
            if (gameboard[i][i] == 'O') {
                diagScore = 0;
                break;
            } else if (gameboard[i][i] == 'X') {
                diagScore++;
            }
        }
        score += (diagScore == 3) ? 10000 : diagScore;
        // diag top-right to bottom-left
        diagScore = 0;
        for (int i = 0; i < 3; i++) {
            if (gameboard[i][2 - i] == 'O') {
                diagScore = 0;
                break;
            } else if (gameboard[i][2 - i] == 'X') {
                diagScore++;
            }
        }
        score += (diagScore == 3) ? 10000 : diagScore;
        return score;
    }

    private double oScore() {
        // the number of pieces X has in each row/column/diagonal, or 0 if they can't win that row
        int score = 0;
        // horizontal
        for (int x = 0; x < 3; x++) {
            int rowScore = 0;
            for (int y = 0; y < 3; y++) {
                if (gameboard[x][y] == 'X') {
                    rowScore = 0;
                    break;
                } else if (gameboard[x][y] == 'O') {
                    rowScore++;
                }
            }
            score += (rowScore == 3) ? 10000 : rowScore;
        }
        // vertical
        for (int y = 0; y < 3; y++) {
            int colScore = 0;
            for (int x = 0; x < 3; x++) {
                if (gameboard[x][y] == 'X') {
                    colScore = 0;
                    break;
                } else if (gameboard[x][y] == 'O') {
                    colScore++;
                }
            }
            score += (colScore == 3) ? 10000 : colScore;
        }
        // diag top-left to bottom-right
        int diagScore = 0;
        for (int i = 0; i < 3; i++) {
            if (gameboard[i][i] == 'X') {
                diagScore = 0;
                break;
            } else if (gameboard[i][i] == 'O') {
                diagScore++;
            }
        }
        score += (diagScore == 3) ? 10000 : diagScore;
        // diag top-right to bottom-left
        diagScore = 0;
        for (int i = 0; i < 3; i++) {
            if (gameboard[i][2 - i] == 'X') {
                diagScore = 0;
                break;
            } else if (gameboard[i][2 - i] == 'O') {
                diagScore++;
            }
        }
        score += (diagScore == 3) ? 10000 : diagScore;
        return score;
    }

    @Override
    public boolean isTerminal() {
        if( xScore() > 1000 || oScore() > 1000)
            return true;

        // check if all tiles on the board are filled in
        for (int x = 0; x <3; x++) {
            for (int y = 0; y < 3; y++) {
                if(gameboard[x][y] == ' ')
                    return false;
            }
        }
        return true;
    }

    public TicTacToeGameState doTurn(int x, int y) {
        char[][] newBoard = cloneGameBoard();
        assert newBoard[x][y] == ' ';
        assert x >= 0 && x <= 3;
        assert y >= 0 && y <= 3;
        newBoard[x][y] = (xsTurn) ? 'X' : 'O';
        return new TicTacToeGameState(!xsTurn, newBoard);
    }

    @Override
    public String toString() {
        String s = "-------------\n";
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                s += "| " + gameboard[x][y] + " ";
            }
            s += "|\n";
        }
        s += "-------------";
        return s;
    }

    private char[][] cloneGameBoard(){
        char[][] newBoard = new char[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                newBoard[x][y] = gameboard[x][y];
            }
        }
        return newBoard;
    }
}
