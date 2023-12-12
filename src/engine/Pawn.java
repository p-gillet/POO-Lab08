package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    public Pawn(PlayerColor color, Square square){
        super(PieceType.PAWN, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
