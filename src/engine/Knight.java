package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {
    public Knight(PlayerColor color, Square square){
        super(PieceType.KNIGHT, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
