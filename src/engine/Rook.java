package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {
    public Rook(PlayerColor color, Square square){
        super(PieceType.ROOK, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
