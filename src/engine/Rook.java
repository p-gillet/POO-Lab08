package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {
    public Rook(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
}
