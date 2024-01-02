package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece implements LinearMove {
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

    @Override
    public boolean isOnline(Square from, Square to) {
        return LinearMove.super.isOnline(from, to);
    }
    @Override
    public boolean isValidMove(Square dest) {
        return isOnline(this.square, dest) && !checkCollision(dest);
    }
}
