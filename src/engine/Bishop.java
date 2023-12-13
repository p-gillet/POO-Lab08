package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    public Bishop(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    public boolean canMove(Square dest) {
        return !dest.isOccupied() &&
                Math.abs((square.getX() - dest.getX())) == Math.abs(square.getY() - dest.getY());
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
