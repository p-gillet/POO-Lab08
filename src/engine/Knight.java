package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {
    public Knight(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
