package com.jeremythunell.minmaxtree.examples.chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.jeremythunell.minmaxtree.minmaxtree.MinMaxGameState;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessGameState implements MinMaxGameState<String> {
    String board;
    Side player;
    Board b = null;

    public ChessGameState(String fen, Side player) {
        this.board = fen;
        this.player = player;
    }

    @Override
    public Map<MinMaxGameState<String>, String> getChildren() {
        if(this.b == null) {
            b = new Board();
            b.loadFromFen(board);
        }
        MoveList moves;
        try {
            moves = MoveGenerator.generateLegalMoves(b);
        } catch (MoveGeneratorException e) {
            return Collections.emptyMap();
        }
        Map<MinMaxGameState<String>, String> children = new HashMap<>();
        for(Move move : moves){
            String moveStr = move.toString();
            Board newBoard = b.clone();
            newBoard.doMove(new Move(moveStr, player));
            ChessGameState child = new ChessGameState(newBoard.getFen(), (player == Side.WHITE)?Side.BLACK : Side.WHITE);
            children.put(child, moveStr);
        }
        return children;
    }

    @Override
    public double evaluate() {
        final double KING = 10000, QUEEN = 10, ROOK = 5, KNIGHT = 3, BISHOP = 3, PAWN = 1;

        if(this.b == null) {
            b = new Board();
            b.loadFromFen(board);
        }
        double score = 0; // higher score means black is winning
        for(Piece piece : b.boardToArray()){
            switch(piece){
                case BLACK_KING:
                    score += KING;
                    break;
                case BLACK_QUEEN:
                    score += QUEEN;
                    break;
                case BLACK_ROOK:
                    score += ROOK;
                    break;
                case BLACK_BISHOP:
                    score += BISHOP;
                    break;
                case BLACK_KNIGHT:
                    score += KNIGHT;
                    break;
                case BLACK_PAWN:
                    score += PAWN;
                    break;
                case WHITE_KING:
                    score -= KING;
                    break;
                case WHITE_QUEEN:
                    score -= QUEEN;
                    break;
                case WHITE_ROOK:
                    score -= ROOK;
                    break;
                case WHITE_BISHOP:
                    score -= BISHOP;
                    break;
                case WHITE_KNIGHT:
                    score -= KNIGHT;
                    break;
                case WHITE_PAWN:
                    score -= PAWN;
                    break;
            }
        }
        return score;
    }

    @Override
    public boolean isTerminal() {
        if(this.b == null) {
            b = new Board();
            b.loadFromFen(board);
        }
        return b.isMated() || b.isStaleMate();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ChessGameState))
            return false;
        return ((ChessGameState) obj).board.equals(board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
