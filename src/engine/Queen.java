package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {
    public Queen(PlayerColor color, Square square){
        super(PieceType.QUEEN, color, square);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }
}
