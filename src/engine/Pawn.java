package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece implements LinearMove {
    public Pawn(PlayerColor color, Square square, Board board) {
        super(color, square, board);
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(Square dest) {
        if (isOnline(this.square, dest) && !checkCollision(dest) && this.square.getX() == dest.getX()) {
            int dist = Math.abs(this.square.getY() - dest.getY());
            if (((this.nbMove == 0 && (dist == 1 || dist == 2)) || dist == 1)) {
                //Check si la case est en face du pion
                switch (this.getColor()) {
                    case WHITE -> {
                        return this.square.getY() < dest.getY();//Check si la case est en face du pion
                    }
                    case BLACK -> {
                        return this.square.getY() > dest.getY();
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }
}