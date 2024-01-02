package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece implements DiagonalMove {
    public Bishop(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    protected boolean isValidMove(Square target) {
        return isOnDiagonal(this.getSquare(), target);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
