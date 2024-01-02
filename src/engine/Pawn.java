package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece implements LinearMove {
    private final int goesUp; // 1 si le pion va vers le haut, -1 s'il va vers le bas
    private boolean enPassantVictim; // true si le pion est une victime de la prise en passant
    public Pawn(PlayerColor color, Square square, Board board){
        super(color, square, board);
        goesUp = color == PlayerColor.WHITE ? 1 : -1;
        enPassantVictim = false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }


    @Override
    public boolean isValidMove(Square dest) {
        if (isOnline(this.getSquare(), dest) && this.getSquare().getX() == dest.getX()) {
            int dist = Math.abs(this.getSquare().getY() - dest.getY());
            if (((this.nbMove == 0 && (dist == 1 || dist == 2)) || dist == 1)) {
                //Check si la case est en face du pion
                switch (this.getColor()) {
                    case WHITE -> {
                        return this.getSquare().getY() < dest.getY();//Check si la case est en face du pion
                    }
                    case BLACK -> {
                        return this.getSquare().getY() > dest.getY();
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }
}