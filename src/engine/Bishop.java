package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    public Bishop(PlayerColor color, Square square){
        super(PieceType.BISHOP, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
