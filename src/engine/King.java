package engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece {
    public King(PlayerColor color, Square square){
        super(PieceType.KING, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
