package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    public Pawn(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(Square dest) {
        boolean distIsValid;
        boolean isAhead;
        switch (color){
            case WHITE -> {
                isAhead     = this.square.getY() < dest.getY();
            }
            case BLACK -> {
                isAhead = this.square.getY() > dest.getY();
            }
        }
        return false; // yikes
    }
}
